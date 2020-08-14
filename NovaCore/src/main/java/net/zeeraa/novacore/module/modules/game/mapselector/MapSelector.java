package net.zeeraa.novacore.module.modules.game.mapselector;

import java.util.ArrayList;
import java.util.List;

import net.zeeraa.novacore.module.modules.game.map.GameMapData;

/**
 * This class can be used to create map selection systems for games
 * 
 * @author Zeeraa
 */
public abstract class MapSelector {
	protected List<GameMapData> maps;

	public MapSelector() {
		this.maps = new ArrayList<GameMapData>();
	}

	/**
	 * Get a list with all maps
	 * 
	 * @return {@link List} with {@link GameMapData}
	 */
	public List<GameMapData> getMaps() {
		return maps;
	}

	/**
	 * Get a map by name
	 * 
	 * @param name The name of the map
	 * @return The {@link GameMapData} or <code>null</code> if the map was not found
	 */
	public GameMapData getMap(String name) {
		for(GameMapData map : maps) {
			if(map.getMapName().equalsIgnoreCase(name)) {
				return map;
			}
		}
		
		return null;
	}

	/**
	 * Add a map to the possible maps
	 * 
	 * @param map The {@link GameMapData} to be added
	 */
	public void addMap(GameMapData map) {
		maps.add(map);
	}

	/**
	 * Get the map to use for the game
	 * 
	 * @return {@link GameMapData} to use
	 */
	public abstract GameMapData getMapToUse();
}