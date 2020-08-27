package net.zeeraa.novacore.commons.tasks;

public interface AbstractSimpleTaskCreator {
	public Task createTask(Runnable runnable, long delay, long period);
}