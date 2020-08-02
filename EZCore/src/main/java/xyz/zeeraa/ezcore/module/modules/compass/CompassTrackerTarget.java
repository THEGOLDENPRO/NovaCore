package xyz.zeeraa.ezcore.module.modules.compass;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * This class is used to make custom compass targets. Use
 * {@link CompassTracker#setCompassTrackerTarget(CompassTrackerTarget)} to
 * register
 * 
 * @author Zeeraa
 */
public interface CompassTrackerTarget {
	/**
	 * Get a location for a players compass to track
	 * 
	 * @param player The player that is searching for a compass target
	 * @return {@link CompassTarget} with the {@link Location} and a String with a
	 *         message to display
	 */
	public abstract CompassTarget getCompassTarget(Player player);
}