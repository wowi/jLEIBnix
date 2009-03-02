package org.leibnix.admin.core;

import org.eclipse.core.runtime.IAdaptable;

public interface INetwork extends IAdaptable {
	public String getName();
	public void setName(String pName);
}
