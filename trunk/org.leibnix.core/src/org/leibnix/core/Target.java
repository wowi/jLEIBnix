package org.leibnix.core;


public class Target implements ITarget {

	private String mId;
	private String mType;

	public Target (String pId, String pType) {
		mId = pId;
		mType = pType;
	}

	public String getId() {
		return (mId);
	}

	@Override
	public String getType() {
		return mType;
	}
}
