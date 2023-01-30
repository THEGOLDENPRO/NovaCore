package net.zeeraa.novacore.spigot.abstraction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

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
	void registerCommand(Plugin plugin, Command command);

	/**
	 * Get the bukkit {@link CommandMap}
	 * 
	 * @return Instance of the bukkit {@link CommandMap}
	 */
	CommandMap getCommandMap();
	
	/**
	 * Call the syncCommands function in the CraftServer
	 * @return <code>true</code> if this version of spigot supports the function, <code>false</code> otherwise
	 */
	boolean syncCommands();
}