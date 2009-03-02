package org.leibnix.admin.core;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.leibnix.device.clocktimer.interfaces.IClockTimer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.device.Device;

import ch.ethz.iks.r_osgi.RemoteOSGiException;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;
import ch.ethz.iks.slp.ServiceType;

public class DeviceManager {

	private Hashtable mDevices = new Hashtable();

	private static DeviceManager mInstance = new DeviceManager();

	private DeviceManager() {
		try {
			readRemoteDevices();
		} catch (RemoteOSGiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readRemoteDevices() throws RemoteOSGiException,
			ClassNotFoundException, CoreException, IOException {
		BundleContext context = Activator.getDefaultBundleContext();
		ServiceReference ref = context
				.getServiceReference(RemoteOSGiService.class.getName());
		if (ref != null) {
			RemoteOSGiService rs = (RemoteOSGiService) context.getService(ref);
			RemoteServiceReference[] serviceURLs = rs.connect(new URI("r-osgi://localhost:9278")); // FIXME use remote host from preferences

			for (RemoteServiceReference service : serviceURLs) {
				System.out.println ("Interfaces: " + service.getServiceInterfaces());
				Device remoteDevice = (Device) rs.getRemoteService(service);
				//IClockTimer timer=(IClockTimer) rs.getRemoteService(service);
//				ServiceType serviceType = service.getServiceType();
//
//				System.out.println(serviceType.getConcreteTypeName());
				IDevice deviceDefinition = findDeviceDefinition(service);
				if (deviceDefinition != null) {
					deviceDefinition.setRemoteService(remoteDevice);
					mDevices.put(deviceDefinition.getName(),
							deviceDefinition);
//					// if
//					// (serviceType.getConcreteTypeName().endsWith("ClockTimer"))
//					// {
//					// rs.fetchService(service);
//					// Object remoteService = rs.getFetchedService(service);
//					// if (remoteService instanceof IClockTimer) {
//					// IClockTimer clock = (IClockTimer) remoteService;
//					// clock.addTimer("1");
//					// }
//					// break;
//					// }
				}
			}
		}
	}

	public static Hashtable getAllDevices() {
		return (mInstance.mDevices);
	}

	private IDevice findDeviceDefinition(RemoteServiceReference service) throws CoreException {
		IDevice device = null;
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor("org.leibnix.admin.core.ui.devices");
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			if (interfaceMatch (element.getAttribute("interface"), service.getServiceInterfaces())){
				device = (IDevice) element.createExecutableExtension("deviceClass");
				device.setName(element.getAttribute("name"));
				device.setType(element.getAttribute("type"));
				device.setCategory(element.getAttribute("Category"));
				break;
			}
//			if (pServiceType.get)(obj))
			// INetwork network = (INetwork) element
			// .createExecutableExtension("networkClass");
		}
		return (device);
	}

	// public static INetwork[] getNetworkDevices(Class mClass) {
	// }

	private boolean interfaceMatch(String pInterface, String[] pServiceInterfaces) {
		boolean ret=false;
		for (String check : pServiceInterfaces) {
			if (check.equals(pInterface)) {
				ret = true;
				break;
			}

		}
		return ret;
	}

	public static DeviceManager getInstance() {
		return mInstance;
	}
}
