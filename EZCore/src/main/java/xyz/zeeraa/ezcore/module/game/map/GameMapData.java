package xyz.zeeraa.ezcore.module.game.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.multiverse.MultiverseManager;
import xyz.zeeraa.ezcore.module.multiverse.MultiverseWorld;
import xyz.zeeraa.ezcore.module.multiverse.WorldUnloadOption;
import xyz.zeeraa.ezcore.utils.LocationData;

/**
 * Represents the data for a map before it has been loaded. You can load it with
 * {@link GameMapData#load()}
 * 
 * @author Zeeraa
 */
public class GameMapData {
	private ArrayList<LocationData> starterLocations;
	private LocationData spectatorLocation;

	private String mapName;
	private String displayName;

	private String description;

	private File worldFile;

	private boolean enabled;

	private String chestLoot;
	private String enderChestLoot;

	public GameMapData(ArrayList<LocationData> starterLocations, LocationData spectatorLocation, String mapName, String displayName, String description, File worldFile, boolean enabled, String chestLoot, String enderChestLoot) {
		this.starterLocations = starterLocations;
		this.spectatorLocation = spectatorLocation;

		this.mapName = mapName;
		this.displayName = displayName;
		this.description = description;

		this.worldFile = worldFile;

		this.enabled = enabled;

		this.chestLoot = chestLoot;
		this.enderChestLoot = enderChestLoot;
	}

	/**
	 * Get a list of starter locations
	 * 
	 * @return List with starter locations
	 */
	public List<LocationData> getStarterLocations() {
		return starterLocations;
	}

	/**
	 * Get the spectator location
	 * 
	 * @return the spectator location
	 */
	public LocationData getSpectatorLocation() {
		return spectatorLocation;
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
	 * Get the description to display to players in the map list
	 * 
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the world file that will be copied when the map is loaded
	 * 
	 * @return The world file
	 */
	public File getWorldFile() {
		return worldFile;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public String getEnderChestLootTable() {
		return enderChestLoot;
	}
	
	public boolean hasEnderChestLootTable() {
		return enderChestLoot != null;
	}
	
	public String getChestLootTable() {
		return chestLoot;
	}
	
	public boolean hasChestLootTable() {
		return chestLoot != null;
	}

	/**
	 * Load the map as a {@link GameMap} and load the world into the multiverse
	 * system
	 * 
	 * @return The {@link GameMap} that was loaded
	 * @throws IOException if the server fails to copy or read the world
	 */
	public GameMap load() throws IOException {
		EZLogger.info("Loading map " + getMapName() + " display name: " + getDisplayName());
		MultiverseWorld multiverseWorld = MultiverseManager.getInstance().createFromFile(worldFile, WorldUnloadOption.DELETE);

		World world = multiverseWorld.getWorld();

		EZLogger.info("World " + world.getName() + " has been loaded");
		
		return new GameMap(world, this, LocationData.toLocations(starterLocations, world), spectatorLocation.toLocation(world));
	}
}