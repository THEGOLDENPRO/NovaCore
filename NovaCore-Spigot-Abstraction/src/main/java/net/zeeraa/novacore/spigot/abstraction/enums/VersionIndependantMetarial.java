package net.zeeraa.novacore.spigot.abstraction.enums;

import org.bukkit.Material;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils;

/**
 * Represents a bukkit {@link Material} that changed over multiple version of
 * the game
 * 
 * @author Zeeraa
 */
public enum VersionIndependantMetarial {
	FILLED_MAP, END_STONE, WORKBENCH, OAK_BOAT;

	/**
	 * Get the bukkit {@link Material} for the currently used version of the game
	 * 
	 * @return Bukkit {@link Material}
	 */
	public Material toBukkitVersion() {
		return VersionIndependantUtils.get().getMaterial(this);
	}
}