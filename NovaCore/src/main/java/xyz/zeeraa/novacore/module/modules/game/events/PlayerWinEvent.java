package xyz.zeeraa.novacore.module.modules.game.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import xyz.zeeraa.novacore.module.modules.game.Game;

/**
 * Called when a player wins a game.
 * <p>
 * This will not be called by NovaCore if {@link Game#autoEndGame()} is disabled
 * 
 * @author Zeeraa
 */
public class PlayerWinEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private OfflinePlayer player;

	public PlayerWinEvent(OfflinePlayer player) {
		this.player = player;
	}

	/**
	 * Get the {@link OfflinePlayer} that won the game
	 * 
	 * @return The winning {@link OfflinePlayer}
	 */
	public OfflinePlayer getPlayer() {
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