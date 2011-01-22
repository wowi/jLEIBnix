package org.leibnix.configuration;

import org.leibnix.configuration.internal.ConfigSet;

public interface IConfigurationManager {

	public IConfigSet getConfigSet (String pConfigId);
	public IConfigSet getConfigSet (String pConfigId, String pFilter);
	public void newConfigSet (String pConfigId, String pConfigDescr);
	public void destroyConfigSet(String string);
	public void insertConfigSet (IConfigSet pConfigSet);
	public boolean deleteConfigSetItems (IConfigSet pConfigSet);
	public void rereadConigSet(IConfigSet newConfigSet);
}
