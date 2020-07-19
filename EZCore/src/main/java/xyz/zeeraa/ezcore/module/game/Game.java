package xyz.zeeraa.ezcore.module.game;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import xyz.zeeraa.ezcore.module.game.events.PlayerEliminatedEvent;

/**
 * This class represents a game that {@link GameManager} can use
 * 
 * @author Zeeraa
 */
public abstract class Game {
	/**
	 * List of all players participating in the game
	 */
	protected ArrayList<UUID> players;

	public Game() {
		players = new ArrayList<UUID>();
	}

	/**
	 * Called when the game is added to {@link GameManager}
	 */
	public void onLoad() {
	}

	/**
	 * Called when the game is disabled by {@link GameManager}
	 */
	public void onUnload() {
	}

	/**
	 * Get a list of all players participating in the game
	 * 
	 * @return {@link ArrayList} with the {@link UUID} of all participants
	 */
	public ArrayList<UUID> getPlayers() {
		return players;
	}

	/**
	 * Get the {@link PlayerQuitEliminationAction} to use when a player quits
	 * 
	 * @return {@link PlayerQuitEliminationAction} to use
	 */
	public abstract PlayerQuitEliminationAction getPlayerQuitEliminationAction();

	/**
	 * Get delay in seconds before eliminating player. The default value is 180
	 * seconds (3 minutes) <br>
	 * <br>
	 * This only works if {@link PlayerQuitEliminationAction} is set to
	 * {@link PlayerQuitEliminationAction#DELAYED}
	 * 
	 * @return delay in seconds before eliminating player
	 */
	public int getPlayerEliminationDelay() {
		return 180;
	}

	/**
	 * Check if a player should be eliminated on death
	 * 
	 * @param player The {@link Player} that died
	 * 
	 * @return <code>true</code> to eliminate player
	 */
	public abstract boolean eliminatePlayerOnDeath(Player player);

	/**
	 * Eliminate a player from the game
	 * 
	 * @param player The player to eliminate
	 * @param killer  killer The entity that killed the player. this can be null
	 * @param reason {@link PlayerEliminationReason} for why the player was eliminated
	 * 
	 * @return <code>true</code> if player was eliminated. <code>false</code> if the player was not in game or if canceled
	 */
	public boolean eliminatePlayer(OfflinePlayer player, Entity killer, PlayerEliminationReason reason) {
		if (!players.contains(player.getUniqueId())) {
			return false;
		}
		
		PlayerEliminatedEvent playerEliminatedEvent = new PlayerEliminatedEvent(player, killer, reason);
		
		Bukkit.getServer().getPluginManager().callEvent(playerEliminatedEvent);
		
		if(playerEliminatedEvent.isCancelled()) {
			return false;
		}
		
		players.remove(player.getUniqueId());
		
		onPlayerEliminated(player, killer, reason);

		return true;
	}

	/**
	 * Called when a player is eliminated. This is called after
	 * {@link PlayerEliminatedEvent} and wont be called if the event is canceled.
	 * The player is removed from the player list before this is called
	 * 
	 * @param player The player that was eliminated
	 * @param killer The entity that killed the player. this will always be
	 *               <code>null</code> unless the {@link PlayerEliminationReason} is
	 *               {@link PlayerEliminationReason#KILLED}
	 * @param reason The {@link PlayerEliminationReason}
	 */
	public void onPlayerEliminated(OfflinePlayer player, Entity killer, PlayerEliminationReason reason) {
	}
}