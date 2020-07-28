package xyz.zeeraa.ezcore.module.game;

import java.io.IOException;

import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.ModuleManager;
import xyz.zeeraa.ezcore.module.chestloot.ChestLootManager;
import xyz.zeeraa.ezcore.module.game.map.GameMap;
import xyz.zeeraa.ezcore.module.game.map.GameMapData;

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

		if (mapData.hasChestLootTable() || mapData.hasEnderChestLootTable()) {
			if (ModuleManager.isDisabled(ChestLootManager.class)) {
				EZLogger.info("Loading ChestLootManager because the game map has a chest or ender chest loot table");
				ModuleManager.enable(ChestLootManager.class);
			}

			ChestLootManager.getInstance().setChestLootTable(mapData.getChestLootTable());
			ChestLootManager.getInstance().setEnderChestLootTable(mapData.getEnderChestLootTable());
		}

		return true;
	}
}