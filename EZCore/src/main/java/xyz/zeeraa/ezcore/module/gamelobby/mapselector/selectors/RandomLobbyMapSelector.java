package xyz.zeeraa.ezcore.module.gamelobby.mapselector.selectors;

import java.util.Random;

import xyz.zeeraa.ezcore.module.game.mapselector.MapSelector;
import xyz.zeeraa.ezcore.module.gamelobby.map.GameLobbyMapData;
import xyz.zeeraa.ezcore.module.gamelobby.mapselector.LobbyMapSelector;

/**
 * This {@link MapSelector} selects a random map form the map list
 * 
 * @author Zeeraa
 */
public class RandomLobbyMapSelector extends LobbyMapSelector {
	@Override
	public GameLobbyMapData getMapToUse() {
		if (maps.size() == 0) {
			return null;
		}

		return maps.get(new Random().nextInt(maps.size()));
	}
}