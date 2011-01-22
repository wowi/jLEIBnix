package org.leibnix.core;


public class Message implements IMessage {

	private IBusDevice mDestination;
	private IValue mValue;

	public Message (IBusDevice pDestination, IBusDevice pSource, IValue pValue) {
		mDestination = pDestination;
		mValue = pValue;
	}

	@Override
	public IBusDevice getDestination() {
		return mDestination;
	}

	@Override
	public IBusDevice getSource() {
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
