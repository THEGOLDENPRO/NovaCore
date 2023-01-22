package net.zeeraa.novacore.spigot.abstraction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

/**
 * Register a command directly without using plugin.yml
 * 
 * @author Zeeraa
 */
public interface CommandRegistrator {
	/**
	 * Register a command
	 * 
	 * @param command {@link Command} to be registered
	 */
	void registerCommand(Command command);

	/**
	 * Get the bukkit {@link CommandMap}
	 * 
	 * @return Instance of the bukkit {@link CommandMap}
	 */
	CommandMap getCommandMap();
}