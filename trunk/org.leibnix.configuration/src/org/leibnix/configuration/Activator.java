package org.leibnix.configuration;

import org.leibnix.configuration.internal.ConfigurationManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private BundleContext bc;
	private ServiceRegistration configurationService;

	private static final String[] configClasses = new String[] {
		IConfigurationManager.class.getName() };

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		this.bc = context;
		ConfigurationManager config = new ConfigurationManager();
		configurationService = bc.registerService(configClasses, config, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
