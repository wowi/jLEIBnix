package org.leibnix.configuration.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.leibnix.configuration.IConfigSet;

public class ConfigSet implements IConfigSet {
	private String mId;
	private List mList = new Vector();
	private int mPos = -1;

	public ConfigSet(String pId) {
		mId = pId;
	}

	public String getId() {
		return mId;
	}

	public boolean hasNext() {
		boolean ret = false;
		if (mPos + 1< mList.size()) {
			ret = true;
		}
		return ret;
	}

	public void next() {
		mPos++;
	}

	public void add(HashMap pValue) {
		mList.add(pValue);
	}

	@Override
	public int getInt(String pValueName) {
		int ret = ((Integer)((HashMap) mList.get(mPos)).get(pValueName)).intValue();
		return (ret);
	}

	@Override
	public String getString(String pValueName) {
		String ret = (String)((HashMap) mList.get(mPos)).get(pValueName);
		return (ret);

	}

	public List getList() {
		return mList;
	}
}
