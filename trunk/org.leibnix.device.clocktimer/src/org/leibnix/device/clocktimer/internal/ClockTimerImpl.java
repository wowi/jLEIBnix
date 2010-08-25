package org.leibnix.device.clocktimer.internal;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.leibnix.configuration.ConfigSetFactory;
import org.leibnix.configuration.IConfigSet;
import org.leibnix.configuration.IConfigurationManager;
import org.leibnix.core.BooleanValue;
import org.leibnix.core.ITarget;
import org.leibnix.core.IValue;
import org.leibnix.core.Target;
import org.leibnix.device.clocktimer.interfaces.ClockEvent;
import org.leibnix.device.clocktimer.interfaces.IClockTimer;
import org.leibnix.emb.core.IMessageBus;
import org.leibnix.server.osgi.IDevice;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

public class ClockTimerImpl implements IClockTimer, IDevice {

	BundleContext mBC;
	private Vector mEventList = new Vector();
	Scheduler mScheduler = null;

	public ClockTimerImpl(BundleContext context, IConfigSet configSet) {
		mBC = context;

		initScheduler(configSet);
	}

	private void initScheduler(IConfigSet configSet) {
		SchedulerFactory schedFact = new StdSchedulerFactory();
		try {
			mScheduler = schedFact.getScheduler();
			mScheduler.start();
			String name;
			int type;
			String expression;
			String target;
			String value;
			String key;
			while (configSet.hasNext()) {
				configSet.next();
				name = configSet.getString("ID");
				type = configSet.getInt("TYPE");
				target = configSet.getString("TARGET_ID");
				expression = configSet.getString("CONFIG_STRING");
				value = configSet.getString("VALUE");
				key = configSet.getString("KEY");
				System.out.println("key: " + key);
				ClockEvent event = addTimer(mScheduler, name, key, type,
						expression, new Target(target, "TARGET_TYPE_EIB"), new BooleanValue(
								value));
			}
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void removeTimer (Scheduler sched, String pJobName) {
		try {
			sched.deleteJob(pJobName, Scheduler.DEFAULT_GROUP);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ClockEvent addTimer(Scheduler sched, String name, String key,
			int type, String expression, ITarget target, IValue value)
			throws ParseException, SchedulerException {
		System.out.println("Add Timer: " + name);
		JobDetail jobDetail = new JobDetail(name, Scheduler.DEFAULT_GROUP,
				Worker.class);
		ServiceReference sr = mBC.getServiceReference(IMessageBus.class
				.getName());
		IMessageBus emb = (IMessageBus) mBC.getService(sr);
		jobDetail.getJobDataMap().put("EMB", emb);
		jobDetail.getJobDataMap().put("TARGET_ID", target);
		jobDetail.getJobDataMap().put("VALUE", value);
		//
		// Trigger trigger = TriggerUtils.makeHourlyTrigger(); // fire
		// every
		// hour
		Trigger trigger = null;
		try {
		trigger = new CronTrigger(name + "_TRIGGER",
				Scheduler.DEFAULT_GROUP, expression);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		// long ts = System.currentTimeMillis() + 4000; // get time a
		// few
		// SimpleTrigger trigger = new SimpleTrigger("trigg1",
		// Scheduler.DEFAULT_GROUP,
		// new Date(ts), null, 0, 0);
		ClockEvent event = new ClockEvent();
		if (trigger != null) {
			sched.scheduleJob(jobDetail, trigger);
			System.out.println("Anzahl Trigger: "
				+ sched.getTriggerNames(Scheduler.DEFAULT_GROUP).length);
		} else {
			event.setStatus (ClockEvent.STATUS_ERROR);
		}
		event = new ClockEvent();
		event.setName(name);
		event.setKey(key);
		event.setExpresss(expression);
		mEventList.add(event);
		return event;
	}

	private void addTimer(ClockEvent event) {
		try {
			addTimer(mScheduler, event.getName(), null, event.getType(), event
					.getExpresss(), event.getMessage().getDestination(), event
					.getMessage().getValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ITarget[] getTargetObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public boolean addEvent(ClockEvent pEvent) {
		// System.out.println("add new Event: " + pEvent.getName());
		// mEventList.add(pEvent);

		Hashtable properties = new Hashtable();

		// this is the hint for R-OSGi that the service
		// is made available for remote access
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		ServiceReference sr = mBC
				.getServiceReference(IConfigurationManager.class.getName());
		IConfigurationManager configManager = (IConfigurationManager) mBC
				.getService(sr);
		IConfigSet newConfigSet = ConfigSetFactory
				.newConfigSet("LEIBNIX_CLOCKTIMER");
		HashMap map = new HashMap();
		map.put("ID", pEvent.getName());
		map.put("KEY", UUID.randomUUID());
		map.put("TYPE", new Integer(pEvent.getType()));
		map.put("CONFIG_STRING", pEvent.getExpresss());
		map.put("TARGET_ID", String.valueOf(pEvent.getMessage()
				.getDestination()));
		map.put("VALUE", pEvent.getMessage().getValue().getValueAsString());
		newConfigSet.add(map);
		configManager.insertConfigSet(newConfigSet);
		configManager.rereadConigSet (newConfigSet);
		// pEvent.setKey()
		addTimer(pEvent);
		return false;
	}

	public void noDriverFound() {
		// TODO Auto-generated method stub

	}

	public List getEvents() {
		System.out.println("GetEvents: " + mEventList.size());
		return mEventList;
	}

	public boolean clearEventList() {
		// this is the hint for R-OSGi that the service
		// is made available for remote access
		Hashtable properties = new Hashtable();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		ServiceReference sr = mBC
				.getServiceReference(IConfigurationManager.class.getName());
		IConfigurationManager configManager = (IConfigurationManager) mBC
				.getService(sr);
		IConfigSet configSet = configManager.getConfigSet("LEIBNIX_CLOCKTIMER");
		while (configSet.hasNext()) {
			configSet.next();
			String id = configSet.getString("ID");
			try {
				mScheduler.deleteJob(id, Scheduler.DEFAULT_GROUP);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		configManager.destroyConfigSet("LEIBNIX_CLOCKTIMER");
		createConfigSet(configManager);
		return false;
	}

	public static void createConfigSet(IConfigurationManager configManager) {
		configManager
				.newConfigSet("LEIBNIX_CLOCKTIMER",
						"KEY,ID String, TYPE int, CONFIG_STRING String, TARGET_ID String, VALUE String");
	}

	public boolean removeEvent(ClockEvent pEvent) {
		ServiceReference sr = mBC
				.getServiceReference(IConfigurationManager.class.getName());
		IConfigurationManager configManager = (IConfigurationManager) mBC
				.getService(sr);

		IConfigSet configSet = ConfigSetFactory
				.newConfigSet("LEIBNIX_CLOCKTIMER");
		HashMap map = new HashMap();
		map.put("KEY", pEvent.getKey());
		configSet.add(map);

		if (configManager.deleteConfigSetItems(configSet)) {
			mEventList.remove(pEvent);
			removeTimer (mScheduler, pEvent.getName());
		}
		return false;
	}

	public void updateEvent(ClockEvent event) {
		// TODO Auto-generated method stub

	}

}
