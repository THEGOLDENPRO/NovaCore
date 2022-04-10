package net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.mapselector;

import java.util.ArrayList;
import java.util.List;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMapData;
import net.zeeraa.novacore.spigot.gameengine.module.modules.gamelobby.map.GameLobbyMapData;

/**
 * This class can be used to create map selection systems for lobbies
 * 
 * @author Zeeraa
 */
public abstract class LobbyMapSelector {
	protected List<GameLobbyMapData> maps;

	public LobbyMapSelector() {
		this.maps = new ArrayList<GameLobbyMapData>();
	}

	/**
	 * Get a list with all maps
	 * 
	 * @return {@link List} with {@link GameMapData}
	 */
	public List<GameLobbyMapData> getMaps() {
		return maps;
	}

	/**
	 * Add a map to the possible maps
	 * 
	 * @param map The {@link GameLobbyMapData} to be added
	 */
	public void addMap(GameLobbyMapData map) {
		maps.add(map);
	}

	/**
	 * Get the map to use for the game
	 * 
	 * @return {@link GameMapData} to use
	 */
	public abstract GameLobbyMapData getMapToUse();
}