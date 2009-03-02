package org.leibnix.admin.core;

public abstract class AbstractDevice implements IDevice {

	private String mName;
	private String mType;
	private String mCategory;
	private Object mRemoteService=null;
	
	@Override
	public String getName() {
		return mName;
	}

	@Override
	public void setName(String pName) {
		mName = pName;
	}

	public Object getRemoteService() {
		return mRemoteService;
	}

	public void setRemoteService(Object pRemoteService) {
		mRemoteService = pRemoteService;
	}

	public void setType(String pType) {
		this.mType = pType;
	}

	public String getType() {
		return mType;
	}

	public void setCategory(String pCategory) {
		this.mCategory = pCategory;
	}

	public String getCategory() {
		return mCategory;
	}

}
