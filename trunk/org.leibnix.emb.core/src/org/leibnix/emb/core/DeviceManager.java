package org.leibnix.emb.core;

import java.util.Vector;

import org.leibnix.configuration.IConfigurationManager;


public class DeviceManager {

	private IConfigurationManager mConfigManager;

	public DeviceManager(IConfigurationManager pConfigManager) {
		mConfigManager=pConfigManager;
	};


	public void createConfigSet() {
		mConfigManager.newConfigSet("LEIBNIX_DEVICE",
				"KEY,ID String, TYPE String, TEMPLATE Boolean");
		mConfigManager.newConfigSet("LEIBNIX_DEVICE_MESSAGE_DESCRIPTION",
				"KEY,ID String, TYPE String, TEMPLATE Boolean");
	}


	public Vector getDevices() {
		// TODO Auto-generated method stub
		return null;
	}
}
