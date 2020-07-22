package xyz.zeeraa.ezcore.module.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.module.EZModule;

/**
 * Module used to make games
 * 
 * @author Zeeraa
 */
public class GameManager extends EZModule implements Listener {
	private static GameManager instance;
	private Game activeGame;

	/**
	 * Get instance of {@link GameManager}
	 * 
	 * @return Instance
	 */
	public static GameManager getInstance() {
		return instance;
	}

	public GameManager() {
		this.activeGame = null;
		instance = this;
	}

	@Override
	public String getName() {
		return "GameManager";
	}

	/**
	 * Get the {@link Game} that has been loaded
	 * 
	 * @return Instance of the active {@link Game}
	 */
	public Game getActiveGame() {
		return activeGame;
	}

	/**
	 * Check if a game is loaded
	 * 
	 * @return <code>true</code> if a game has been loaded
	 */
	public boolean hasGame() {
		return this.activeGame != null;
	}

	/**
	 * Load a {@link Game} and register listeners
	 * 
	 * @param game {@link Game} to be loaded
	 * @return <code>true</code> on success
	 */
	public boolean loadGame(Game game) {
		if (this.hasGame()) {
			return false;
		}

		if (game instanceof Listener) {
			Bukkit.getPluginManager().registerEvents((Listener) game, EZCore.getInstance());
		}

		return true;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (hasGame()) {
			Player p = e.getPlayer();
			
			switch (activeGame.getPlayerQuitEliminationAction()) {
			case NONE:
				break;
				
			case INSTANT:
				activeGame.eliminatePlayer(p, null, PlayerEliminationReason.QUIT);
				break;
				
			case DELAYED:
				//TODO: Delayed elimination
				break;

			default:
				break;
			}
		}

	}
}