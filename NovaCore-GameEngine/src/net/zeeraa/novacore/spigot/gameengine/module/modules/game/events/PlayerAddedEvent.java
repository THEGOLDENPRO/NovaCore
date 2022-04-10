package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player is added to a game
 * 
 * @author Zeeraa
 */
public class PlayerAddedEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Player player;

	public PlayerAddedEvent(Player player) {
		this.player = player;
	}

	/**
	 * Get the player that was added to the game
	 * 
	 * @return The {@link Player} that was added
	 */
	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}