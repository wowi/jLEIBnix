package org.leibnix.core;

import java.io.Serializable;

public interface IMessage extends Serializable {

	ITarget getDestination ();
	ITarget getSource();
	IValue getValue ();
	String getValueAsString();
}
