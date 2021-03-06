package org.leibnix.admin.core.ui.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.leibnix.admin.core.DeviceManager;
import org.leibnix.admin.core.IDevice;
import org.leibnix.admin.core.INetwork;
import org.leibnix.admin.core.NetworkManager;
import org.leibnix.admin.core.RepositoryManager;
import org.leibnix.repository.common.Module;
import org.leibnix.repository.common.RepositorySite;

import ch.ethz.iks.r_osgi.RemoteOSGiException;

public class DeviceContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] ret = null;
		if (parentElement instanceof String) {
			ret = DeviceManager.getAllDevices().values().toArray();
		}
		if (parentElement instanceof INetwork) {
			INetwork network = (INetwork) parentElement;
			ITreeContentProvider provider =  (ITreeContentProvider) network.getAdapter(ITreeContentProvider.class);
			if (provider != null) {
				return provider.getChildren(parentElement);
			}
		} else if (parentElement instanceof IDevice) {
			IDevice device = (IDevice) parentElement;
			ITreeContentProvider provider = (ITreeContentProvider) device.getAdapter(ITreeContentProvider.class);
			if (provider != null) {
				return provider.getChildren(parentElement);
			}
		} else if (parentElement instanceof RepositorySite) {
			RepositorySite site = (RepositorySite) parentElement;
			try {
				List<Module> modules = RepositoryManager.getInstance().getModules (site);
				if (modules != null) {
					return modules.toArray();
				}
			} catch (RemoteOSGiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// Get all Root Allements
		ArrayList<Object>objects = new ArrayList<Object>(); 
		if (!NetworkManager.getNetworks().isEmpty()) {
			objects.addAll(NetworkManager.getNetworks().values());
		}
		try {
			List<RepositorySite> repositorySites = RepositoryManager.getInstance().getRepositories();
			if (repositorySites != null) {
				objects.addAll(repositorySites);
			}
		} catch (RemoteOSGiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objects.toArray();
//		return new String[] {"Devices", "Networks"};
	}

	public DeviceContentProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
