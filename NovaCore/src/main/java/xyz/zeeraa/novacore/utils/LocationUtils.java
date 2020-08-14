package xyz.zeeraa.novacore.utils;

import org.bukkit.Location;
import org.bukkit.WorldBorder;

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

	public static double blockCenter(int block) {
		if (block >= 0) {
			return ((double) block) + 0.5;
		}
		if (block < 0) {
			return ((double) block) - 0.5;
		}
		return block;
	}
}