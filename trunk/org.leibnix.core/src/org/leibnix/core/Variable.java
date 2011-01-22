package org.leibnix.core;


public class Variable implements ITarget {

	public static final int TYPE_BOOLEAN = 1;
	
	public static final int TYPE_BOOLEAN_ON_OFF = 1;
	public static final int TYPE_BOOLEAN_UP_DOWN = 2;


	private String mId;
	private int mType;
	private int mSubType;

	/**
	 * @return the mSubType
	 */
	public int getSubType() {
		return mSubType;
	}

	/**
	 * @param pSubType the pSubType to set
	 */
	public void setSubType(int pSubType) {
		mSubType = pSubType;
	}

	public Variable (String pId, int pType) {
		mId = pId;
		mType = pType;
	}

	public String getId() {
		return (mId);
	}

	@Override
	public int getType() {
		return mType;
	}
}
