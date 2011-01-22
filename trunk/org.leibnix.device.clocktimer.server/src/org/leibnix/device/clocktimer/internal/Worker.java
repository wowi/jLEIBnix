package org.leibnix.device.clocktimer.internal;

import org.leibnix.emb.core.IMessageBus;
import org.leibnix.core.BooleanValue;
import org.leibnix.core.ITarget;
import org.leibnix.core.IValue;
import org.leibnix.core.Message;
import org.leibnix.core.Target;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Worker extends Thread implements Job {

	IMessageBus mEMB;

	public void execute(JobExecutionContext pContext)
			throws JobExecutionException {
		System.out.println ("Execute Job: " + pContext.getJobDetail().getName());
		ITarget target = (ITarget) pContext.getJobDetail().getJobDataMap().get(
				"TARGET_ID");
		IValue value = (IValue) pContext.getJobDetail().getJobDataMap().get(
				"VALUE");
		IMessageBus emb = (IMessageBus) pContext.getJobDetail().getJobDataMap()
				.get("EMB");
		System.out.println("TARGET_ID: " + target);
		System.out.println("VALUE: " + value);
		Message message = new Message(target, null,
				new BooleanValue(value.getValueAsString()));
		emb.send(message);

	}
}
