package org.leibnix.server.osgi;

import org.leibnix.core.ITarget;
import org.osgi.service.device.Device;

public interface IDevice extends Device {
	public void init ();
	public ITarget[] getTargetObjects ();
}
