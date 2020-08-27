package net.zeeraa.novacore.commons.tasks;

/**
 * Represents a task that can be started or canceled
 * 
 * @author Zeeraa
 */
public interface Task {
	/**
	 * Start the task
	 * 
	 * @return <code>true</code> if the task was started
	 */
	public boolean start();

	/**
	 * Stop the task
	 * 
	 * @return <code>true</code> if the task was stopped
	 */
	public boolean stop();

	/**
	 * Check if the task is running
	 * 
	 * @return <code>true</code> if the task is running
	 */
	public boolean isRunning();
}