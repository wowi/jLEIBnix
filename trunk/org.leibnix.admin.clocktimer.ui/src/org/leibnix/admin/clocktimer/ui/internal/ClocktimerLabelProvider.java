package org.leibnix.admin.clocktimer.ui.internal;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;
import org.leibnix.device.clocktimer.interfaces.ClockEvent;

public class ClocktimerLabelProvider extends LabelProvider implements ILabelProvider {

	@Override
	public String getText(Object element) {
		String ret="Unknow timer device";
		if (element instanceof ClocktimerDevice)  {
			ret = ((ClocktimerDevice)element).getName();
		}
		if (element instanceof ClockEventWrapper) {
			ret = ((ClockEventWrapper) element).getEvent().getName();
		}
		return ret;
	}


}
