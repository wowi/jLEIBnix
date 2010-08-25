package org.leibnix.emb.core.internal;

import java.util.Vector;

import org.leibnix.emb.core.DeviceManager;
import org.leibnix.emb.core.IMessageBus;
import org.leibnix.network.INetworkDevice;
import org.leibnix.server.osgi.IDevice;
import org.leibnix.core.IMessage;
import org.leibnix.core.ITarget;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;


// FIXME: make message bus EIB independent

public class MessageBusImpl implements IMessageBus {

//	private final String filterSpec = "(&(DEVICE_CATEGORY=INetwork))";
	private final String filterSpec = "(|(DEVICE_CATEGORY=INetwork)(objectclass=org.leibnix.core.IDevice))";

	private BundleContext mBC = null;

	private ServiceTracker mTracker;

	private Filter filter;

	private DeviceManager mDeviceManager;

	public MessageBusImpl(BundleContext context)
			throws InvalidSyntaxException {
		mBC = context;
		filter = mBC.createFilter(filterSpec);

		ObjectTracker customizer = new ObjectTracker(mBC, this);
		mTracker = new ServiceTracker(mBC, filter, customizer);
		mTracker.open();
		// ServiceReference sr =
		// context.getServiceReference(EIBNetworkDevice.class.getName());
		// EIBNetworkDevice netDevice = (EIBNetworkDevice)
		// context.getService(sr);
		// netDevice.addListener(this);
	}

	@Override
	public void send(IMessage pMessage) {
		System.out.println("Send Message: " + pMessage);
		ServiceReference sr = mBC.getServiceReference(INetworkDevice.class
				.getName());
		INetworkDevice netDevice = (INetworkDevice) mBC.getService(sr);
		netDevice.sendMessage(pMessage);
	}

	@Override
	public void newMessage(IMessage pMessage) {
		System.out.println("New Message received: " + pMessage);

	}

	public void addDevice(ITarget[] pTargets, IDevice idev) {
		System.out.println ("New device added");
	}

	public void setDeviceManager(DeviceManager pDeviceManager) {
		mDeviceManager = pDeviceManager;		
	}
	
	public Vector getDevices () {
		return (mDeviceManager.getDevices ());
	}

	// public void newFrameReceived(FrameEvent e) {
	// System.out.println("New Message received");
	// CEMI_L_DATA data = (CEMI_L_DATA) e.getPacket();
	// System.out.println ("Package Receive.Destination: " +
	// data.getDestinationAddress());
	// System.out.println ("Package Receive.Source: " +
	// data.getSourceAddress());
	//
	// }
	//
	// public void serverDisconnected(DisconnectEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// public void send(CEMI pMessage) {
	// ServiceReference sr =
	// mBC.getServiceReference(EIBNetworkDevice.class.getName());
	// EIBNetworkDevice netDevice = (EIBNetworkDevice) mBC.getService(sr);
	// try {
	// netDevice.sendFrame(pMessage, CEMI_Connection.WAIT_FOR_CONFIRM);
	// } catch (EICLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
