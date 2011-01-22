package org.leibnix.repository.common;

import java.io.Serializable;

public class Module implements Serializable{
	private String mId;
	private String mLabel;
	private String mVersion;
	private String mRepositoryId;
	
	public Module(String pId, String pPresentationName, String pVersion, String pRepositoryId) {
		mId=pId;
		mLabel=pPresentationName;
		mVersion=pVersion;
		mRepositoryId=pRepositoryId;
	}

	/**
	 * @return the mRepositoryId
	 */
	public String getRepositoryId() {
		return mRepositoryId;
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
	 * @return the mLabel
	 */
	public String getLabel() {
		return mLabel;
	}

	/**
	 * @param pLabel the mLabel to set
	 */
	public void setLabel(String pLabel) {
		this.mLabel = pLabel;
	}

	/**
	 * @return the mVersion
	 */
	public String getVersion() {
		return mVersion;
	}

	/**
	 * @param pVersion the mVersion to set
	 */
	public void setVersion(String pVersion) {
		this.mVersion = pVersion;
	}

}
