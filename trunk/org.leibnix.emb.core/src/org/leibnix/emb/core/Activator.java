package org.leibnix.emb.core;

import java.util.HashMap;

import org.leibnix.configuration.ConfigSetFactory;
import org.leibnix.configuration.IConfigSet;
import org.leibnix.configuration.IConfigurationManager;
import org.leibnix.emb.core.internal.MessageBusImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private BundleContext bc;
	private ServiceRegistration embService;

	private static final String[] embClasses = new String[] { IMessageBus.class
			.getName() };

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		this.bc = context;

		// get Configuration Manager Service
		ServiceReference sr = context
				.getServiceReference(IConfigurationManager.class.getName());
		IConfigurationManager configManager = (IConfigurationManager) context
				.getService(sr);

		
		// create Device Manager
		configManager.destroyConfigSet(DeviceManager.LEIBNIX_DEVICE);
		configManager.destroyConfigSet("LEIBNIX_DEVICE_VARIABLE_DESCRIPTION");
		DeviceManager deviceManager = DeviceManager.getInstance(configManager);
		deviceManager.createConfigSet();
		
		// Dummy Data
		IConfigSet newConfigSet = ConfigSetFactory
				.newConfigSet(DeviceManager.LEIBNIX_DEVICE);
		HashMap map = new HashMap();
		map.put("ID", "Thermostat Arbeitszimmer");
		map.put("NETWORK_TYPE", "NETWORK_TYPE_EIB");
		newConfigSet.add(map);
		map = new HashMap();
		map.put("ID", "Thermostat Nicola");
		map.put("NETWORK_TYPE", "NETWORK_TYPE_EIB");
		newConfigSet.add(map);
		map = new HashMap();
		map.put("ID", "VDR-Server");
		map.put("NETWORK_TYPE", "NETWORK_TYPE_LAN");
		newConfigSet.add(map);
		configManager.insertConfigSet(newConfigSet);

		MessageBusImpl emb = new MessageBusImpl(context);
		emb.setDeviceManager (deviceManager);
		embService = bc.registerService(embClasses, emb, null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
