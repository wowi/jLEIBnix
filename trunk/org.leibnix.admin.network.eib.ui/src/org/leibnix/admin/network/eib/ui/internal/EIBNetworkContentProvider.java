package org.leibnix.admin.network.eib.ui.internal;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.leibnix.admin.core.DeviceManager;
import org.leibnix.admin.core.INetwork;

public class EIBNetworkContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] ret = null;
		// if (parentElement instanceof INetwork) {
		// ret = new Object [] {DeviceManager.getInstance()};
		// } else if (parentElement instanceof DeviceManager) {
		// if (DeviceManager.getAllDevices().size() != 0) {
		// ret = DeviceManager.getAllDevices().values().toArray();
		// }
		// }
		if (parentElement instanceof INetwork) {
			ret = DeviceManager.getAllDevices().values().toArray();
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
		// TODO Auto-generated method stub
		return null;
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
