package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule;

import org.json.JSONObject;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMap;

/**
 * Represents a configurable part of a map file
 * <p>
 * When constructing a map module in the map loader remember to call
 * {@link MapModule#setName(String)} with the name of the map module
 * 
 * @author Zeeraa
 */
public abstract class MapModule {
	private String name;

	public MapModule(JSONObject json) {
		this.name = null;
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

	/**
	 * Set the module name. This is only used by the map reader and should never be
	 * called in any other way
	 * 
	 * @param name The name to set
	 */
	public void setName(String name) {
		if (this.name != null) {
			return;
		}
		this.name = name;
	}

	/**
	 * Get the name of the map module
	 * 
	 * @return Name of the map module
	 */
	public String getName() {
		return name;
	}
}