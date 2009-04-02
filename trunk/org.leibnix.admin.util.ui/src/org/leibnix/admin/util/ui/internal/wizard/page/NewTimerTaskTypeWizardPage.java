package org.leibnix.admin.util.ui.internal.wizard.page;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.leibnix.admin.util.ui.NewTimesetWizard;
import org.leibnix.admin.util.ui.internal.wizard.page.control.IntroPageControl;

public class NewTimerTaskTypeWizardPage extends WizardPage {

	public int mType=-1;
	public String mId=null;
	
	public NewTimerTaskTypeWizardPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Bitte wählen sie aus, zu welchem Zeitpunkt die Task gestartet werden soll");
	}

	public void createControl(Composite parent) {
		setControl (new IntroPageControl(this, parent, SWT.NONE));
	}

	@Override
	public IWizardPage getNextPage() {
		if (mType==1) {
			return ((NewTimesetWizard)this.getWizard()).mDaylyPage;
		} else if (mType==2) {
			return ((NewTimesetWizard)this.getWizard()).mWeeklyPage;
		}
		return super.getNextPage();
	}

	@Override
	public boolean isPageComplete() {
		if (mId == null || mId.equals ("")) {
			return (false);
		}
		return super.isPageComplete();
	}

	public String getId() {
		return mId;
	}

	public int getType() {
		return mType;
	}

	public void setType(int pType) {
		mType = pType;
	}
	
}
