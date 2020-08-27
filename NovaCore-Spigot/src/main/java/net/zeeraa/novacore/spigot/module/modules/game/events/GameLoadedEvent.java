package net.zeeraa.novacore.spigot.module.modules.game.events;

import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.module.modules.game.Game;

/**
 * Called when a game has been loaded
 * 
 * @author Zeeraa
 */
public class GameLoadedEvent extends GameEvent {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameLoadedEvent(Game game) {
		super(game);
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}