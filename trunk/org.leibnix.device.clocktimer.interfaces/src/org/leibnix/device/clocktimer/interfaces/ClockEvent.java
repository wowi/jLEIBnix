package org.leibnix.device.clocktimer.interfaces;

import java.io.Serializable;

import org.leibnix.core.IMessage;

public class ClockEvent implements Serializable {
	public final static int TYPE_CRON=1;
	public final static int TYPE_ONETIME=2;
	
	private String mKey;
	private String mName;
	private int mType = TYPE_CRON;
	private String mExpresss = null;
	private IMessage mMessage = null;
	
	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public int getType() {
		return mType;
	}

	public void setType(int type) {
		mType = type;
	}

	public String getExpresss() {
		return mExpresss;
	}

	public void setExpresss(String expresss) {
		mExpresss = expresss;
	}

	public IMessage getMessage() {
		return mMessage;
	}

	public void setMessage(IMessage message) {
		mMessage = message;
	}

	public void setKey(String pKey) {
		this.mKey = pKey;
	}

	public String getKey() {
		return mKey;
	}

}
