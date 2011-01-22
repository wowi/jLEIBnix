package org.leibnix.network.eibnetip.internal;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.leibnix.core.Device;
import org.leibnix.core.IBusDevice;
import org.leibnix.core.IMessage;
import org.leibnix.core.ITarget;
import org.leibnix.core.IValue;
import org.leibnix.emb.core.DeviceManager;
import org.leibnix.network.INetworkDevice;
import org.leibnix.network.eibnetip.Activator;
import org.leibnix.server.osgi.INetworkListener;
import org.osgi.framework.BundleContext;

import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.datapoint.Datapoint;
import tuwien.auto.calimero.datapoint.StateDP;
import tuwien.auto.calimero.exception.KNXException;
import tuwien.auto.calimero.exception.KNXFormatException;
import tuwien.auto.calimero.link.KNXLinkClosedException;
import tuwien.auto.calimero.link.KNXNetworkLink;
import tuwien.auto.calimero.link.KNXNetworkLinkFT12;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.log.LogLevel;
import tuwien.auto.calimero.log.LogStreamWriter;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

public class EIBIPNetworkDeviceImpl implements INetworkDevice {

	public static String NETWORK_TYPE_EIB = "NETWORK_TYPE_EIB";
	private KNXNetworkLink mKNXNetwokrLink;

