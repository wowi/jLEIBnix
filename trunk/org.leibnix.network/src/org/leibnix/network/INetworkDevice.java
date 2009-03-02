package org.leibnix.network;

import org.leibnix.server.osgi.INetworkListener;
import org.leibnix.core.IMessage;
import org.osgi.service.device.Device;


public interface INetworkDevice extends Device {

	// public void sendFrame(CEMI _CEMI, boolean _Mode) throws EICLException;
	//
	// public void addListener (EICLEventListener eventListener);
	//exit

	// public void disconnect() throws EICLException;
	//
	public void connect();

	public void addListener(INetworkListener memb);

	public void sendMessage(IMessage pMessage);
}
