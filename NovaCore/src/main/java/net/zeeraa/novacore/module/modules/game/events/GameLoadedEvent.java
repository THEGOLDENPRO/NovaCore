package net.zeeraa.novacore.module.modules.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.module.modules.game.Game;

/**
 * Called when a game has been loaded
 * 
 * @author Zeeraa
 */
public class GameLoadedEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Game game;

	public GameLoadedEvent(Game game) {
		this.game = game;
	}

	/**
	 * Get instance of the {@link Game} that was loaded
	 * 
	 * @return The {@link Game} that was loaded
	 */
	public Game getGame() {
		return game;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}