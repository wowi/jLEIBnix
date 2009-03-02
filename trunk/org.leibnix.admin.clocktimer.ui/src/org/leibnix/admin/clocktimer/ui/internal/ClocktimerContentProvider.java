package org.leibnix.admin.clocktimer.ui.internal;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;

public class ClocktimerContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ClocktimerDevice) {
			ClocktimerDevice device = (ClocktimerDevice) parentElement;
//			List events = device.getRemoteClockService().getEvents();
//			return events.toArray();
			return (device.getClockEvents());
		} else {
			return null;
		}
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		boolean ret = false;
		if (element instanceof ClocktimerDevice) {
			ClocktimerDevice device = (ClocktimerDevice) element;
			List events = device.getRemoteClockService().getEvents();
			if (events.size() != 0)
				ret = true;
		}
		return ret;
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
