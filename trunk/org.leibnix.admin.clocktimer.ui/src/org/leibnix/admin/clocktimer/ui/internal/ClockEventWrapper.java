package org.leibnix.admin.clocktimer.ui.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;
import org.leibnix.device.clocktimer.interfaces.ClockEvent;

public class ClockEventWrapper implements IAdaptable{

	ClockEvent mEvent;
	private ClocktimerDevice mDevice;

	public ClockEventWrapper(ClockEvent event, ClocktimerDevice pDevice) {
		mEvent = event;
		mDevice = pDevice;
	}
	
	public ClockEvent getEvent() {
		return mEvent;
	}
	
	public ClocktimerDevice getDevice () {
		return mDevice;
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object ret=null;
		if (adapter.getName().equals(ITreeContentProvider.class.getName())) {
			ret = new ClocktimerContentProvider();			
		} else if (adapter.getName().equals(ILabelProvider.class.getName())) {
			ret = new ClocktimerLabelProvider();
		}
		return ret;
	}
}
