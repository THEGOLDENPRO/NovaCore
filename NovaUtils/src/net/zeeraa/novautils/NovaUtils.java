package net.zeeraa.novautils;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.permission.PermissionRegistrator;
import net.zeeraa.novautils.commands.InvseeCommand;
import net.zeeraa.novautils.commands.SudoCommand;

public class NovaUtils extends JavaPlugin {
	private static NovaUtils instance;

	public static NovaUtils getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		NovaUtils.instance = this;

		// Permissions
		PermissionRegistrator.registerPermission("novautils.command.sudo.exempt", "Prevent players from running sudo on you", PermissionDefault.FALSE);

		// Commands
		CommandRegistry.registerCommand(new InvseeCommand());
		CommandRegistry.registerCommand(new SudoCommand());
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
	}
}