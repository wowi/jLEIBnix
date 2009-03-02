package org.leibnix.admin.core;

import org.eclipse.core.runtime.IAdaptable;

public interface IDevice extends IAdaptable {
	public String getName();
	public void setName(String pName);
	public void setRemoteService (Object pRemoteService);
	public Object getRemoteService ();
	public void setType(String pType);
	public String getType();
	public void setCategory(String pCategory);
	public String getCategory();
}
