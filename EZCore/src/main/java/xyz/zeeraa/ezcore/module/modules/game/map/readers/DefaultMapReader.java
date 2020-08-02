package xyz.zeeraa.ezcore.module.modules.game.map.readers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.modules.game.map.GameMapData;
import xyz.zeeraa.ezcore.module.modules.game.map.MapReader;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodule.MapModule;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodule.MapModuleManager;
import xyz.zeeraa.ezcore.utils.LocationData;

public class DefaultMapReader extends MapReader {

	@Override
	public GameMapData readMap(JSONObject json, File worldDirectory) {
		String mapName = json.getString("map_name");
		String displayName = json.getString("display_name");
		String worldFileName = json.getString("world_file");
		String description = "";

		boolean enabled = true;

		File worldFile = new File(worldDirectory.getPath() + File.separator + worldFileName);

		if (json.has("description")) {
			description = json.getString("description");
		}

		if (json.has("enabled")) {
			enabled = json.getBoolean("enabled");
		}

		LocationData spectatorLocation = null;
		if (json.has("spectator_location")) {
			spectatorLocation = new LocationData(json.getJSONObject("spectator_location"));
		}

		List<MapModule> mapModules = new ArrayList<MapModule>();

		for (String key : json.keySet()) {
			if(MapModuleManager.hasMapModule(key)) {
				Class<? extends MapModule> clazz = MapModuleManager.getMapModule(key);
				MapModule mapModule;
				try {
					mapModule = MapModuleManager.loadMapModule(clazz, json.getJSONObject(key));
					mapModules.add(mapModule);
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | JSONException e) {
					EZLogger.error("Failed to load MapModule " + clazz.getName() + " from map " + mapName + ". Caused by " + e.getClass().getName());
					e.printStackTrace();
				}
			}
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

		return new GameMapData(mapModules, starterLocations, spectatorLocation, mapName, displayName, description, worldFile, enabled);
	}
}