	@Override
	public void addListener(INetworkListener memb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connect() {
//		LogManager.getManager().addWriter(null, new ConsoleWriter(false));
		try {
			// KNX/IP Connection
			// mKNXNetwokrLink = createLink("192.168.178.48",
			// KNXnetIPConnection.IP_PORT);
			//
			// serial connection
			mKNXNetwokrLink = new KNXNetworkLinkFT12(Integer.parseInt("1"),
					new TPSettings(true));
			testRead();
		} catch (KNXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

	}

	@Override
	public void sendMessage(IMessage message) {
		write(message.getDestination(), message.getValue());

	}

	@Override
	public void noDriverFound() {
		// TODO Auto-generated method stub

	}

	private KNXNetworkLink createLink(String pHostName, int pPort)
			throws KNXException, UnknownHostException {
		InetAddress hostAddr = InetAddress.getByName(pHostName);
		final InetSocketAddress host = new InetSocketAddress(hostAddr, pPort);
		return new KNXNetworkLinkIP(KNXNetworkLinkIP.TUNNEL, null, host, false,
				TPSettings.TP1);
	}

	private static final class ConsoleWriter extends LogStreamWriter {
		ConsoleWriter(boolean verbose) {
			super(verbose ? LogLevel.INFO : LogLevel.ERROR, System.out, true);
		}

		public void close() {
		}
	}

	private void testRead() {
		ProcessCommunicator pc = null;
		try {
			pc = new ProcessCommunicatorImpl(mKNXNetwokrLink);
			// if (options.containsKey("timeout"))
			// pc.setResponseTimeout(((Integer)
			// options.get("timeout")).intValue());
			// final boolean read = options.containsKey("read");
			final GroupAddress main = new GroupAddress("1/0/5");
			String dptID = "float";
			if (dptID.equals("bool"))
				dptID = "1.002";
			else if (dptID.equals("string"))
				dptID = "16.001";
			else if (dptID.equals("float"))
				dptID = "9.002";
			else if (dptID.equals("ucount"))
				dptID = "5.010";
			else if (dptID.equals("angle"))
				dptID = "5.003";

			// Datapoint datapoint = new Datapoint(main, "", true);
			final Datapoint dp = new StateDP(main, "", 0, dptID);
			System.out.println("read value: " + pc.read(dp));
		} catch (KNXLinkClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KNXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pc != null)
				pc.detach();
		}

	}

	private void write(IBusDevice pDevice, IValue pValue) {
		ProcessCommunicator pc = null;
		Exception lastException = null;
		int retry = 1;
		boolean send = false;
		while (send == false && retry >= 0) {
			try {
				if (mKNXNetwokrLink == null) {
					// mKNXNetwokrLink = createLink("192.168.178.23",
					// KNXnetIPConnection.IP_PORT);
					mKNXNetwokrLink = new KNXNetworkLinkFT12(
							Integer.parseInt("1"), new TPSettings(true));
				}
				pc = writeToEIB(pDevice, pValue);
				send = true;
			} catch (KNXLinkClosedException e) {
				retry--;
				mKNXNetwokrLink = null;
				lastException = e;
			} catch (KNXException e) {
				retry--;
				mKNXNetwokrLink = null;
				lastException = e;
				// } catch (UnknownHostException e) {
				// e.printStackTrace();
				// lastException = e;
			} finally {
				if (pc != null)
					pc.detach();
			}
		}
		if (send == false) {
			// System.out.println ("Exception in Send: ");
			// lastException.printStackTrace();
		}

	}

	private ProcessCommunicator writeToEIB(IBusDevice pDevice, IValue pValue)
			throws KNXLinkClosedException, KNXFormatException, KNXException {
		ProcessCommunicator pc;
		pc = new ProcessCommunicatorImpl(mKNXNetwokrLink);
		pc.setResponseTimeout(1000);
		final GroupAddress main = new GroupAddress(pDevice.getId());
		String dptID = typeToDptId(pValue.getType());
		final Datapoint dp = new StateDP(main, "", 0, dptID);
		pc.write(dp, pValue.getValueAsString());
		return pc;
	}

	private String typeToDptId(int pType) {
		switch (pType) {
		case IValue.TYPE_BOOLEAN:
			return "1.002";
		case IValue.TYPE_FLOAT:
			return ("9.002");
		case IValue.TYPE_STRING:
			return ("16.001");
		}
		return null;
	}

	@Override
	public void initDevices() {
		// IConfigSet configSet =
		// mConfigManager.getConfigSet(DeviceManager.LEIBNIX_DEVICE,
		// "NETWORK_TYPE" = );
		DeviceManager deviceManager = DeviceManager.getInstance();
		List<Device> devices = deviceManager.getDevices(NETWORK_TYPE_EIB);
		System.out.println("Found EIB-Devices: " + devices.size());
		for (Device device : devices) {
			System.out.println(device.getId());
		}
	}
}

// public class EIBIPNetworkDeviceImpl implements INetworkDevice,
// EICLEventListener {
//
// private CEMI_Connection tunnel = null;
//
// private String mLock = "LOCKSTRING";
//
// private Hashtable mListenerList = new Hashtable();;
//
// public void noDriverFound() {
// System.out.println("no driver found");
// }
//
// public void connect() {
// System.out.println("connect");
// synchronized (mLock) {
// InetAddress myAdr;
// try {
// myAdr = InetAddress.getByName("192.168.178.24");
//
// HPAI.setLocalIP(myAdr);
// DatagramSocket sock = new DatagramSocket(12345, myAdr);
// tunnel = new CEMI_Connection(sock,
// new InetSocketAddress("224.0.23.12",
// EIBNETIP_Constants.EIBNETIP_PORT_NUMBER),
// new TunnellingConnectionType());
// if (tunnel != null) {
// tunnel.addFrameListener(this);
// }
// } catch (UnknownHostException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (SocketException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (EICLException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
// }
// }
//
// public void disconnect() throws EICLException {
// // TODO Auto-generated method stub
//
// }
//
// public void sendFrame(CEMI _CEMI, boolean _Mode) throws EICLException {
// tunnel.sendFrame(_CEMI, _Mode);
//
// }
//
// public void addListener(INetworkListener eventListener) {
// synchronized (mLock) {
// mListenerList.put(String.valueOf(eventListener.hashCode()),
// eventListener);
// }
//
// }
//
// @Override
// public void newFrameReceived(FrameEvent pEvent) {
// if (mListenerList.size() != 0) {
// Enumeration keys = mListenerList.keys();
// CEMI_L_DATA data = (CEMI_L_DATA) pEvent.getPacket();
// System.out.println("Package Receive.Destination: "
// + data.getDestinationAddress());
// System.out.println("Package Receive.Source: "
// + data.getSourceAddress());
// while (keys.hasMoreElements()) {
// INetworkListener eventListener;
// eventListener = (INetworkListener) mListenerList.get(keys
// .nextElement());
// eventListener.newMessage(null);
// }
// }
//
// }
//
// @Override
// public void serverDisconnected(DisconnectEvent arg0) {
// System.out.println ("******************* FATAL *****************");
// System.out.println ("************* Server Disconnted ***********");
// System.out.println ("*******************************************");
// }
//
// @Override
// public void sendMessage(IMessage pMessage) {
// try {
// PointPDUXlator dimVal = PDUXlatorList.getPointPDUXlator(
// PDUXlatorList.TYPE_BOOLEAN[0],
// PointPDUXlator_Boolean.DPT_BINARYVALUE[0]);
//
// dimVal.setServiceType(PointPDUXlator.A_GROUPVALUE_WRITE);
// dimVal.setASDUfromString(pMessage.getValueAsString());
// CEMI_L_DATA eibMessage = new CEMI_L_DATA(CEMI_L_DATA.MC_L_DATAREQ,
// new EIB_Address(), new EIB_Address("1/0/2"), dimVal
// .getAPDUByteArray());
// tunnel.sendFrame(eibMessage, CEMI_Connection.WAIT_FOR_CONFIRM);
// } catch (EICLException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
//
// }
//
// }
