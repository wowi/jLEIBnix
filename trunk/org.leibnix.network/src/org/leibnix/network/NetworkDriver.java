package org.leibnix.network;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.device.Driver;


	public class NetworkDriver implements Driver {

	private String filterSpec = "(&(DEVICE_CATEGORY=Network))";

	private Filter filter;
	BundleContext mContext;

	public NetworkDriver(BundleContext pContext)
			throws InvalidSyntaxException {
		filter = pContext.createFilter(filterSpec);
		mContext = pContext;
	}

	public String attach(ServiceReference pServiceReference) throws Exception {
		INetworkDevice device = (INetworkDevice) mContext
				.getService(pServiceReference);

		System.out.println("Attach Network device");
		System.out.println("try to connect ...");
		device.connect();
		device.initDevices();
		return null;
	}

	public int match(ServiceReference pServiceReference) throws Exception {
		if (filter.match(pServiceReference)) {
			return 2;
		} else {
			return -1;
			// return Device.MATCH_NONE;
		}
	}
}
