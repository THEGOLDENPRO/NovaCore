package xyz.zeeraa.ezcore.module.modules.game;

import java.io.IOException;

import org.bukkit.Bukkit;

import xyz.zeeraa.ezcore.module.modules.game.events.GameEndEvent;
import xyz.zeeraa.ezcore.module.modules.game.events.GameStartEvent;
import xyz.zeeraa.ezcore.module.modules.game.map.GameMap;
import xyz.zeeraa.ezcore.module.modules.game.map.GameMapData;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodule.MapModule;

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
	 * Get the loaded map. You can check if a map has been loaded using
	 * {@link MapGame#hasActiveMap()}
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
	 * @param map The {@link GameMapData} to load
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
		
		for(MapModule mapModule : map.getMapData().getMapModules()) {
			mapModule.onMapLoad(map);
		}

		return true;
	}
	
	/**
	 * Start the game
	 */
	public void startGame() {
		Bukkit.getServer().getPluginManager().callEvent(new GameStartEvent(this));
		
		for(MapModule module : activeMap.getMapData().getMapModules()) {
			module.onGameStart(this);
		}
		
		this.onStart();
	}
	
	/**
	 * End the game. This should also send all players to the lobby and reset the
	 * server
	 */
	public void endGame() {
		Bukkit.getServer().getPluginManager().callEvent(new GameEndEvent(this));
		
		for(MapModule module : activeMap.getMapData().getMapModules()) {
			module.onGameEnd(this);
		}
		
		this.onEnd();
	}
}