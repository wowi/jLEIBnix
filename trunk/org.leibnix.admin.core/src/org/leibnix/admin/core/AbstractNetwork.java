package org.leibnix.admin.core;

public abstract class AbstractNetwork  implements INetwork{
	
	private String mName;

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}
	
}
