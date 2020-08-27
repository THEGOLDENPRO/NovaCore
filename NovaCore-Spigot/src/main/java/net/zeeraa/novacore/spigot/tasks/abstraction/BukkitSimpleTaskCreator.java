package net.zeeraa.novacore.spigot.tasks.abstraction;

import net.zeeraa.novacore.commons.tasks.AbstractSimpleTaskCreator;
import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;

public class BukkitSimpleTaskCreator implements AbstractSimpleTaskCreator {
	@Override
	public Task createTask(Runnable runnable, long delay, long period) {
		return new SimpleTask(runnable, delay, period);
	}
}