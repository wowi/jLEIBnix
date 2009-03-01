package org.leibnix.emb.core;

import org.leibnix.emb.core.internal.MessageBusImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private BundleContext bc;
	private ServiceRegistration embService;

	private static final String[] embClasses = new String[] {
		IMessageBus.class.getName() };

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		this.bc = context;
		IMessageBus emb = new MessageBusImpl(context);
		embService = bc.registerService(embClasses, emb, null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
