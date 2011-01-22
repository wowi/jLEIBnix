package org.leibnix.repository.common;

import java.util.List;

public interface IRepositoryManager {
	List<Module> getModules (RepositorySite pRepositorySite);
	List<RepositorySite> getRepositories();
	void installOrUpdateModule (Module pModule);
}
