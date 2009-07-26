package org.leibnix.admin.clocktimer.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;
import org.leibnix.admin.clocktimer.ui.internal.ClockEventWrapper;
import org.leibnix.admin.util.ui.NewTimesetWizard;
import org.leibnix.device.clocktimer.interfaces.ClockEvent;

public class EditTimer extends ActionDelegate implements IObjectActionDelegate {

	private IWorkbenchPart mPart;
	private TreeSelection mSelection;

	@Override
	public void run(IAction action) {
		NewTimesetWizard wizard;
		if (mSelection.getFirstElement() instanceof ClockEventWrapper) {
			ClockEventWrapper eventWrapper = (ClockEventWrapper) mSelection
					.getFirstElement();
			ClockEvent event = eventWrapper.getEvent();
			wizard = new NewTimesetWizard(event.getExpresss(), event.getName(),
					event.isCron());
			// Instantiates the wizard container with the wizard and opens it
			WizardDialog dialog = new WizardDialog(mPart.getSite().getShell(),
					wizard);
			dialog.create();
			dialog.open();
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
