package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule;

import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
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
	private boolean critical;

	public MapModule(JSONObject json) {
		this.name = null;
		this.critical = false;

		if (json.has("is_critical")) {
			this.critical = json.getBoolean("is_critical");
		}
	}

	/**
	 * Called when the map has been loaded
	 * 
	 * @param map The {@link GameMap} that was loaded
	 */
	public void onMapLoad(GameMap map) {
	}

	/**
	 * Called when a game is starting. This gets called before
	 * {@link Game#onStart()}
	 * 
	 * @param game The {@link Game} that is starting
	 */
	public void onGameStart(Game game) {
	}

	/**
	 * Called when the game has started
	 * 
	 * @param game The {@link Game} that has started
	 * @since 2.0.0
	 */
	public void onGameBegin(Game game) {
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
	public final void setName(String name) {
		if (this.name != null) {
			Log.warn("MapModule", "Attempted to set name after name has already been set. This could indicate that MapModule#setName(name) was called outside of the map reader. Please remove any potential custom calls to this function");
			return;
		}
		this.name = name;
	}

	/**
	 * Get the name of the map module
	 * 
	 * @return Name of the map module
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Check if the module was set as critical in the configuration. A critical
	 * module means that if the module fails to enable the game should end with an
	 * error state
	 * <p>
	 * Critical modules are used when failing to load the module would cause an
	 * unplayable state if loading fails
	 * 
	 * @return <code>true</code> if the module was set as critical
	 */
	public boolean isCritical() {
		return critical;
	}

	/**
	 * Get the summary of the module as a string. This should return data useful for
	 * debugging like timers and stored variables. If there is no relative data for
	 * the module you can either skip the implementation of this function or just
	 * return <code>null</code> to indicate that no interesting data exists for this
	 * module
	 * 
	 * @return Important debugging data for this module as a string or
	 *         <code>null</code> if there is no important debugging data to show
	 *         from this module
	 */
	public String getSummaryString() {
		return null;
	}
}