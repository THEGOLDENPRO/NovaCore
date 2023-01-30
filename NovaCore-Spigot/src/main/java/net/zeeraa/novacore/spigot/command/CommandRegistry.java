package net.zeeraa.novacore.spigot.command;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.permission.PermissionRegistrator;

/**
 * This class is used to register {@link NovaCommand}s
 * 
 * @author Zeeraa
 */
public class CommandRegistry {
	private static List<NovaCommand> registeredCommands = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public static void removePluginCommands(Plugin plugin) {
		registeredCommands.removeIf(c -> c.getOwner().equals(plugin));
		CommandMap commandMap = NovaCore.getInstance().getCommandRegistrator().getCommandMap();
		if (commandMap instanceof SimpleCommandMap) {
			SimpleCommandMap simpleCommandMap = (SimpleCommandMap) commandMap;

			Map<String, Command> knownCommands = NovaCore.getInstance().getCommandRegistrator().tryGetKnownCommandsFromSimpleCommandMap(simpleCommandMap);
			try {
				if (knownCommands == null) {
					Log.trace("CommandRegistry", "This version does not support native way of accessing the knownCommands map. Trying to use reflection to get it");

					Field field = null;
					boolean fieldFound = false;

					try {
						field = simpleCommandMap.getClass().getDeclaredField("knownCommands");
						fieldFound = true;
					} catch (NoSuchFieldException e) {
						Log.trace("CommandRegistry", "field knownCommands not found. attempting to use superclass");
					}

					if (!fieldFound) {
						try {
							field = simpleCommandMap.getClass().getSuperclass().getDeclaredField("knownCommands");
							fieldFound = true;
						} catch (NoSuchFieldException e) {
							Log.trace("CommandRegistry", "field knownCommands not found in superclass. attempting to invoke getKnownCommands");
						}
					}

					if (fieldFound) {
						field.setAccessible(true);
						knownCommands = (Map<String, Command>) field.get(simpleCommandMap);
					}
				}

				if (knownCommands == null) {
					Log.error("CommandRegistry", "All attempts to fetch knownCommands from CommandMap failed. Please open an issue here https://github.com/NovaUniverse/NovaCore/issues and provide logs and info about what fork and version your server is running and we will try to fix this asap");
				} else {
					List<String> toRemove = new ArrayList<>();

					knownCommands.forEach((key, command) -> {
						if (command instanceof NovaCommandProxy) {
							NovaCommandProxy proxy = (NovaCommandProxy) command;
							if (proxy.getOwnerPlugin().equals(plugin)) {
								toRemove.add(key);
								Log.trace("CommandRegistry", "Command " + proxy.getClass().getName() + " should be removed since its owner plugin " + plugin.getName() + " is getting disabled. key: " + key);
							}
						}
					});

					for (String c : toRemove) {
						Command command = knownCommands.remove(c);
						command.unregister(simpleCommandMap);
					}
				}
			} catch (Exception e) {
				Log.error("NovaCore", "Could not unregister commands from SimpleCommandMap. (class: " + simpleCommandMap.getClass().getName() + ") " + e.getClass().getName() + " " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			Log.error("NovaCore", "Failed to unregister commands from plugin " + plugin.getName() + " since the CommandMap " + commandMap.getClass().getName() + " is not supported by NovaCore");
		}
	}

	/**
	 * Attempts to call the syncCommands function on the server
	 * 
	 * @return <code>true</code> if this version has the function.
	 *         <code>false</code> otherwise
	 */
	public static boolean syncCommands() {
		return NovaCore.getInstance().getCommandRegistrator().syncCommands();
	}

	/**
	 * Register a {@link NovaCommand}
	 * <p>
	 * This will fail and return <code>false</code> if that command class file has
	 * already been registered
	 * 
	 * @param command {@link NovaCommand} to be registered
	 * @return <code>true</code> if the command was registered
	 */
	public static boolean registerCommand(NovaCommand command) {
		if (registeredCommands.contains(command)) {
			Log.warn("Tried to register command " + command.getClass().getName() + " but it was already registered");
			return false;
		}

		Log.debug("CommandRegistry", "Registering command " + command.getName());
		CommandRegistry.registerCommandPermissions(command);
		NovaCommandProxy commandProxy = new NovaCommandProxy(command);
		NovaCore.getInstance().getCommandRegistrator().registerCommand(command.getOwner(), commandProxy);

		CommandRegistry.registeredCommands.add(command);

		return true;
	}

	/**
	 * Create an instance of and register a {@link NovaCommand}
	 * <p>
	 * This will fail and return <code>false</code> if that command class file has
	 * already been registered
	 * 
	 * @param clazz The class of the {@link NovaCommand} to register
	 * @throws InstantiationException    .
	 * @throws IllegalAccessException    .
	 * @throws IllegalArgumentException  .
	 * @throws InvocationTargetException .
	 * @throws NoSuchMethodException     .
	 * @throws SecurityException         .
	 * 
	 * @return <code>true</code> if the command was registered
	 */
	public static boolean registerCommand(Class<? extends NovaCommand> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return registerCommand(clazz.getConstructor().newInstance());
	}

	private static void registerCommandPermissions(NovaCommandBase command) {
		if (command.hasPermission()) {
			PermissionRegistrator.registerPermission(command.getPermission(), command.getPermissionDescription(), command.getPermissionDefaultValue());
		}

		command.getSubCommands().forEach(CommandRegistry::registerCommandPermissions);
	}

	/**
	 * Get a list of all registered commands
	 * 
	 * @return {@link List} containing all registered {@link NovaCommand}s
	 */
	public static List<NovaCommand> getRegisteredCommands() {
		return registeredCommands;
	}

	/**
	 * Check if a {@link NovaCommand} has been registered
	 * 
	 * @param command The {@link NovaCommand} to check for
	 * @return <code>true</code> if registered
	 */
	public static boolean isRegistered(NovaCommand command) {
		return CommandRegistry.isRegistered(command.getClass());
	}

	/**
	 * Check if a {@link NovaCommand} has been registered by its class
	 * 
	 * @param clazz The class of the {@link NovaCommand} to check for
	 * @return <code>true</code> if registered
	 */
	public static boolean isRegistered(Class<? extends NovaCommand> clazz) {
		return CommandRegistry.getCommand(clazz) != null;
	}

	/**
	 * Get a registered command by its class
	 * <p>
	 * This will try to match the command basted on {@link Class#getName()}
	 * 
	 * @param clazz The {@link NovaCommand} class
	 * @return the instance of the {@link NovaCommand} it its registered.
	 *         <code>null</code> if the command has not been registered
	 */
	@Nullable
	public static NovaCommand getCommand(Class<? extends NovaCommand> clazz) {
		for (NovaCommand command : registeredCommands) {
			// System.out.println(command.getClass().getName() + " eq " + clazz.getName() +
			// " = " + command.getClass().getName().equalsIgnoreCase(clazz.getName()));
			if (command.getClass().getName().equalsIgnoreCase(clazz.getName())) {
				return command;
			}
		}

		return null;
	}
}