package org.leibnix.repository.common;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

public class RepositorySite implements Serializable {
	private String mId;
	private URL mURL;
	private HashMap mResourceMap;

	public RepositorySite(String pId, URL pURL) {
		mId = pId;
		mURL = pURL;
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
	 * @return the mURL
	 */
	public URL getURL() {
		return mURL;
	}
	/**
	 * @param pURL the mURL to set
	 */
	public void setURL(URL pURL) {
		this.mURL = pURL;
	}
	/**
	 * @return the mResourceMap
	 */
	public HashMap getResourceMap() {
		return mResourceMap;
	}
	/**
	 * @param pResourceMap the mResourceMap to set
	 */
	public void setResourceMap(HashMap pResourceMap) {
		this.mResourceMap = pResourceMap;
	}
}
