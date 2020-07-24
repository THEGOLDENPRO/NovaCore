package xyz.zeeraa.ezcore.command;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.log.EZLogger;

/**
 * This class is used to register {@link EZCommand}s
 * 
 * @author Zeeraa
 */
public class CommandRegistry {
	/**
	 * Register a {@link EZCommand}
	 * 
	 * @param command {@link EZCommand} to be registered
	 */
	public static void registerCommand(EZCommand command) {
		EZLogger.info("Registering command " + command.getName());
		registerCommandPermissions(command);
		EZCommandProxy commandProxy = new EZCommandProxy(command);
		EZCore.getInstance().getCommandRegistrator().registerCommand(commandProxy);
	}

	private static void registerCommandPermissions(EZCommandBase command) {
		if (command.hasPermission()) {
			if (Bukkit.getServer().getPluginManager().getPermission(command.getPermission()) == null) {
				Bukkit.getServer().getPluginManager().addPermission(new Permission(command.getPermission(), command.getPermissionDescription(), command.getPermissionDefaultValue()));
			}
		}

		for (EZCommandBase subCommand : command.getSubCommands()) {
			registerCommandPermissions(subCommand);
		}
	}
}