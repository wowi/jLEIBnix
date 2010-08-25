package org.leibnix.admin.clocktimer.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;
import org.leibnix.admin.core.ui.views.NodeView;

public class ClearEventList extends ActionDelegate implements
		IObjectActionDelegate {

	private TreeSelection mSelection;
	private IWorkbenchPart mPart;

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		mPart = targetPart;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		mSelection = (TreeSelection) selection;
		super.selectionChanged(action, selection);
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		if (mSelection.getFirstElement() instanceof ClocktimerDevice) {
			ClocktimerDevice clock = (ClocktimerDevice) mSelection.getFirstElement();
			clock.getRemoteClockService().clearEventList();
			if (mPart instanceof NodeView) {
				((NodeView)mPart).getViewer().refresh(clock,true);				
			}
		}
	}

}
