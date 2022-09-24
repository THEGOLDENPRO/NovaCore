package net.zeeraa.novacore.spigot.utils;

import org.bukkit.Bukkit;

public class BukkitUtils {
	public static final boolean hasPlugin(String name) {
		return Bukkit.getServer().getPluginManager().getPlugin(name) != null;
	}
}