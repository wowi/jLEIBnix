package org.leibnix.admin.rcp.ui;


//public class DeviceListener implements DiscoveryListener {
//
//	private RemoteOSGiService remote;
//	private BundleContext context;
//
//
//	DeviceListener(BundleContext bcontext, RemoteOSGiService rosgi) {
//		remote = rosgi;
//		context = bcontext;
//	}
//
//	public void notifyDiscovery(ServiceURL service) {
//
//		try {
//			// fetch the service, i.e. let this framework
//			// build a proxy bundle on the fly and register
//			// it with the local service registry
//			remote.fetchService(service);
//
//			// we have many RobotDevices, so get a reference to
//			// exactly the device that has just been discovered
////			RobotDevice robot = (RobotDevice) remote.getFetchedService(service);
//			Object remoteService = remote.getFetchedService(service);
//
//			// and start a new instance of RobotController to control this device
//			// controller = new RobotController(service, robot);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void notifyServiceLost(ServiceURL service) {
//		// we lost contact to the service, so shut down the controller instance.
//		// RobotController.dispose(service);
//	}
//}