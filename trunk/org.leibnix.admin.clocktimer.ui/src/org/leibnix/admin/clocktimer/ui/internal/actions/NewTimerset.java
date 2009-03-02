package org.leibnix.admin.clocktimer.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;
import org.leibnix.admin.clocktimer.ui.internal.wizard.NewTimesetWizard;

public class NewTimerset extends ActionDelegate implements IObjectActionDelegate  {

	IWorkbenchPart mPart;
	private TreeSelection mSelection;

	@Override
	public void run(IAction action) {
		NewTimesetWizard wizard=null;
		if (mSelection.getFirstElement() instanceof ClocktimerDevice) {
			wizard = new NewTimesetWizard((ClocktimerDevice)mSelection.getFirstElement());
			// Instantiates the wizard container with the wizard and opens it
			WizardDialog dialog = new WizardDialog( mPart.getSite().getShell(), wizard);
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
