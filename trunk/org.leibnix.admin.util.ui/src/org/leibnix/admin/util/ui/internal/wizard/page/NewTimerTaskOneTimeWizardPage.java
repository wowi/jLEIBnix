package org.leibnix.admin.util.ui.internal.wizard.page;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.leibnix.admin.util.ui.internal.wizard.page.control.OneTimePageControl;
import org.leibnix.admin.util.ui.internal.wizard.page.control.WeeklyPageControl;

public class NewTimerTaskOneTimeWizardPage extends WizardPage {

	public String mExpression=null;
	private OneTimePageControl mControl;
	
	public NewTimerTaskOneTimeWizardPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Wählen Sie Uhrzeit und Tag aus.");
	}

	public void createControl(Composite parent) {
		mControl = new OneTimePageControl(this, parent, SWT.NONE);
		if (mExpression!=null) {
			// mControl.setExpression(mExpression);
		}
		setControl (mControl);
	}
}
