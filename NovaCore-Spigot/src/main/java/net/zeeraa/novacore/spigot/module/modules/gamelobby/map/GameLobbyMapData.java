package net.zeeraa.novacore.spigot.module.modules.gamelobby.map;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.World;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseWorld;
import net.zeeraa.novacore.spigot.module.modules.multiverse.WorldUnloadOption;
import net.zeeraa.novacore.spigot.utils.LocationData;
import net.zeeraa.novacore.spigot.utils.maps.AbstractMap;
import net.zeeraa.novacore.spigot.utils.maps.AbstractMapData;
import net.zeeraa.novacore.spigot.utils.maps.HologramData;

public class GameLobbyMapData extends AbstractMapData {
	private LocationData spawnLocation;

	public GameLobbyMapData(LocationData spawnLocation, String mapName, String displayName, String description, File worldFile, List<HologramData> holograms) {
		super(mapName, displayName, description, worldFile, holograms);

		this.spawnLocation = spawnLocation;
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
	public AbstractMap load() throws IOException {
		Log.info("Loading lobby map " + getMapName() + " display name: " + getDisplayName());
		MultiverseWorld multiverseWorld = MultiverseManager.getInstance().createFromFile(worldFile, WorldUnloadOption.DELETE);

		World world = multiverseWorld.getWorld();

		Log.info("World " + world.getName() + " has been loaded");

		initHolograms(world);
		
		return new GameLobbyMap(world, this, spawnLocation.toLocation(world));
	}
}