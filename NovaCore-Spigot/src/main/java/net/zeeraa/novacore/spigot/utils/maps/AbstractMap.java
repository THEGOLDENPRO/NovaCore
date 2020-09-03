package net.zeeraa.novacore.spigot.utils.maps;

import java.util.List;

import org.bukkit.World;

public abstract class AbstractMap {
	protected World world;
	protected AbstractMapData abstractMapData;

	public AbstractMap(World world, AbstractMapData abstractMapData) {
		this.world = world;
		this.abstractMapData = abstractMapData;
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

	public List<HologramData> getHolograms() {
		return getAbstractMapData().getHolograms();
	}
}