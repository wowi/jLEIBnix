package org.leibnix.device.clocktimer;

import java.util.HashMap;
import java.util.Hashtable;

import org.leibnix.configuration.ConfigSetFactory;
import org.leibnix.configuration.IConfigSet;
import org.leibnix.configuration.IConfigurationManager;
import org.leibnix.device.clocktimer.interfaces.IClockTimer;
import org.leibnix.device.clocktimer.internal.ClockTimerImpl;
import org.leibnix.server.osgi.IDevice;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

public class Activator implements BundleActivator {

	private BundleContext bc;
	private ServiceRegistration clockTimerService;

	private static final String[] clockTimerClasses = new String[] {
			IClockTimer.class.getName(), IDevice.class.getName() };

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		this.bc = context;
		Hashtable properties = new Hashtable();

		// this is the hint for R-OSGi that the service
		// is made available for remote access
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		ServiceReference sr = context
				.getServiceReference(IConfigurationManager.class.getName());
		IConfigurationManager configManager = (IConfigurationManager) context
				.getService(sr);
		// configManager.destroyConfigSet ("LEIBNIX_CLOCKTIMER");
		ClockTimerImpl.createConfigSet(configManager);
		// create TestDatea;
		// IConfigSet newConfigSet =
		// ConfigSetFactory.newConfigSet("LEIBNIX_CLOCKTIMER");
		// HashMap map = new HashMap();
		// map.put("ID", "AZ_EIN");
		// map.put("TYPE", 1);
		// map.put("CONFIG_STRING", "0/20 * * * * ?");
		// map.put("TARGET_ID", "1/0/2");
		// map.put("VALUE", "TRUE");
		// newConfigSet.add (map);
		// map = new HashMap();
		// map.put("ID", "AZ_AUS");
		// map.put("TYPE", 1);
		// map.put("CONFIG_STRING", "10/20 * * * * ?");
		// map.put("TARGET_ID", "1/0/2");
		// map.put("VALUE", "FALSE");
		// newConfigSet.add (map);
		//
		// configManager.insertConfigSet(newConfigSet);

		IConfigSet configSet = configManager.getConfigSet("LEIBNIX_CLOCKTIMER");

		IClockTimer clockTimer = new ClockTimerImpl(context, configSet);
		clockTimerService = bc.registerService(clockTimerClasses, clockTimer,
				properties);

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
