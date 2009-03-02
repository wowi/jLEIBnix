package org.leibnix.admin.core.ui.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IMemento;
//import org.eclipse.ui.navigator.ICommonContentExtensionSite;
//import org.eclipse.ui.navigator.ICommonLabelProvider;
//import org.eclipse.ui.navigator.IDescriptionProvider;

public class DeviceLabelProvider extends LabelProvider implements ILabelProvider {

	public DeviceLabelProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getText(Object element) {
		String ret="Unknown Object Type";
//		if (element instanceof String) {
//			ret=String.valueOf(element);
//		} else if (element instanceof INetwork){
//			INetwork network = (INetwork) element;
//			ret=network.getName();
//		} else if (element instanceof DeviceManager){
//			ret="Devices";
//		} else if (element instanceof IDevice) {
//			IDevice device = (IDevice) element;
//			ret=device.getName();
//		}
		if (element instanceof String) {
			ret=String.valueOf(element);
		} else if (element instanceof IAdaptable) {
			ILabelProvider provider = (ILabelProvider) ((IAdaptable)element).getAdapter(ILabelProvider.class);
			if (provider != null) {
				ret=provider.getText(element);
			}
		}
		return ret;
	}
}
