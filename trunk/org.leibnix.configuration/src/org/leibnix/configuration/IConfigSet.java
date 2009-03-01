package org.leibnix.configuration;

import java.math.BigInteger;
import java.util.HashMap;

public interface IConfigSet {

	public boolean hasNext();
	public void next();
	public String getString(String pValueName);
	public int getInt(String pValueName);
	public void add (HashMap pValues);
}
