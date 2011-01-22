package org.leibnix.admin.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.leibnix.repository.common.IRepositoryManager;
import org.leibnix.repository.common.Module;
import org.leibnix.repository.common.RepositorySite;

import ch.ethz.iks.r_osgi.RemoteOSGiException;
import ch.ethz.iks.r_osgi.RemoteServiceReference;

public class RepositoryManager {
	private static RepositoryManager mInstance;
	private static HashMap mLock = new HashMap();
	private IRepositoryManager mRepositoryService;

	private RepositoryManager () {
		
	}
	
	public static RepositoryManager getInstance() throws RemoteOSGiException, IOException {
		if (mInstance == null) {
			synchronized (mLock ) {
				if (mInstance ==null) {
					mInstance=new RepositoryManager();
					mInstance.init();
				}				
			}
		}
		return mInstance;
	}
	
	public List<RepositorySite> getRepositories() {
		List<RepositorySite> repositories = mRepositoryService.getRepositories();
		if (repositories != null && repositories.size()>0) {
			return repositories;
		}
		return null;
	}
	
	private void init () throws RemoteOSGiException, IOException {
		List<RemoteServiceReference> remoteServiceReferenceList = RemoteServiceManager.getInstance().getRemoteServiceReference("org.leibnix.repository.common.IRepositoryManager");
		if (remoteServiceReferenceList!=null) {
			mRepositoryService = (IRepositoryManager) RemoteServiceManager.getInstance().getRemoteService(remoteServiceReferenceList.get(0));
		}
	}

	public List<Module> getModules(RepositorySite pRepositorySite) {
		List<Module> modules = mRepositoryService.getModules(pRepositorySite);		
		if (modules != null && modules.size()>0) {
			return modules;
		}
		return null;
	}

	public void installOrUpdateModule(Module pModule) {
		mRepositoryService.installOrUpdateModule(pModule);
		
	}
}
