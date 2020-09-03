package net.zeeraa.novacore.bungeecord.abstraction;

import net.zeeraa.novacore.bungeecord.task.SimpleTask;
import net.zeeraa.novacore.commons.tasks.AbstractSimpleTaskCreator;
import net.zeeraa.novacore.commons.tasks.Task;

public class BungeecordSimpleTaskCreator implements AbstractSimpleTaskCreator {
	@Override
	public Task createTask(Runnable runnable, long delay, long period) {
		return new SimpleTask(runnable, delay, period);
	}
}