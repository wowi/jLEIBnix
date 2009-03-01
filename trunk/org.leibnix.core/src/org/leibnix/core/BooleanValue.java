package org.leibnix.core;


public class BooleanValue implements IValue {

	boolean mValue;

	public BooleanValue (boolean pValue) {
		mValue = pValue;
	}

	public BooleanValue (String pValue) {
		mValue = Boolean.parseBoolean(pValue);
	}

	@Override
	public String getValueAsString() {
		return Boolean.toString(mValue);
	}

	@Override
	public int getType() {
		return IValue.TYPE_BOOLEAN;
	}

	@Override
	public String toString() {
		return String.valueOf(mValue);
	}

}
