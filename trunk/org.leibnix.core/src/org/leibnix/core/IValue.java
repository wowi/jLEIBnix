package org.leibnix.core;

import java.io.Serializable;

public interface IValue extends Serializable {

	public int TYPE_BOOLEAN=1;
	public int TYPE_FLOAT=2;
	public int TYPE_STRING=3;

	public String getValueAsString();
	public int getType();
}
