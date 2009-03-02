package org.leibnix.admin.core.ui.internal;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.leibnix.admin.core.DeviceManager;
import org.leibnix.admin.core.IDevice;
import org.leibnix.admin.core.INetwork;
import org.leibnix.admin.core.NetworkManager;

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
		if (!NetworkManager.getNetworks().isEmpty()) {
			return (NetworkManager.getNetworkArray());
		}
		return null;
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
