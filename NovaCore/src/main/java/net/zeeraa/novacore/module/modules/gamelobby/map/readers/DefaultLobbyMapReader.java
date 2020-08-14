package net.zeeraa.novacore.module.modules.gamelobby.map.readers;

import java.io.File;
import org.json.JSONObject;

import net.zeeraa.novacore.module.modules.gamelobby.map.GameLobbyMapData;
import net.zeeraa.novacore.module.modules.gamelobby.map.GameLobbyReader;
import net.zeeraa.novacore.utils.LocationData;

public class DefaultLobbyMapReader extends GameLobbyReader {
	@Override
	public GameLobbyMapData readMap(JSONObject json, File worldDirectory) {
		String mapName = json.getString("map_name");
		String displayName = json.getString("display_name");
		String worldFileName = json.getString("world_file");
		String description = "";
		
		File worldFile = new File(worldDirectory.getPath() + File.separator + worldFileName);
		
		if(json.has("description")) {
			description = json.getString("description");
		}
		
		LocationData spawnLocation = new LocationData(json.getJSONObject("spawn_location"));

		return new GameLobbyMapData(spawnLocation, mapName, displayName, description, worldFile);
	}	
}