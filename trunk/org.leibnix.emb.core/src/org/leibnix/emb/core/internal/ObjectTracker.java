package org.leibnix.emb.core.internal;

import org.leibnix.emb.core.IMessageBus;
import org.leibnix.network.INetworkDevice;
import org.leibnix.server.osgi.IDevice;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class ObjectTracker implements ServiceTrackerCustomizer{

	private BundleContext mBC;
	private IMessageBus mEMB;

	public ObjectTracker(BundleContext pBC, IMessageBus pEMB) {
		mBC = pBC;
		mEMB = pEMB;
	}

	public Object addingService(ServiceReference reference) {
		Object service = mBC.getService(reference);
		System.out.println ("Service added: " + service.getClass().getName());
		if (service instanceof INetworkDevice) {
			System.out.println ("EIBNetworkDevice added");
			INetworkDevice device = (INetworkDevice) service;
			device.addListener(mEMB);
		} else if (service instanceof IDevice) {
			System.out.println ("New Device added");
			IDevice idev = (IDevice) service;
			mEMB.addDevice (idev.getTargetObjects(), idev);
		}
		return null;
	}

	public void modifiedService(ServiceReference reference, Object service) {
		System.out.println ("Service modified");

	}

	public void removedService(ServiceReference reference, Object service) {
		System.out.println ("Service removed");

	}

}
