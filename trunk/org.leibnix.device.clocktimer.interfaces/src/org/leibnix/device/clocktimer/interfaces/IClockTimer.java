package org.leibnix.device.clocktimer.interfaces;

import java.util.List;

public interface IClockTimer {
	boolean addEvent (ClockEvent pTrigger);
	List<ClockEvent> getEvents ();
	boolean clearEventList ();
	boolean removeEvent(ClockEvent event);
	void updateEvent(ClockEvent event);
}
