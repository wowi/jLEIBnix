package org.leibnix.server.osgi;

import org.leibnix.core.IMessage;


public interface INetworkListener {

	void newMessage(IMessage pMessage);

}
