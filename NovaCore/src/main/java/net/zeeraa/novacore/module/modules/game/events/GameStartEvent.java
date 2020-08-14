package net.zeeraa.novacore.module.modules.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.module.modules.game.Game;

/**
 * Called when a game is starting
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
	 * Get instance of the {@link Game} that is starting
	 * 
	 * @return The {@link Game} that is starting
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