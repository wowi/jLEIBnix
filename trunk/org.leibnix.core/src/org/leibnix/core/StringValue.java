package org.leibnix.core;


public class StringValue implements IValue {

	private String mValue;

	public StringValue(String pValue) {
		mValue = pValue;
	}

	@Override
	public String getValueAsString() {
		return mValue;
	}

	@Override
	public int getType() {
		return IValue.TYPE_STRING;
	}
}
