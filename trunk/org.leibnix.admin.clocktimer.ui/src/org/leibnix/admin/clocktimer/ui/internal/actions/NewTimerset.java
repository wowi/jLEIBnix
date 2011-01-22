package org.leibnix.admin.clocktimer.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;
import org.leibnix.admin.core.ui.views.NodeView;
import org.leibnix.admin.util.ui.NewTimesetWizard;
import org.leibnix.core.BooleanValue;
import org.leibnix.core.IMessage;
import org.leibnix.core.Message;
import org.leibnix.core.Variable;
import org.leibnix.device.clocktimer.interfaces.ClockEvent;

public class NewTimerset extends ActionDelegate implements
		IObjectActionDelegate {

	IWorkbenchPart mPart;
	private TreeSelection mSelection;

	@Override
	public void run(IAction action) {
		NewTimesetWizard wizard = null;
		if (mSelection.getFirstElement() instanceof ClocktimerDevice) {
			ClocktimerDevice device = (ClocktimerDevice) mSelection.getFirstElement();
			wizard = new NewTimesetWizard(null, null, true);
			// Instantiates the wizard container with the wizard and opens it
			WizardDialog dialog = new WizardDialog(mPart.getSite().getShell(),
					wizard);
			dialog.create();
			int ret = dialog.open();
			if (ret == WizardDialog.OK) {
				System.out.println(ret);
				System.out.println(wizard.getExpression());
				System.out.println(wizard.getName());
				ClockEvent event = new ClockEvent();
				fillEvent(wizard, event);
				event.setType(ClockEvent.TYPE_CRON);
				IMessage msg = new Message(new Variable("1/0/2", Variable.TYPE_BOOLEAN), null,
						new BooleanValue(true));
				event.setMessage(msg);
				device.getRemoteClockService().addEvent(event);
				if (mPart instanceof NodeView) {
					((NodeView)mPart).getViewer().refresh(device,true);				
				}

			}
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
	
	private void fillEvent(NewTimesetWizard wizard, ClockEvent event) {
		event.setName(wizard.getName());
//		if (wizard.getType()==2) {
			event.setExpresss(wizard.getExpression());
//		}
	}
}
