package org.leibnix.admin.clocktimer.ui.internal.wizard.page;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.leibnix.admin.clocktimer.ui.internal.wizard.page.control.DaylyPageControl;

public class NewTimerTaskDaylyWizardPage extends WizardPage {

	public NewTimerTaskDaylyWizardPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Wählen Sie Uhrzeit und Tag aus.");
	}

	public void createControl(Composite parent) {
		setControl (new DaylyPageControl(parent, SWT.NONE));
	}
}
