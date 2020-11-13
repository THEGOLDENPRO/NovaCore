package net.zeeraa.novacore.spigot.module.modules.game.triggers;

/**
 * The result returned by {@link GameTrigger#trigger()}
 * 
 * @author Zeeraa
 *
 */
public class TriggerResponse {
	private GameTrigger trigger;
	private boolean success;

	public TriggerResponse(GameTrigger trigger, boolean success) {
		this.trigger = trigger;
		this.success = success;
	}

	/**
	 * Check if the trigger was triggered successfully
	 * 
	 * @return <code>true</code> on successful trigger
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Get an instance of the {@link GameTrigger}
	 * 
	 * @return instance of the {@link GameTrigger}
	 */
	public GameTrigger getTrigger() {
		return trigger;
	}

	/**
	 * Get the amount of times the trigger has been called
	 * <p>
	 * Same as {@link GameTrigger#getTriggerCount()}
	 * 
	 * @return amount of times the trigger has been called
	 */
	public int getTriggerCount() {
		return trigger.getTriggerCount();
	}
}