package org.leibnix.core;

import java.io.Serializable;
import java.util.List;

public class Device implements IBusDevice, Serializable{
	private String mId;
	private boolean mTemplate;
	private List mVariableList;
	private String mNetworkType;

	public Device (String pId, String pNetworkType, boolean pTemplate) {
		mId = pId;
		mNetworkType = pNetworkType;
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
	public String getNetworkType() {
		return mNetworkType;
	}

	/**
	 * @param pType the mType to set
	 */
	public void setNetworkType(String pNetworkType) {
		this.mNetworkType = pNetworkType;
	}

}
