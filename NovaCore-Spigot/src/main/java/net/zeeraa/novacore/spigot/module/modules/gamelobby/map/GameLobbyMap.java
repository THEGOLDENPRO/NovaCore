package net.zeeraa.novacore.spigot.module.modules.gamelobby.map;

import org.bukkit.Location;
import org.bukkit.World;

import net.zeeraa.novacore.spigot.utils.maps.AbstractMap;
import net.zeeraa.novacore.spigot.utils.maps.AbstractMapData;

public class GameLobbyMap extends AbstractMap {
	private Location spawnLocation;
	
	public GameLobbyMap(World world, AbstractMapData mapData, Location spawnLocation) {
		super(world, mapData);
		
		this.spawnLocation = spawnLocation;
	}
	
	/**
	 * Get the spawn location
	 * 
	 * @return the spawn location
	 */
	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public GameLobbyMapData getMapData() {
		return (GameLobbyMapData) abstractMapData;
	}
}