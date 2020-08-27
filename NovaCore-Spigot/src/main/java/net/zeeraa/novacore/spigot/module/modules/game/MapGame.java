package net.zeeraa.novacore.spigot.module.modules.game;

import java.io.IOException;

import org.bukkit.Bukkit;

import net.zeeraa.novacore.spigot.module.modules.game.events.GameEndEvent;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameStartEvent;
import net.zeeraa.novacore.spigot.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.module.modules.game.map.GameMapData;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodule.MapModule;

/**
 * This is the same as {@link Game} but this type of game uses pre made maps for
 * games
 * 
 * @author Zeeraa
 */
public abstract class MapGame extends Game {
	private GameMap activeMap;

	public MapGame() {
		super();

		this.activeMap = null;
	}

	/**
	 * Get the loaded map.
	 * <p>
	 * You can check if a map has been loaded using {@link MapGame#hasActiveMap()}
	 * 
	 * @return The loaded {@link GameMap} or null if no map has been loaded
	 */
	public GameMap getActiveMap() {
		return activeMap;
	}

	/**
	 * Check if a map has been loaded
	 * 
	 * @return <code>true</code> if a map has been loaded
	 */
	public boolean hasActiveMap() {
		return activeMap != null;
	}

	/**
	 * Load a map
	 * 
	 * @param mapData The {@link GameMapData} to load
	 * @return <code>true</code> on success. <code>false</code> the a map has
	 *         already been loaded
	 * @throws IOException if and {@link IOException} occurs while loading the world
	 */
	public boolean loadMap(GameMapData mapData) throws IOException {
		if (hasActiveMap()) {
			return false;
		}

		GameMap map = mapData.load();

		this.activeMap = map;
		this.world = map.getWorld();

		for (MapModule mapModule : map.getMapData().getMapModules()) {
			mapModule.onMapLoad(map);
		}

		return true;
	}

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

		for (MapModule module : activeMap.getMapData().getMapModules()) {
			module.onGameStart(this);
		}

		if (autoEndGame()) {
			winCheckTask.start();
		}

		this.onStart();

		return true;
	}

	/**
	 * End the game.
	 * <p>
	 * This should also send all players to the lobby and reset the server
	 * 
	 * @param reason The {@link GameEndReason} why the game ended
	 * @return <code>false</code> if this has already been called
	 */
	public boolean endGame(GameEndReason reason) {
		if (endCalled) {
			return false;
		}
		endCalled = true;

		winCheckTask.stop();

		Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(this, reason));
		this.onEnd(reason);

		return true;
	}
}