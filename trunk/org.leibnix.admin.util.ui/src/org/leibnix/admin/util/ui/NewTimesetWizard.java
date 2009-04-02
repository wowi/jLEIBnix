package org.leibnix.admin.util.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.leibnix.admin.util.ui.internal.wizard.page.NewTimerTaskDaylyWizardPage;
import org.leibnix.admin.util.ui.internal.wizard.page.NewTimerTaskTypeWizardPage;
import org.leibnix.admin.util.ui.internal.wizard.page.NewTimerTaskWeeklyWizardPage;

public class NewTimesetWizard extends Wizard {

	public NewTimerTaskTypeWizardPage mIntroPage;
	public NewTimerTaskWeeklyWizardPage mWeeklyPage;
	public NewTimerTaskDaylyWizardPage mDaylyPage;
	private String mExpression;
	private boolean mIsCron;
	private String mName;

	public NewTimesetWizard(String pExpression, String pName, boolean pIsCron) {
		mExpression = pExpression;
		mIsCron = pIsCron;
		mName = pName;
		ImageDescriptor defaultIcon = ImageDescriptor.createFromFile(
				ImageCache.class, "internal/icons/clock.jpg");
		ImageCache.getInstance().getRegistry().put("clock", defaultIcon);
		this.setDefaultPageImageDescriptor(ImageCache.getInstance()
				.getRegistry().getDescriptor("clock"));
		this.setWindowTitle("Assistent für geplante Task");
	}

	@Override
	public void addPages() {
		// holidayPage = new HolidayMainPage(workbench, selection);
		// addPage(holidayPage);
		mIntroPage = new NewTimerTaskTypeWizardPage("Task ausführen");
		addPage(mIntroPage);
		mDaylyPage = new NewTimerTaskDaylyWizardPage("Uhrzeit und Tag");
		addPage(mDaylyPage);
		mWeeklyPage = new NewTimerTaskWeeklyWizardPage("Uhrzeit und Tag");
		addPage(mWeeklyPage);
		if (mIsCron) {
			mIntroPage.mType = 2;
			mIntroPage.mId = mName;
			mWeeklyPage.mExpression = mExpression;
		}
	}

	@Override
	public boolean performFinish() {
		mExpression = mWeeklyPage.mExpression;
		mName = mIntroPage.getId();
		mIsCron = mIntroPage.mType == 2;
		return true;
	}
	
	public String getExpression () {
		return mExpression;
	}

	public String getName () {
		return mName;
	}

	public boolean isCron () {
		return mIsCron;
	}
}
