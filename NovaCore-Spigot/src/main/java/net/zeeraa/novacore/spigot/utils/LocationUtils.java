package net.zeeraa.novacore.spigot.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.json.JSONObject;

/**
 * Utils to compare and modify locations
 * 
 * @author Zeeraa
 */
public class LocationUtils {
	public static boolean isOutsideOfBorder(Location location) {
		WorldBorder border = location.getWorld().getWorldBorder();
		double size = border.getSize() / 2;
		Location center = border.getCenter();
		double x = location.getX() - center.getX(), z = location.getZ() - center.getZ();
		return ((x > size || (-x) > size) || (z > size || (-z) > size));
	}

	/**
	 * Check if 2 locations have matching {@link Location#getWorld()},
	 * {@link Location#getBlockX()}, {@link Location#getBlockY()} and
	 * {@link Location#getBlockZ()}
	 * 
	 * @param location1 Location 1 to check
	 * @param location2 Location 2 to check
	 * @return <code>true</code> if matching
	 */
	public static boolean isSameBlock(Location location1, Location location2) {
		if (location1.getWorld().equals(location2.getWorld())) {
			if (location1.getBlockX() == location2.getBlockX()) {
				if (location1.getBlockY() == location2.getBlockY()) {
					if (location1.getBlockZ() == location2.getBlockZ()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static double blockCenter(int block) {
		if (block >= 0) {
			return ((double) block) + 0.5;
		}
		if (block < 0) {
			return ((double) block) - 0.5;
		}
		return block;
	}

	public static JSONObject toJSONObject(Location location) {
		JSONObject result = new JSONObject();

		result.put("x", location.getX());
		result.put("y", location.getY());
		result.put("z", location.getZ());

		result.put("yaw", location.getYaw());
		result.put("pitch", location.getPitch());

		result.put("world", location.getWorld().getName());

		return result;
	}

	public static Location fromJSONObject(JSONObject json) {
		World world = Bukkit.getServer().getWorld(json.getString("world"));

		if (world == null) {
			world = LocationUtils.getFirstAvaliableWorld();
		}

		double x = json.getDouble("x");
		double y = json.getDouble("y");
		double z = json.getDouble("z");

		float yaw = 0;

		if (json.has("yaw")) {
			yaw = json.getFloat("yaw");
		}

		float pitch = 0;

		if (json.has("pitch")) {
			pitch = json.getFloat("pitch");
		}

		return new Location(world, x, y, z, yaw, pitch);
	}

	public static World getFirstAvaliableWorld() {
		return Bukkit.getServer().getWorlds().get(0);
	}
}