package org.leibnix.admin.network.eib.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.leibnix.admin.core.AbstractNetwork;
import org.leibnix.admin.network.eib.ui.internal.EIBNetworkContentProvider;
import org.leibnix.admin.network.eib.ui.internal.EIBNetworkLabelProvider;

public class EIBNetwork extends AbstractNetwork {

	@Override
	public Object getAdapter(Class adapter) {
		Object ret=null;
		if (adapter.getName().equals(ITreeContentProvider.class.getName())) {
			ret = new EIBNetworkContentProvider();			
		} else if (adapter.getName().equals(ILabelProvider.class.getName())) {
			ret = new EIBNetworkLabelProvider();
		}
		return ret;
	}

}
