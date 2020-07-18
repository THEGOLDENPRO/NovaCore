package xyz.zeeraa.ezcore.abstraction;

import org.bukkit.command.Command;

public interface BukkitCommandRegistry {
	public void registerCommand(Command command);
}