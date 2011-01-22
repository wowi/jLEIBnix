package org.leibnix.core;

import java.io.Serializable;

public interface IMessage extends Serializable {

	IBusDevice getDestination ();
	IBusDevice getSource();
	IValue getValue ();
	String getValueAsString();
}
