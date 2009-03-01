package org.leibnix.core;


public class Target implements ITarget {

	private String mId;
	private int mType;

	public Target (String pId, int pType) {
		mId = pId;
		mType = pType;
	}

	public String getId() {
		return (mId);
	}
}
