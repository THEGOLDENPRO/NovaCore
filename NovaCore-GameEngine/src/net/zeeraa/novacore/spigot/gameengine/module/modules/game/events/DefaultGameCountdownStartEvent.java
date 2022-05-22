package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a game is starting
 * 
 * @author Zeeraa
 */
public class DefaultGameCountdownStartEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public DefaultGameCountdownStartEvent() {
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}