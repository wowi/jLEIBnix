package org.leibnix.emb.core;

import org.leibnix.server.osgi.IDevice;
import org.leibnix.server.osgi.INetworkListener;
import org.leibnix.core.IMessage;
import org.leibnix.core.ITarget;

public interface IMessageBus extends INetworkListener {
	public void send (IMessage pMessageI);

	public void addDevice(ITarget[] pTargets, IDevice idev);
}
