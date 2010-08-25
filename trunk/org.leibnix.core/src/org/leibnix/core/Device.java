package org.leibnix.core;

import java.io.Serializable;
import java.util.Vector;

public class Device implements Serializable{
	private String mId;
	private String mType;
	private Vector mSendMessageDescriptionList;
	private Vector mReceiveMessageDescriptionList;
	private boolean mTemplate;

	public Device (String pId, String pType, boolean pTemplate) {
		mId = pId;
		mType = pType;
		mTemplate = pTemplate;
	}
	
	/**
	 * @return the mId
	 */
	public String getId() {
		return mId;
	}

	/**
	 * @param pId the mId to set
	 */
	public void setId(String pId) {
		this.mId = pId;
	}

	/**
	 * @return the mType
	 */
	public String getType() {
		return mType;
	}

	/**
	 * @param pType the mType to set
	 */
	public void setType(String pType) {
		this.mType = pType;
	}

	public void addSendMessageDescription (MessageDescription pMessageDescription) {
		mSendMessageDescriptionList.add(pMessageDescription);
	}
	public void addReceiveMessageDescription (MessageDescription pMessageDescription) {
		mReceiveMessageDescriptionList.add(pMessageDescription);
	}
}
