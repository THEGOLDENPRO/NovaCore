package net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.event;

import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.triggers.GameTrigger;

/**
 * Called when a {@link GameTrigger} is triggered
 * @author Zeeraa
 */
public class GameTriggerTriggerEvent  extends GameTriggerEvent {
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	public GameTriggerTriggerEvent(GameTrigger trigger) {
		super(trigger);
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}