package org.leibnix.configuration;

import org.leibnix.configuration.internal.ConfigSet;

public class ConfigSetFactory {
	public static IConfigSet newConfigSet (String pConfigId) {
		return (new ConfigSet(pConfigId));
	}
}
