package org.leibnix.emb.core;

import java.util.ArrayList;
import java.util.List;

import org.leibnix.configuration.IConfigSet;
import org.leibnix.configuration.IConfigurationManager;
import org.leibnix.core.Device;


public class DeviceManager {

	public static final String LEIBNIX_DEVICE = "LEIBNIX_DEVICE";
	private static DeviceManager mDeviceManager = null;
	private IConfigurationManager mConfigManager;
	private List<Device> mDeviceList = null;

	private DeviceManager(IConfigurationManager pConfigManager) {
		mConfigManager=pConfigManager;
	};
		
	public static DeviceManager getInstance (IConfigurationManager pConfigManager) {
		if (mDeviceManager  == null) {
			synchronized (LEIBNIX_DEVICE) {
				if (mDeviceManager == null) {
					mDeviceManager = new DeviceManager(pConfigManager);
				}
			}
		}
		return mDeviceManager;
	}

	public void createConfigSet() {
		mConfigManager.newConfigSet(LEIBNIX_DEVICE,
				"KEY,ID String, NETWORK_TYPE String, MANUFACTURER String, TYPE String, TEMPLATE Boolean");
		mConfigManager.newConfigSet("LEIBNIX_DEVICE_VARIABLE_DESCRIPTION",
				"KEY,ID String, TYPE String, TEMPLATE Boolean");
	}

	public List<Device> getDevices (String pNetworkType) {
		IConfigSet configSet = mConfigManager.getConfigSet(LEIBNIX_DEVICE, "NETWORK_TYPE='" + pNetworkType+"'");
		String id = null;
		mDeviceList = new ArrayList();
		while (configSet.hasNext()) {
			configSet.next();
			id = configSet.getString("ID");
			mDeviceList.add(new Device(id, "TYPE_EIB", false));
		}
		return mDeviceList;
	}
	
	public List getDevices() {
		// TODO Auto-generated method stub
		return null;
	}

	public static DeviceManager getInstance() {
		if (mDeviceManager==null) {
			throw new IllegalStateException("Device Manager not initialized");
		}
		return mDeviceManager;
	}
}
