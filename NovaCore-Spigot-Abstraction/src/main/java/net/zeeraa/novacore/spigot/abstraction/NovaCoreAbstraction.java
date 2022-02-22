package net.zeeraa.novacore.spigot.abstraction;

import org.bukkit.Bukkit;

/**
 * This is used to get the NMS version
 * @author Zeeraa
 */
public class NovaCoreAbstraction {
	public static final String getNMSVersion() {
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);

		return version;
	}
}