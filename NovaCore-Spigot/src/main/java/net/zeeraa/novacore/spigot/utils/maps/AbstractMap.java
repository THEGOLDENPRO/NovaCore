package net.zeeraa.novacore.spigot.utils.maps;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public abstract class AbstractMap {
	protected World world;
	protected AbstractMapData abstractMapData;
	protected Location spawnLocation;

	public AbstractMap(World world, AbstractMapData abstractMapData, Location spawnLocation) {
		this.world = world;
		this.abstractMapData = abstractMapData;
		this.spawnLocation = spawnLocation;
	}

	/**
	 * Get the world that the lobby map loaded
	 * 
	 * @return The world for the lobby map
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Return the instance of the {@link AbstractMapData} this object was created
	 * from
	 * 
	 * @return The {@link AbstractMapData} for this map
	 */
	public AbstractMapData getAbstractMapData() {
		return abstractMapData;
	}

	/**
	 * Get the spawn location
	 * 
	 * @return the spawn location
	 */
	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public List<HologramData> getHolograms() {
		return getAbstractMapData().getHolograms();
	}
}