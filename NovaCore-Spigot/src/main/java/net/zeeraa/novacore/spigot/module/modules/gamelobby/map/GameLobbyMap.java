package net.zeeraa.novacore.spigot.module.modules.gamelobby.map;

import org.bukkit.Location;
import org.bukkit.World;

import net.zeeraa.novacore.spigot.utils.maps.AbstractMap;

public class GameLobbyMap extends AbstractMap {
	public GameLobbyMap(World world, GameLobbyMapData mapData, Location spawnLocation) {
		super(world, mapData, spawnLocation);
	}
}