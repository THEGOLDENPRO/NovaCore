package xyz.zeeraa.ezcore.module.game.mapselector;

import java.util.ArrayList;
import java.util.List;

import xyz.zeeraa.ezcore.module.game.map.GameMapData;

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