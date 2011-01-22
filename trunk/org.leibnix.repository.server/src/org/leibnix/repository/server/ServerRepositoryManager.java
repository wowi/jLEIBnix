package org.leibnix.repository.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.leibnix.repository.common.IRepositoryManager;
import org.leibnix.repository.common.Module;
import org.leibnix.repository.common.RepositorySite;
import org.osgi.framework.Bundle;
import org.osgi.service.obr.Repository;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Requirement;
import org.osgi.service.obr.Resolver;
import org.osgi.service.obr.Resource;

public class ServerRepositoryManager implements IRepositoryManager {

	private RepositoryAdmin mRepositoryAdmin = null;

	public ServerRepositoryManager(RepositoryAdmin pRepositoryAdminService) {
		mRepositoryAdmin = pRepositoryAdminService;
	}

	public void addRepository (URL pURL) throws MalformedURLException, Exception {
		mRepositoryAdmin.addRepository(pURL);
	}
	
	@Override
	public List<RepositorySite> getRepositories() {
		Repository[] listRepositories = mRepositoryAdmin.listRepositories();
		List<RepositorySite> ret=new ArrayList<RepositorySite>();
		for (Repository repository : listRepositories) {
			System.out.println(repository.getName());
			RepositorySite repositorySite = new RepositorySite(repository.getName(), repository.getURL());
			ret.add(repositorySite);
		}
		if (ret.size()>0) {
			return ret;
		}
		return null;
	}

	@Override
	public List<Module> getModules(RepositorySite pRepositorySite) {
		Repository repository = findRepository(pRepositorySite);
		if (repository != null) {
			Resource[] resourceArray = repository.getResources();
			List<Module> ret=new ArrayList<Module>();
			for (Resource resource : resourceArray) {
				System.out.println(resource.getId());				
				Module module = new Module (resource.getId(), resource.getPresentationName(), resource.getVersion().toString(), pRepositorySite.getId());
				ret.add(module);
			}
			if (ret.size()>0) {
				return ret;
			}
		}
		
		return null;
	}
	
	
	
	public Repository findRepository(RepositorySite pRepositorySite) {
		Repository[] listRepositories = mRepositoryAdmin.listRepositories();
		List<RepositorySite> ret=new ArrayList<RepositorySite>();
		for (Repository repository : listRepositories) {
			if (repository.getName().equals(pRepositorySite.getId())) {
				return repository;
			}
		}
		return null;
	}

	@Override
	public void installOrUpdateModule(Module pModule) {
		Resolver resolver = mRepositoryAdmin.resolver();
		Bundle[] bundles = Activator.getContext().getBundles();
		for (Bundle bundle : bundles) {
			System.out.println(bundle.getSymbolicName());
		}
		String filter="(&(id="+pModule.getId() +"))";
		Resource[] resource = mRepositoryAdmin.discoverResources(filter);
		resolver.add(resource[0]);
		if (resolver.resolve()) {
			resolver.deploy(true);
			
		} else {
		    Requirement[] reqs = resolver.getUnsatisfiedRequirements();
		    for (int i = 0; i < reqs.length; i++)
		    {
		        System.out.println("Unable to resolve: " + reqs[i].getName());
		        System.out.println("Unable to resolve: " + reqs[i].getComment());
		    }
		}

		
	}

}
