package net.zeeraa.novacore.module.modules.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.NovaCore;
import net.zeeraa.novacore.log.Log;
import net.zeeraa.novacore.module.modules.game.elimination.PlayerEliminationReason;
import net.zeeraa.novacore.module.modules.game.elimination.PlayerQuitEliminationAction;
import net.zeeraa.novacore.module.modules.game.events.GameEndEvent;
import net.zeeraa.novacore.module.modules.game.events.GameStartEvent;
import net.zeeraa.novacore.module.modules.game.events.PlayerEliminatedEvent;
import net.zeeraa.novacore.module.modules.game.events.PlayerWinEvent;
import net.zeeraa.novacore.module.modules.game.events.TeamEliminatedEvent;
import net.zeeraa.novacore.module.modules.game.events.TeamWinEvent;
import net.zeeraa.novacore.teams.Team;

/**
 * This class represents a game that {@link GameManager} can use
 * 
 * @author Zeeraa
 */
public abstract class Game {
	/**
	 * This is the task id for the winner check.
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected int winCheckTaskId;

	/**
	 * This is used the prevent {@link Game#endGame()} from being called twice
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected boolean endCalled;

	/**
	 * This is used the prevent {@link Game#startGame()} from being called twice
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected boolean startCalled;

	/**
	 * This is false until a winner is found.
	 * <p>
	 * This should never be changed by the game code unless you know what you are
	 * doing
	 */
	protected boolean autoWinnerCheckCompleted;

	/**
	 * List of all players participating in the game
	 */
	protected ArrayList<UUID> players;

	/**
	 * The {@link World} that the game takes place in.
	 * <p>
	 * This can be null before the game has started.
	 * <p>
	 * You can check if this has a value by using {@link Game#hasWorld()}
	 */
	protected World world;

	public Game() {
		this.players = new ArrayList<UUID>();
		this.world = null;
		this.winCheckTaskId = -1;
		this.endCalled = false;
		this.startCalled = false;
		this.autoWinnerCheckCompleted = false;
	}

	/**
	 * Get the name of the game used by plugins
	 * 
	 * @return Name of the game
	 */
	public abstract String getName();

	/**
	 * Get the display name of the game
	 * 
	 * @return Display name of the game
	 */
	public abstract String getDisplayName();

