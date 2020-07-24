package xyz.zeeraa.ezcore.module.gamelobby.map;

import java.io.File;
import java.io.IOException;
import org.bukkit.World;

import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.multiverse.MultiverseManager;
import xyz.zeeraa.ezcore.module.multiverse.MultiverseWorld;
import xyz.zeeraa.ezcore.module.multiverse.WorldUnloadOption;
import xyz.zeeraa.ezcore.utils.LocationData;

public class GameLobbyMapData {
	private LocationData spawnLocation;

	private String mapName;
	private String displayName;

	private String description;

	private File worldFile;

	public GameLobbyMapData(LocationData spawnLocation, String mapName, String displayName, String description, File worldFile) {
		this.spawnLocation = spawnLocation;

		this.mapName = mapName;
		this.displayName = displayName;
		this.description = description;

		this.worldFile = worldFile;
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
	 * Get the lobby description
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the world file that will be copied when the lobby is loaded
	 * 
	 * @return The world file
	 */
	public File getWorldFile() {
		return worldFile;
	}
	
	/**
	 * Get the spawn location
	 * 
	 * @return the spawn location
	 */
	public LocationData getSpawnLocation() {
		return spawnLocation;
	}

	/**
	 * Load the lobby as a {@link GameLobbyMap} and load the world into the
	 * multiverse system
	 * 
	 * @return The {@link GameLobbyMap} that was loaded
	 * @throws IOException if the server fails to copy or read the world
	 */
	public GameLobbyMap load() throws IOException {
		EZLogger.info("Loading lobby map " + getMapName() + " display name: " + getDisplayName());
		MultiverseWorld multiverseWorld = MultiverseManager.getInstance().createFromFile(worldFile, WorldUnloadOption.DELETE);

		World world = multiverseWorld.getWorld();

		EZLogger.info("World " + world.getName() + " has been loaded");

		return new GameLobbyMap(world, this, spawnLocation.toLocation(world));
	}
}