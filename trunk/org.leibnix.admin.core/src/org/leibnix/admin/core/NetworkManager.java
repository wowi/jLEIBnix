package org.leibnix.admin.core;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class NetworkManager {
	private Hashtable mNetworks = new Hashtable();
	
	private static NetworkManager mInstance = new NetworkManager();
	
	private NetworkManager() {
		try {
			readRegistry();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readRegistry() throws CoreException {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor("org.leibnix.admin.core.networkTypes");
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			INetwork network = (INetwork) element.createExecutableExtension("networkClass");
			network.setName(element.getAttribute("name"));
			mNetworks.put(element.getAttribute("id"), network);
			// buffer.append(" (");
			// String cost = "unknown";
			// if (element.getAttribute("cost") != null) {
			// cost = element.getAttribute("cost");
			// }
			// buffer.append(cost);
			// buffer.append("\n");
		}
	}
	
	public static Hashtable getNetworks () {
		return (mInstance.mNetworks);
	}

	public static INetwork[] getNetworkArray() {
		Enumeration en = mInstance.mNetworks.elements();
		INetwork[] array = new INetwork[mInstance.mNetworks.size()];
		int i=0;
		while (en.hasMoreElements()) {
			array[i] = (INetwork) en.nextElement();
			i++;
		}		
		return array;
	}
}
