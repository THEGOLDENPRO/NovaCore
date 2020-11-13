package net.zeeraa.novacore.spigot.module.modules.game.triggers;

import org.bukkit.event.EventPriority;

import net.zeeraa.novacore.spigot.module.modules.game.events.GameEndEvent;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameStartEvent;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameStartFailureEvent;

/**
 * Different flags that can be added to a {@link GameTrigger} using
 * {@link GameTrigger#addFlag(TriggerFlag)}
 * 
 * @author Zeeraa
 *
 */
public enum TriggerFlag {
	/**
	 * Prevent this from being used with the <code>/game trigger</code> command
	 */
	DENY_TRIGGER_BY_COMMAND(false),
	/**
	 * Only allow this trigger to be executed once
	 */
	RUN_ONLY_ONCE(false),
	/**
	 * Triggers with this flag will automatically trigger when the game starts
	 * <p>
	 * The event used to call this will be {@link GameStartEvent} with
	 * {@link EventPriority#NORMAL}
	 */
	TRIGGER_ON_GAME_START(true),
	/**
	 * Triggers with this flag will automatically trigger when the game ends
	 * <p>
	 * The event used to call this will be {@link GameEndEvent} with
	 * {@link EventPriority#NORMAL}
	 */
	TRIGGER_ON_GAME_END(true),
	/**
	 * Triggers with this flag will automatically trigger when the game fails to
	 * starts
	 * <p>
	 * The event used to call this will be {@link GameStartFailureEvent} with
	 * {@link EventPriority#NORMAL}
	 */
	TRIGGER_ON_GAME_START_FAILURE(true);

	private boolean isAutoTrigger;

	private TriggerFlag(boolean isAutoTrigger) {
		this.isAutoTrigger = isAutoTrigger;
	}

	/**
	 * Check if the trigger is an auto trigger
	 * <p>
	 * Auto triggers will automatically call the trigger whenever a certain event is
	 * triggered
	 * 
	 * @return <code>true</code> if auto trigger
	 */
	public boolean isAutoTrigger() {
		return isAutoTrigger;
	}
}