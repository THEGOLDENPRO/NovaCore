package net.zeeraa.novacore.spigot.module.modules.gamelobby.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event is called when a player has been teleported to the game lobby
 * 
 * @author Zeeraa
 */
public class PlayerJoinGameLobbyEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private Player player;

	public PlayerJoinGameLobbyEvent(Player player) {
		this.player = player;
	}

	/**
	 * Get the player that was teleported to the game lobby
	 * 
	 * @return The {@link Player}
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