package org.leibnix.core;

import java.io.Serializable;

public class MessageDescription implements Serializable {
	private String mDescription;
	private String mId;
	private String mLabel;

	public MessageDescription (String pId, String pLabel, String pDescription) {
		mId=pId;
		mLabel = pLabel;
		mDescription = pDescription;
	}
}
