package xyz.zeeraa.ezcore.module.game;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.game.events.GameEndEvent;
import xyz.zeeraa.ezcore.module.game.events.GameStartEvent;
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

	/**
	 * The {@link World} that the game takes place in. This can be null before the
	 * game has started. You can check if this has a value by using
	 * {@link Game#hasWorld()}
	 */
	protected World world;

	public Game() {
		this.players = new ArrayList<UUID>();
		this.world = null;
	}

	/**
	 * Get the name of the game used by plugins
	 * @return Name of the game
	 */
	public abstract String getName();

	/**
	 * Get the {@link World} that the game takes place in. This can be null before
	 * the game has started. You can check if this has a value by using
	 * {@link Game#hasWorld()}
	 * 
	 * @return The {@link World} for the game or <code>null</code>
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Check if the world has been set
	 * 
	 * @return <code>true</code> if the world is not null
	 */
	public boolean hasWorld() {
		return world != null;
	}

	/**
	 * Called when the game is added to {@link GameManager}. Called before
	 * registering events
	 */
	public void onLoad() {
	}

	/**
	 * Called when the game is disabled by {@link GameManager}. Called after events
	 * have been unregistered
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
	 * Check if PvP is enabled
	 * 
	 * @return <code>true</code> if players can hit each other
	 */
	public abstract boolean isPVPEnabled();

	/**
	 * Check if the game has started
	 * 
	 * @return <code>true</code> if the game has started
	 */
	public abstract boolean hasStarted();

	/**
	 * Check friendly fire is enabled. This wont do anything unless
	 * {@link GameManager#setUseTeams(boolean)} is enabled and
	 * {@link EZCore#setTeamManager()} is set
	 * 
	 * @return <code>true</code> if friendly fire is enabled
	 */
	public abstract boolean isFriendlyFireAllowed();

	/**
	 * Check if 2 entities can hurt each other
	 * 
	 * @param attacker The attacking {@link LivingEntity}, If the entity is a
	 *                 projectile the entity that fired the projectile will be the
	 *                 attacker
	 * @param target   The target {@link LivingEntity}
	 * @return <code>true</code> if the attack is allowed
	 */
	public abstract boolean canAttack(LivingEntity attacker, LivingEntity target);

	/**
	 * Start the game
	 */
	public void startGame() {
		Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(this));
		this.onStart();
	}
	
	/**
	 * Called when the game starts
	 */
	public abstract void onStart();

	/**
	 * End the game. This should also send all players to the lobby and reset the
	 * server
	 */
	public void endGame() {
		Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(this));
		this.onEnd();
	}
	
	/**
	 * Called when the game ends
	 */
	public abstract void onEnd();

	/**
	 * Eliminate a player from the game
	 * 
	 * @param player The player to eliminate
	 * @param killer killer The entity that killed the player. this can be null
	 * @param reason {@link PlayerEliminationReason} for why the player was
	 *               eliminated
	 * 
	 * @return <code>true</code> if player was eliminated. <code>false</code> if the
	 *         player was not in game or if canceled
	 */
	public boolean eliminatePlayer(OfflinePlayer player, Entity killer, PlayerEliminationReason reason) {
		if (!players.contains(player.getUniqueId())) {
			return false;
		}

		PlayerEliminatedEvent playerEliminatedEvent = new PlayerEliminatedEvent(player, killer, reason);

		Bukkit.getServer().getPluginManager().callEvent(playerEliminatedEvent);

		if (playerEliminatedEvent.isCancelled()) {
			return false;
		}

		players.remove(player.getUniqueId());

		onPlayerEliminated(player, killer, reason);

		return true;
	}

	/**
	 * Add a player to the game and call {@link Game#teleportPlayer(Player)} on them
	 * 
	 * @param player The player to be added
	 * @return <code>true</code> if the player was added
	 */
	public boolean addPlayer(Player player) {
		if (players.contains(player.getUniqueId())) {
			return false;
		}
		
		EZLogger.debug("Adding player " + player.getName() + " the the game");

		players.add(player.getUniqueId());

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