package net.zeeraa.novacore.spigot.utils.maps;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.zeeraa.novacore.spigot.utils.LocationData;

public abstract class AbstractMapData {
	protected String mapName;
	protected String displayName;
	protected String description;

	protected File worldFile;

	protected LocationData spawnLocation;

	protected List<HologramData> holograms;

	public AbstractMapData(LocationData spawnLocation, String mapName, String displayName, String description, File worldFile, List<HologramData> holograms) {
		this.mapName = mapName;
		this.displayName = displayName;

		this.description = description;

		this.worldFile = worldFile;

		this.spawnLocation = spawnLocation;

		this.holograms = holograms;
	}

	/**
	 * Get the map name used by the plugin
	 * 
	 * @return The map name
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * Get the display name that is shown to players
	 * 
	 * @return The display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Get the world file that will be copied when the map is loaded
	 * 
	 * @return The world file
	 */
	public File getWorldFile() {
		return worldFile;
	}

	/**
	 * Get the map description
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the spawn location
	 * 
	 * @return the spawn location
	 */
	public LocationData getSpawnLocation() {
		return spawnLocation;
	}

	public List<HologramData> getHolograms() {
		return holograms;
	}

	public abstract AbstractMap load() throws IOException;
}