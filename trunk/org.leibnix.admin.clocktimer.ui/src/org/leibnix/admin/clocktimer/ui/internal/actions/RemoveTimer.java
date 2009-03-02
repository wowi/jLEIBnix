package org.leibnix.admin.clocktimer.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.leibnix.admin.clocktimer.ui.internal.ClockEventWrapper;

public class RemoveTimer extends ActionDelegate implements IObjectActionDelegate  {

	private IWorkbenchPart mPart;
	private TreeSelection mSelection;

	@Override
	public void run(IAction action) {
		if (mSelection.getFirstElement() instanceof ClockEventWrapper) {
			ClockEventWrapper wrapper = (ClockEventWrapper) mSelection.getFirstElement();
			wrapper.getDevice().getRemoteClockService().removeEvent (wrapper.getEvent());
		}
	}
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		mPart = targetPart;
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		mSelection = (TreeSelection) selection;
		super.selectionChanged(action, selection);
	}

}
