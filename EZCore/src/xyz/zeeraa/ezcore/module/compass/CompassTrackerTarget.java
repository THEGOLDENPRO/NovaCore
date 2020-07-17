package xyz.zeeraa.ezcore.module.compass;

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
	 * @return a {@link Location} for the player to track or <code>null</code> to
	 *         not track anything
	 */
	public abstract Location getCompassTarget(Player player);
}