package xyz.zeeraa.ezcore.module.compass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.module.EZModule;

/**
 * This module is used to set custom compass tracking targets
 * 
 * @author Zeeraa
 */
public class CompassTracker extends EZModule {
	private static CompassTracker instance;
	
	private CompassTrackerTarget compassTrackerTarget;
	private boolean strictMode;

	private int taskId;

	@Override
	public void onLoad() {
		CompassTracker.instance = this;
		this.compassTrackerTarget = null;
		this.strictMode = false;
		this.taskId = -1;
	}

	public static CompassTracker getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		if (this.taskId == -1) {
			this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(EZCore.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (!hasCompassTrackerTarget()) {
						return;
					}

					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (strictMode) {
							if (!p.getInventory().contains(Material.COMPASS)) {
								continue;
							}
						}

						Location target = compassTrackerTarget.getCompassTarget(p);

						if (target != null) {
							p.setCompassTarget(target);
						}
					}
				}
			}, 5L, 5L);
		}
	}

	@Override
	public void onDisable() {
		if (this.taskId != -1) {
			Bukkit.getScheduler().cancelTask(this.taskId);
			this.taskId = -1;
		}
	}

	@Override
	public String getName() {
		return "CompassTracker";
	}

	/**
	 * Get the {@link CompassTrackerTarget} class to use for compass tracking
	 * 
	 * @return Insyance of the {@link CompassTrackerTarget}
	 */
	public CompassTrackerTarget getCompassTrackerTarget() {
		return compassTrackerTarget;
	}

	/**
	 * Set the {@link CompassTrackerTarget} class to use for compass tracking
	 * 
	 * @param compassTrackerTarget The {@link CompassTrackerTarget} class to use
	 */
	public void setCompassTrackerTarget(CompassTrackerTarget compassTrackerTarget) {
		this.compassTrackerTarget = compassTrackerTarget;
	}

	/**
	 * Check if a compass target has been registered
	 * 
	 * @return <code>true</code> if a compass target has been registerd
	 */
	public boolean hasCompassTrackerTarget() {
		return compassTrackerTarget != null;
	}

	/**
	 * Check if strict mode is enabled (Disabled by default). Strict mode requires
	 * the player to have a compass in their inventory before they can use compass
	 * trackers
	 * 
	 * @return <code>true</code> if strict mode is enabled
	 */
	public boolean isStrictMode() {
		return strictMode;
	}

	/**
	 * Enable or disable strict mode (Disabled by default). Strict mode requires the
	 * player to have a compass in their inventory before they can use compass
	 * trackers
	 * 
	 * @param strictMode <code>true</code> to enable strict mode
	 */
	public void setStrictMode(boolean strictMode) {
		this.strictMode = strictMode;
	}
}