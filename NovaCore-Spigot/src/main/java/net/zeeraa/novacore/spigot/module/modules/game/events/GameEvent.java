package net.zeeraa.novacore.spigot.module.modules.game.events;

import org.bukkit.event.Event;

import net.zeeraa.novacore.spigot.module.modules.game.Game;

/**
 * Represents an event related to a game
 * 
 * @author Zeeraa
 */
public abstract class GameEvent extends Event {
	private Game game;

	public GameEvent(Game game) {
		this.game = game;
	}

	/**
	 * Get instance of the {@link Game}
	 * 
	 * @return The {@link Game}
	 */
	public Game getGame() {
		return game;
	}
}