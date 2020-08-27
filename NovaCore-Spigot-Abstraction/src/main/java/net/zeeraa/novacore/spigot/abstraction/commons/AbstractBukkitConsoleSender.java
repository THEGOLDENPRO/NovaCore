package net.zeeraa.novacore.spigot.abstraction.commons;

import org.bukkit.Bukkit;

import net.zeeraa.novacore.commons.log.AbstractConsoleSender;

public class AbstractBukkitConsoleSender implements AbstractConsoleSender{
	@Override
	public void sendMessage(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage(message);
	}

	@Override
	public void sendMessage(String[] message) {
		Bukkit.getServer().getConsoleSender().sendMessage(message);
	}
}