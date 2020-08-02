package xyz.zeeraa.ezcore.module.modules.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import xyz.zeeraa.ezcore.module.modules.game.Game;

/**
 * Called when a game is ending
 * 
 * @author Zeeraa
 */
public class GameStartEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Game game;

	public GameStartEvent(Game game) {
		this.game = game;
	}

	/**
	 * Get instance of the {@link Game} that is ending
	 * 
	 * @return The {@link Game} that is ending
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