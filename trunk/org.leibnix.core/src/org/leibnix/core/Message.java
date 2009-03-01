package org.leibnix.core;


public class Message implements IMessage {

	private ITarget mDestination;
	private IValue mValue;

	public Message (ITarget pDestination, ITarget pSource, IValue pValue) {
		mDestination = pDestination;
		mValue = pValue;
	}

	@Override
	public ITarget getDestination() {
		return mDestination;
	}

	@Override
	public ITarget getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Message:");
		buf.append(mDestination.toString());
		buf.append(":");
		buf.append(mValue.toString());
		return buf.toString();
	}

	@Override
	public IValue getValue() {
		return mValue;
	}

	@Override
	public String getValueAsString() {
		return String.valueOf(mValue);
	}


}
