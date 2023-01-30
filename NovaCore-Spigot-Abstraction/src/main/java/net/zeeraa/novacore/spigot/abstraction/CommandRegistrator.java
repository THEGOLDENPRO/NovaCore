package net.zeeraa.novacore.spigot.abstraction;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
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
	 * 
	 * @return <code>true</code> if this version of spigot supports the function,
	 *         <code>false</code> otherwise
	 */
	boolean syncCommands();

	/**
	 * Attempt to extract the {@link Map} of known commands from the
	 * {@link SimpleCommandMap}
	 * 
	 * @param commandMap The {@link SimpleCommandMap} in use
	 * @return {@link Map} with known commands or <code>null</code> if this version
	 *         of spigot does not support this method of extracting the map or if
	 *         the fork uses a modified {@link SimpleCommandMap}
	 */
	Map<String, Command> tryGetKnownCommandsFromSimpleCommandMap(SimpleCommandMap commandMap);
}