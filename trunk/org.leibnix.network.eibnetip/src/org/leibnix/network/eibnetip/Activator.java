/*
 * Created on Fri Jun 29 19:59:50 CEST 2007
 */
package org.leibnix.network.eibnetip;

import java.util.Dictionary;
import java.util.Hashtable;

import org.leibnix.network.INetworkDevice;
import org.leibnix.network.NetworkDriver;
import org.leibnix.network.eibnetip.internal.EIBIPNetworkDeviceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Device;
import org.osgi.service.device.Driver;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {

	private BundleContext bc;

	ServiceTracker logTracker;

	private ServiceRegistration driverService;
	private ServiceRegistration deviceService;

	private static final String[] deviceClasses = new String[] {
			Device.class.getName(), INetworkDevice.class.getName() };

	private static final String[] driverClasses = new String[] {
			Driver.class.getName(), NetworkDriver.class.getName() };

	/*
	 * (non-Javadoc)
	 *
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		this.bc = context;
		// logTracker = new ServiceTracker(bc, LogService.class.getName(),
		// null);
		// logTracker.open();
		// info("Start EIBNetworkDevice");
		// register Driver
		Dictionary driverProps = new Hashtable();
		driverProps.put("DRIVER_ID", "org.leibnix.device.eibnetworkdriver.1.1");

		try {
			NetworkDriver driver = new NetworkDriver(context);
			driverService = bc.registerService(driverClasses, driver,
					driverProps);
			// register Device
			Dictionary deviceProps = new Hashtable();
			deviceProps.put("DEVICE_CATEGORY", new String[] { "Network" });
			deviceProps.put("DEVICE_DESCRIPTION", "Leibnix Network Device");
			EIBIPNetworkDeviceImpl eibNetwork = new EIBIPNetworkDeviceImpl();
			deviceService = bc.registerService(deviceClasses, eibNetwork,
					deviceProps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

	private void info(String msg) {
		if (getLog() != null) {
			getLog().log(LogService.LOG_INFO, msg);
		}
	}

	LogService getLog() {
		return (LogService) logTracker.getService();
	}

}