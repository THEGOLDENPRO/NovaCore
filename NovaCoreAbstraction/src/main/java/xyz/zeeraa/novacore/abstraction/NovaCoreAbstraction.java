package xyz.zeeraa.novacore.abstraction;

import org.bukkit.Bukkit;

public class NovaCoreAbstraction {
	public static String getNMSVersion() {
		String packageName = Bukkit.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);
		
		return version;
	}
}