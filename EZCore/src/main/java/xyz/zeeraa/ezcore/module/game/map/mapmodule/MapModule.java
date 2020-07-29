package xyz.zeeraa.ezcore.module.game.map.mapmodule;

import org.json.JSONObject;

import xyz.zeeraa.ezcore.module.game.Game;
import xyz.zeeraa.ezcore.module.game.map.GameMap;

/**
 * Represents a configurable part of a map file
 * 
 * @author Zeeraa
 */
public abstract class MapModule {
	public MapModule(JSONObject json) {
	}

	/**
	 * Called when the map has been loaded
	 * 
	 * @param map The {@link GameMap} that was loaded
	 */
	public void onMapLoad(GameMap map) {
	}

	/**
	 * Galled when a game is starting
	 * 
	 * @param game The {@link Game} that is starting
	 */
	public void onGameStart(Game game) {
	}

	/**
	 * Galled when a game is ending
	 * 
	 * @param game The {@link Game} that is ending
	 */
	public void onGameEnd(Game game) {
	}
}