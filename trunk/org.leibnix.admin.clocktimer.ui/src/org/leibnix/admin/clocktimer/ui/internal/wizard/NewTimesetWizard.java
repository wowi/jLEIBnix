package org.leibnix.admin.clocktimer.ui.internal.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.leibnix.admin.clocktimer.ui.ClocktimerDevice;
import org.leibnix.admin.clocktimer.ui.internal.ClockEventWrapper;
import org.leibnix.admin.clocktimer.ui.internal.wizard.page.NewTimerTaskDaylyWizardPage;
import org.leibnix.admin.clocktimer.ui.internal.wizard.page.NewTimerTaskWeeklyWizardPage;
import org.leibnix.admin.clocktimer.ui.internal.wizard.page.NewTimerTaskTypeWizardPage;
import org.leibnix.admin.core.ui.ImageCache;
import org.leibnix.core.BooleanValue;
import org.leibnix.core.IMessage;
import org.leibnix.core.Message;
import org.leibnix.core.Target;
import org.leibnix.device.clocktimer.interfaces.ClockEvent;

public class NewTimesetWizard extends Wizard {

	private ClocktimerDevice mClockTimer;
	private ClockEventWrapper mClockEvent=null;
	
	public NewTimerTaskTypeWizardPage mIntroPage;
	public NewTimerTaskWeeklyWizardPage mWeeklyPage;
	public NewTimerTaskDaylyWizardPage mDaylyPage;



	public NewTimesetWizard(ClocktimerDevice clocktimerDevice) {
		mClockTimer = clocktimerDevice;
		ImageDescriptor defaultIcon =
		      ImageDescriptor.createFromFile(ClocktimerDevice.class, "icons/clock.jpg");
		ImageCache.getInstance().getRegistry().put("clock", defaultIcon);
		this.setDefaultPageImageDescriptor(ImageCache.getInstance().getRegistry().getDescriptor("clock"));
		this.setWindowTitle("Assistent für geplante Task");
	}

	public NewTimesetWizard(ClockEventWrapper clockEventWrapper) {
		mClockEvent=clockEventWrapper;
		mClockTimer = clockEventWrapper.getDevice();
		ImageDescriptor defaultIcon =
		      ImageDescriptor.createFromFile(ClocktimerDevice.class, "icons/clock.jpg");
		ImageCache.getInstance().getRegistry().put("clock", defaultIcon);
		this.setDefaultPageImageDescriptor(ImageCache.getInstance().getRegistry().getDescriptor("clock"));
		this.setWindowTitle("Assistent für geplante Task");
	}

	@Override
	public void addPages() {
		// holidayPage = new HolidayMainPage(workbench, selection);
		// addPage(holidayPage);
		mIntroPage = new NewTimerTaskTypeWizardPage("Task ausführen");
		addPage (mIntroPage);
		mDaylyPage = new NewTimerTaskDaylyWizardPage ("Uhrzeit und Tag");
		addPage(mDaylyPage);
		mWeeklyPage=new NewTimerTaskWeeklyWizardPage ("Uhrzeit und Tag");
		addPage(mWeeklyPage);
		if (mClockEvent!=null) {
			mIntroPage.mType=2;
			mIntroPage.mId=mClockEvent.getEvent().getName();
			mWeeklyPage.mExpression=mClockEvent.getEvent().getExpresss();
		}
	}

	@Override
	public boolean performFinish() {
		ClockEvent event=null;
		if (mClockEvent == null) {
			event = new ClockEvent();
			fillEvent(event);
			event.setType(ClockEvent.TYPE_CRON);
			IMessage msg = new Message(new Target("1/0/2", 0), null,
					new BooleanValue(true));
			event.setMessage(msg);
			mClockTimer.getRemoteClockService().addEvent(event);
		} else {
//			fillEvent(mClockEvent.getEvent());			
//			mClockTimer.getRemoteClockService().updateEvent(mClockEvent.getEvent());
		}
		return true;
	}

	private void fillEvent(ClockEvent event) {
		event.setName(mIntroPage.getId());
		if (mIntroPage.getType()==2) {
			event.setExpresss(mWeeklyPage.mExpression);
		}
	}
}
