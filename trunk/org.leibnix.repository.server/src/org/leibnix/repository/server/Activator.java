package org.leibnix.repository.server;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;

import org.leibnix.repository.common.IRepositoryManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

public class Activator implements BundleActivator {

	private static final String[] moduleRepositoryClasses = new String[] { IRepositoryManager.class
			.getName() };

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		// Init Logging 
		ServiceReference ref = context.getServiceReference(LogReaderService.class.getName());
		if (ref != null)
		{
		    LogReaderService reader = (LogReaderService) context.getService(ref);
		    reader.addLogListener(new LogWriter());
		}

		ServiceReference[] allServiceReferences = bundleContext
				.getAllServiceReferences(null, "(objectclass="
						+ org.osgi.service.obr.RepositoryAdmin.class.getName() + ")");
		if (allServiceReferences != null && allServiceReferences.length == 1) {
			Object service = bundleContext.getService(allServiceReferences[0]);
			if (service instanceof org.osgi.service.obr.RepositoryAdmin) {
				org.osgi.service.obr.RepositoryAdmin repositoryAdminService = (org.osgi.service.obr.RepositoryAdmin) bundleContext
						.getService(allServiceReferences[0]);
				ServerRepositoryManager moduleRepository = new ServerRepositoryManager(
						repositoryAdminService);
				File file=new File("bundle/plugins/repository.xml");
				moduleRepository.addRepository(file.toURI().toURL());
				moduleRepository.addRepository(new URL ("http://www.knopflerfish.org/repo/bindex.xml"));
				Hashtable properties = new Hashtable();
				properties.put(RemoteOSGiService.R_OSGi_REGISTRATION,
						Boolean.TRUE);
				bundleContext.registerService(moduleRepositoryClasses,
						moduleRepository, properties);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public class LogWriter implements LogListener
	{
	    // Invoked by the log service implementation for each log entry
	    public void logged(LogEntry entry) 
	    {
	        System.out.println(entry.getMessage());
	    }
	}

}
