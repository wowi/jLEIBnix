package org.leibnix.admin.rcp.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	static RemoteOSGiService remote;

	// The plug-in ID
	public static final String PLUGIN_ID = "org.leibnix.admin.rcp.ui";

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		// create a new RobotDeviceListener
		Bundle[] bundles = context.getBundles();
		// ("org.eclipse.equinox.event");
		Bundle r_osgi=null;
		for (int i = 0; i < bundles.length; i++) {
			Bundle bundle = bundles[i];
			System.out.println ("Bundle.getSymbolicName(): " + bundle.getSymbolicName());
			if ("org.eclipse.equinox.event".equals(bundle.getSymbolicName())) {
				bundle.start();
			} else if ("org.eclipse.equinox.log".equals(bundle
					.getSymbolicName())) {
				bundle.start();
			} else if ("ch.ethz.iks.r_osgi.remote".equals(bundle.getSymbolicName())) {
				r_osgi=bundle;
//			} else if ("org.leibnix.device.clocktimer.interfaces".equals(bundle
//					.getSymbolicName())) {
//				bundle.start();
			}
		}
		if (r_osgi != null) {
			r_osgi.start();
		}
		// ServiceReference ref = context
		// .getServiceReference(RemoteOSGiService.class.getName());
		// if (ref != null) {
		// remote = (RemoteOSGiService) context.getService(ref);
		//
		// DeviceListener listener = new DeviceListener(context, remote);
		//
		// // register for discovery
		// context.registerService(DiscoveryListener.class.getName(),
		// listener, null);
		// }

//		ServiceReference ref = context
//				.getServiceReference(RemoteOSGiService.class.getName());
//		if (ref != null) {
//			RemoteOSGiService rs = (RemoteOSGiService) context.getService(ref);
//			ServiceURL[] serviceURLs = rs.connect(InetAddress.getLocalHost(),
//					9278, null); // FIXME use remote host from preferences
//
//			for (ServiceURL service : serviceURLs) {
//				ServiceType serviceType = service.getServiceType();
//
//				System.out.println(serviceType.getConcreteTypeName());
//				if (serviceType.getConcreteTypeName().endsWith("ClockTimer")) {
//					rs.fetchService(service);
//					Object remoteService = rs.getFetchedService(service);
//					// if (remoteService instanceof IClockTimer) {
//						IClockTimer clock = (IClockTimer) remoteService;
//						clock.addTimer("1");
//					// }
//					break;
//				}
//			}
//		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
