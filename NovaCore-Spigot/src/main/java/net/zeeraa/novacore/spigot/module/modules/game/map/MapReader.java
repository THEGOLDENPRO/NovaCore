package net.zeeraa.novacore.spigot.module.modules.game.map;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.module.modules.game.mapselector.MapSelector;

/**
 * This class is used to load game maps
 * 
 * @author Zeeraa
 */
public abstract class MapReader {
	/**
	 * Try to load all JSON files from a directory as maps and add them to the
	 * active {@link MapSelector} defined by {@link GameManager#getMapSelector()}
	 * 
	 * @param directory      Directory to scan
	 * @param worldDirectory The directory containing the worlds
	 */
	public void loadAll(File directory, File worldDirectory) {
		Log.info("Scanning folder " + directory.getName() + " for maps");
		for (File file : directory.listFiles()) {
			if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
				if (!file.isDirectory()) {
					this.readMap(file, worldDirectory);
				}
			}
		}
	}

	/**
	 * Read {@link GameMapData} from a {@link File} list and add it to the active
	 * {@link MapSelector} defined by {@link GameManager#getMapSelector()}
	 * 
	 * @param file           The {@link File} to read
	 * @param worldDirectory The directory containing the worlds
	 * @return Instance of the {@link GameMapData} or <code>null</code> on failure
	 */
	public GameMapData readMap(File file, File worldDirectory) {
		try {
			Log.info("Reading map from file " + file.getName());

			String data = FileUtils.readFileToString(file, "UTF-8");

			GameMapData map = this.readMap(new JSONObject(data), worldDirectory);

			GameManager.getInstance().getMapSelector().addMap(map);

			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Read {@link GameMapData} from a {@link JSONObject}
	 * 
	 * @param json           The {@link JSONObject} to read
	 * @param worldDirectory The directory containing the worlds
	 * @return Instance of the {@link GameMapData} or <code>null</code> on failure
	 */
	public abstract GameMapData readMap(JSONObject json, File worldDirectory);
}