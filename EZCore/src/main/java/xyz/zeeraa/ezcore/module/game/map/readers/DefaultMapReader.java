package xyz.zeeraa.ezcore.module.game.map.readers;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import xyz.zeeraa.ezcore.module.game.map.GameMapData;
import xyz.zeeraa.ezcore.module.game.map.MapReader;
import xyz.zeeraa.ezcore.utils.LocationData;

public class DefaultMapReader extends MapReader {

	@Override
	public GameMapData readMap(JSONObject json, File worldDirectory) {
		String mapName = json.getString("map_name");
		String displayName = json.getString("display_name");
		String worldFileName = json.getString("world_file");
		String description = "";
		
		String chestLoot = "";
		String enderChestLoot = "";

		boolean enabled = true;

		File worldFile = new File(worldDirectory.getPath() + File.separator + worldFileName);

		if (json.has("description")) {
			description = json.getString("description");
		}
		
		if (json.has("chest_loot")) {
			chestLoot = json.getString("chest_loot");
		}
		
		if (json.has("ender_chest_loot")) {
			enderChestLoot = json.getString("ender_chest_loot");
		}

		if (json.has("enabled")) {
			enabled = json.getBoolean("enabled");
		}

		LocationData spectatorLocation = null;
		if (json.has("spectator_location")) {
			spectatorLocation = new LocationData(json.getJSONObject("spectator_location"));
		}

		ArrayList<LocationData> starterLocations = new ArrayList<LocationData>();
		if (json.has("starter_locations")) {
			JSONArray starterLocationsJSON = json.getJSONArray("starter_locations");
			for (int i = 0; i < starterLocationsJSON.length(); i++) {
				JSONObject starterLocationJSON = starterLocationsJSON.getJSONObject(i);
				
				LocationData location = new LocationData(starterLocationJSON);
				
				location.center();
				
				starterLocations.add(location);
			}
		}

		return new GameMapData(starterLocations, spectatorLocation, mapName, displayName, description, worldFile, enabled, chestLoot, enderChestLoot);
	}
}