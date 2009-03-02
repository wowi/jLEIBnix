package org.leibnix.admin.clocktimer.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.leibnix.admin.clocktimer.ui.internal.ClockEventWrapper;
import org.leibnix.admin.clocktimer.ui.internal.ClocktimerContentProvider;
import org.leibnix.admin.clocktimer.ui.internal.ClocktimerLabelProvider;
import org.leibnix.admin.core.AbstractDevice;
import org.leibnix.device.clocktimer.interfaces.ClockEvent;
import org.leibnix.device.clocktimer.interfaces.IClockTimer;

public class ClocktimerDevice extends AbstractDevice {

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

	public IClockTimer getRemoteClockService () {
		return (IClockTimer) (this.getRemoteService());
	}
	
	public ClockEventWrapper[] getClockEvents() {
		List<ClockEvent> events = ((IClockTimer) (this.getRemoteService())).getEvents();
		Iterator<ClockEvent> it = events.iterator();
		int i=0;
		List<ClockEventWrapper> wrapperList = new Vector<ClockEventWrapper>();
		while (it.hasNext()) {
			ClockEvent clockEvent = (ClockEvent) it.next();
			wrapperList.add(new ClockEventWrapper(clockEvent, this));
			
		}
		return  wrapperList.toArray(new ClockEventWrapper[0]);
	}
}