	/**
	 * Get the {@link World} that the game takes place in.
	 * <p>
	 * This can be null before the game has started.
	 * <p>
	 * You can check if this has a value by using {@link Game#hasWorld()}
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
	 * Called when {@link GameManager} loads the game.
	 * <p>
	 * This should not be called outside of {@link GameManager}
	 */
	public void load() {
		onLoad();

		if (winCheckTaskId == -1) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (autoEndGame()) {
						checkWinner();
					}
				}
			}, 4L, 4L);
		}
	}

	/**
	 * Called when {@link GameManager} unloads the game.
	 * <p>
	 * This should not be called outside of {@link GameManager}
	 */
	public void unload() {
		if (winCheckTaskId != -1) {
			Bukkit.getScheduler().cancelTask(winCheckTaskId);
			winCheckTaskId = -1;
		}

		onUnload();
	}

	/**
	 * Called when the game is added to {@link GameManager}.
	 * <p>
	 * Called before registering events.
	 * <p>
	 * Should only be called using {@link Game#load()}
	 */
	public void onLoad() {
	}

	/**
	 * Called when the game is disabled by {@link GameManager}.
	 * <p>
	 * Called after events have been unregistered.
	 * <p>
	 * Should only be called using {@link Game#unload()}
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
	 * seconds (3 minutes)
	 * <p>
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
	 * Set to true to make the game auto end and call
	 * {@link Game#onPlayerWin(OfflinePlayer)} when there is only one player left or
	 * {@link Game#onTeamWin(Team)} when teams are enabled and there is only one
	 * team left.
	 * <p>
	 * If this is disabled you will have to provide your own code to call
	 * {@link Game#onPlayerWin(OfflinePlayer)}, {@link Game#onTeamWin(Team)} and
	 * fire the events in your game code
	 * 
	 * @return <code>true</code> to enable pvp
	 */
	public abstract boolean autoEndGame();

	/**
	 * By default the
	 * {@link Game#onPlayerEliminated(OfflinePlayer, Entity, PlayerEliminationReason, int)}
	 * and the {@link PlayerEliminatedEvent} and the respective team functions and
	 * events wont be called after the game has been ended by
	 * {@link Game#checkWinner()} but by changing this to <code>true</code> the
	 * events will still be called after the game is auto ended by
	 * {@link Game#checkWinner()}.
	 * <p>
	 * This might have a bad effect on other plugins that register score and might
	 * also cause players and teams to be eliminated as first place.
	 * <p>
	 * If you enable this you should also make sure that your plugin can handle a
	 * {@link PlayerEliminatedEvent} or {@link TeamEliminatedEvent} with a placement
	 * of 1 and that your elimination messages does not display a elimination
	 * message with a placement of 1 unless that is what you want.
	 * <p>
	 * This is disabled by default
	 * 
	 * @return <code>true</code> to auto end the game
	 */
	public boolean eliminateAfterAutoWin() {
		return false;
	}

	/**
	 * Check if the game can be started
	 * <p>
	 * This can be used to create games that need to generate a world before
	 * starting to prevent the game from starting before the world is ready
	 * 
	 * @return <code>true</code> if the game can start
	 */
	public boolean canStart() {
		return true;
	}

	/**
	 * Check if the game has started
	 * 
	 * @return <code>true</code> if the game has started
	 */
	public abstract boolean hasStarted();

	/**
	 * Check if the game has ended
	 * 
	 * @return <code>true</code> if the game has ended
	 */
	public abstract boolean hasEnded();

	/**
	 * Check friendly fire is enabled. This wont do anything unless
	 * {@link GameManager#setUseTeams(boolean)} is enabled and
	 * {@link NovaCore#setTeamManager(net.zeeraa.novacore.teams.TeamManager)} is set
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
	 * 
	 * @return <code>false</code> if this has already been called
	 */
	public boolean startGame() {
		if (startCalled) {
			return false;
		}
		startCalled = true;

		Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(this));
		this.onStart();

		return true;
	}

	/**
	 * Called when the game starts
	 */
	public abstract void onStart();

	/**
	 * End the game.
	 * <p>
	 * This should also send all players to the lobby and reset the server
	 * 
	 * @return <code>false</code> if this has already been called
	 */
	public boolean endGame() {
		if (endCalled) {
			return false;
		}
		endCalled = true;

		Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(this));
		this.onEnd();

		return true;
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
	 *         player was not in game or if canceled. This might also return
	 *         <code>false</code> if the game was ended by
	 *         {@link Game#checkWinner()} and {@link Game#eliminateAfterAutoWin()}
	 *         is not enabled
	 */
	public boolean eliminatePlayer(OfflinePlayer player, Entity killer, PlayerEliminationReason reason) {
		if (!players.contains(player.getUniqueId())) {
			Log.trace("Canceling elimination because the player is not in game");
			return false;
		}

		if (autoWinnerCheckCompleted) {
			if (!eliminateAfterAutoWin()) {
				Log.trace("Canceling elimination because the game has ended");
				return false;
			}
		}

		int placement = players.size() - 1;

		PlayerEliminatedEvent playerEliminatedEvent = new PlayerEliminatedEvent(player, killer, reason, placement);

		Bukkit.getServer().getPluginManager().callEvent(playerEliminatedEvent);

		if (playerEliminatedEvent.isCancelled()) {
			Log.trace("Canceling elimination because the elimination event was canceled");
			return false;
		}

		players.remove(player.getUniqueId());

		onPlayerEliminated(player, killer, reason, placement);
		getGameManager().getPlayerEliminationMessage().showPlayerEliminatedMessage(player, killer, reason, placement);

		if (NovaCore.getInstance().getTeamManager() != null) {
			if (getGameManager().isUseTeams()) {
				boolean teamEliminated = true;
				List<UUID> teamsLeft = new ArrayList<UUID>();

				Team team = NovaCore.getInstance().getTeamManager().getPlayerTeam(player);
				if (team != null) {
					for (UUID uuid : players) {
						Team team2 = NovaCore.getInstance().getTeamManager().getPlayerTeam(uuid);
						if (team2 != null) {
							if (team.getTeamUuid() == team2.getTeamUuid()) {
								teamEliminated = false;
							}

							if (!teamsLeft.contains(team2.getTeamUuid())) {
								teamsLeft.add(team2.getTeamUuid());
							}
						}
					}

					if (teamEliminated) {
						int teamPlacement = teamsLeft.size() + 1;
						TeamEliminatedEvent event = new TeamEliminatedEvent(team, teamPlacement);

						Bukkit.getServer().getPluginManager().callEvent(event);

						onTeamEliminated(team, teamPlacement);

						Log.debug("A team was eliminated. Teams left: " + teamsLeft.size());
						Log.trace("Team placement: " + teamPlacement);

						if (getGameManager().getTeamEliminationMessage() != null) {
							getGameManager().getTeamEliminationMessage().showTeamEliminatedMessage(team, teamPlacement);
						}
					}
				}
			}
		}

		if (autoEndGame()) {
			checkWinner();
		}

		return true;
	}

	/**
	 * Check for winner and if a winner is found call
	 * {@link Game#onPlayerWin(OfflinePlayer)} or {@link Game#onTeamWin(Team)}
	 * depending on if {@link GameManager#isUseTeams()} is enabled or not
	 */
	protected void checkWinner() {
		if (autoWinnerCheckCompleted) {
			return;
		}

		if (NovaCore.getInstance().hasTeamManager() && getGameManager().isUseTeams()) {
			List<UUID> teamsLeft = new ArrayList<UUID>();

			for (UUID uuid : players) {
				Team team = NovaCore.getInstance().getTeamManager().getPlayerTeam(uuid);
				if (team != null) {
					if (!teamsLeft.contains(team.getTeamUuid())) {
						teamsLeft.add(team.getTeamUuid());
					}
				}
			}

			if (teamsLeft.size() == 1) {
				Team team = NovaCore.getInstance().getTeamManager().getTeamByTeamUUID(teamsLeft.get(0));
				if (team != null) {
					onTeamWin(team);
					Bukkit.getServer().getPluginManager().callEvent(new TeamWinEvent(team));
				}
			}

			if (teamsLeft.size() <= 1) {
				getGameManager().endGame();
				autoWinnerCheckCompleted = true;
			}
		} else {
			if (players.size() == 1) {
				OfflinePlayer player = Bukkit.getOfflinePlayer(players.get(0));

				if (player != null) {
					onPlayerWin(player);
					Bukkit.getServer().getPluginManager().callEvent(new PlayerWinEvent(player));
				}
			}

			if (players.size() <= 1) {
				getGameManager().endGame();
				autoWinnerCheckCompleted = true;
			}
		}
	}

	/**
	 * Called when a player wins the game.
	 * <p>
	 * This gets called automatically if teams are disabled,
	 * {@link Game#autoEndGame()} is enabled and there is only one player left.
	 * <p>
	 * If called by {@link Game#checkWinner()} this will be called before the
	 * {@link PlayerWinEvent}
	 * 
	 * @param player The {@link OfflinePlayer} that won
	 */
	public void onPlayerWin(OfflinePlayer player) {
	}

	/**
	 * Called when a team wins the game.
	 * <p>
	 * This gets called automatically if teams are enabled,
	 * {@link Game#autoEndGame()} is enabled and there is only one team left.
	 * <p>
	 * If called by {@link Game#checkWinner()} this will be called before the
	 * {@link TeamWinEvent}
	 * 
	 * @param team The {@link Team} that won
	 */
	public void onTeamWin(Team team) {
	}

	/**
	 * Add a player to the game
	 * 
	 * @param player The player to be added
	 * @return <code>true</code> if the player was added
	 */
	public boolean addPlayer(Player player) {
		if (players.contains(player.getUniqueId())) {
			return false;
		}

		Log.debug("Adding player " + player.getName() + " the the game");

		players.add(player.getUniqueId());

		return true;
	}

	/**
	 * Called when a player is eliminated.
	 * <p>
	 * This is called after {@link PlayerEliminatedEvent} and wont be called if the
	 * event is canceled.
	 * <p>
	 * The player is removed from the player list before this is called
	 * 
	 * @param player    The player that was eliminated
	 * @param killer    The entity that killed the player. this will always be
	 *                  <code>null</code> unless the {@link PlayerEliminationReason}
	 *                  is {@link PlayerEliminationReason#KILLED}
	 * @param reason    The {@link PlayerEliminationReason}
	 * @param placement The placement of the player
	 */
	public void onPlayerEliminated(OfflinePlayer player, Entity killer, PlayerEliminationReason reason, int placement) {
	}

	/**
	 * Called when a team is eliminated.
	 * <p>
	 * This is called after {@link TeamEliminatedEvent}
	 * 
	 * @param team      The {@link Team} that was eliminated
	 * @param placement The placement of the team
	 */
	public void onTeamEliminated(Team team, int placement) {
	}

	/**
	 * Called when a player respawns
	 * 
	 * @param player The player that respawned
	 */
	public void onPlayerRespawn(Player player) {
	}

	/**
	 * Set a player to spectator mode
	 * 
	 * @param player the {@link Player} to set to spectator mode
	 */
	public void tpToSpectator(Player player) {
	}

	/**
	 * Get instance of {@link GameManager}
	 * 
	 * @return {@link GameManager} instance
	 */
	public GameManager getGameManager() {
		return GameManager.getInstance();
	}
}