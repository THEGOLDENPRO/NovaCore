package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers;

/**
 * Represents a trigger with a scheduled trigger activation
 * 
 * @author Zeeraa
 *
 */
public abstract class ScheduledGameTrigger extends GameTrigger {
	public ScheduledGameTrigger(String name, TriggerCallback callback) {
		super(name, callback);
	}

	public ScheduledGameTrigger(String name) {
		super(name);
	}

	/**
	 * Start the scheduled task
	 * 
	 * @return <code>true</code> if the task was started
	 */
	public abstract boolean start();

	/**
	 * Stop the scheduled task
	 * 
	 * @return <code>true</code> if the task was stopped
	 */
	public abstract boolean stop();

	/**
	 * Check if the trigger has been started and the task is running
	 * 
	 * @return <code>true</code> if running
	 */
	public abstract boolean isRunning();
}