package org.leibnix.admin.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.device.Device;

import ch.ethz.iks.r_osgi.RemoteOSGiException;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;

public class RemoteServiceManager {
	
	private static RemoteServiceManager mInstance = null;
	private static RemoteServiceReference[] mRemoteServiceReference;
	private static RemoteOSGiService mRemoteOSGIService;
	private static HashMap<String, String>mLock = new HashMap<String, String>();
	
	private RemoteServiceManager () {
	}

	private void init () throws RemoteOSGiException, IOException {
		BundleContext context = Activator.getDefaultBundleContext();
		ServiceReference ref = context
				.getServiceReference(RemoteOSGiService.class.getName());
		if (ref != null) {
			mRemoteOSGIService = (RemoteOSGiService) context.getService(ref);
			// RemoteServiceReference[] serviceURLs = rs.connect(new
			// URI("r-osgi://localhost:9278")); // FIXME use remote host from
			// preferences
			mRemoteServiceReference = mRemoteOSGIService.connect(new URI(
					"r-osgi://192.168.178.20:9876")); // FIXME use remote host from
													// preferences
		}

	}
	
	public static RemoteServiceManager getInstance() throws RemoteOSGiException, IOException {
		if (mInstance ==null) {
			synchronized (mLock) {
				if (mInstance==null) {
					mInstance=new RemoteServiceManager();
					mInstance.init();
				}				
			}
		}
		return mInstance;
	}

	public List<RemoteServiceReference> getRemoteServiceReference (String pSearchClass) {
		if (mRemoteServiceReference != null) {
			List<RemoteServiceReference> ret=new ArrayList<RemoteServiceReference>();
			for (RemoteServiceReference serviceReference : mRemoteServiceReference) {
				if (interfaceMatch (pSearchClass, serviceReference.getServiceInterfaces()))  {
					ret.add(serviceReference);
				}
			}
			if (ret.size()>0) {
				return ret;
			}
		}
		return null;
	}

	public Object getRemoteService (RemoteServiceReference pRemoteServiceReference) {
		if (mRemoteServiceReference != null) {
			Object remoteService = mRemoteOSGIService.getRemoteService(pRemoteServiceReference);
			return remoteService;	
		}
		return null;
	}
	
	private boolean interfaceMatch(String pInterface,
			String[] pServiceInterfaces) {
		boolean ret = false;
		for (String check : pServiceInterfaces) {
			if (check.equals(pInterface)) {
				ret = true;
				break;
			}

		}
		return ret;
	}

}
