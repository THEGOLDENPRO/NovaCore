package net.zeeraa.novacore.spigot.module.modules.game.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseWorld;
import net.zeeraa.novacore.spigot.module.modules.multiverse.WorldUnloadOption;
import net.zeeraa.novacore.spigot.utils.LocationData;

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

	private List<MapModule> mapModules;
	
	private boolean enabled;

	public GameMapData(List<MapModule> mapModules, ArrayList<LocationData> starterLocations, LocationData spectatorLocation, String mapName, String displayName, String description, File worldFile, boolean enabled) {
		this.mapModules = mapModules;
		
		this.starterLocations = starterLocations;
		this.spectatorLocation = spectatorLocation;

		this.mapName = mapName;
		this.displayName = displayName;
		this.description = description;

		this.worldFile = worldFile;

		this.enabled = enabled;
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
	
	public List<MapModule> getMapModules() {
		return mapModules;
	}
	
	public MapModule getMapModule(Class<? extends MapModule> clazz) {
		for(MapModule mapModule : mapModules) {
			if(mapModule.getClass().isAssignableFrom(clazz)) {
				return mapModule;
			}
		}
		
		return null;
	}

	/**
	 * Load the map as a {@link GameMap} and load the world into the multiverse
	 * system
	 * 
	 * @return The {@link GameMap} that was loaded
	 * @throws IOException if the server fails to copy or read the world
	 */
	public GameMap load() throws IOException {
		Log.info("Loading map " + getMapName() + " display name: " + getDisplayName());
		MultiverseWorld multiverseWorld = MultiverseManager.getInstance().createFromFile(worldFile, WorldUnloadOption.DELETE);

		World world = multiverseWorld.getWorld();

		Log.info("World " + world.getName() + " has been loaded");
		
		return new GameMap(world, this, LocationData.toLocations(starterLocations, world), spectatorLocation.toLocation(world));
	}
}