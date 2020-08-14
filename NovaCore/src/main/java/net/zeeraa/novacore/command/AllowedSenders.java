package net.zeeraa.novacore.command;

import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Represents the allowed senders for a command. This is used to prevent some
 * types of {@link CommandSender}s from using the command
 * 
 * @author Zeeraa
 */
public enum AllowedSenders {
	ALL, PLAYERS, CONSOLE, COMMAND_BLOCK, ENTITY, LIVING_ENTITY;

	/**
	 * Check if a {@link CommandSender} is allowed to use the command
	 * 
	 * @param sender The {@link CommandSender}
	 * @return <code>true</code> if allowed
	 */
	public boolean isAllowed(CommandSender sender) {
		switch (this) {
		case CONSOLE:
			if (!(sender instanceof ConsoleCommandSender)) {
				return false;
			}
			break;

		case PLAYERS:
			if (!(sender instanceof Player)) {
				return false;
			}
			break;

		case COMMAND_BLOCK:
			if (!(sender instanceof CommandBlock)) {
				return false;
			}
			break;

		case ENTITY:
			if (!(sender instanceof Entity)) {
				return false;
			}

		case LIVING_ENTITY:
			if (!(sender instanceof LivingEntity)) {
				return false;
			}
			break;

		default:
			break;
		}

		return true;
	}

	/**
	 * Get a message to display to senders not allowed to use this command
	 * 
	 * @return message
	 */
	public String getErrorMessage() {
		switch (this) {
		case PLAYERS:
			return ChatColor.RED + "Only players can use this command";

		case CONSOLE:
			return ChatColor.RED + "Only the console can use this command";

		case COMMAND_BLOCK:
			return ChatColor.RED + "Only command blocks can use this command";

		case ENTITY:
			return ChatColor.RED + "Only entities can use this command";

		case LIVING_ENTITY:
			return ChatColor.RED + "Only living entities can use this command";

		case ALL:
			return ChatColor.RED + "You should be able to use this command but the developers messed up the allowed senders check";

		default:
			break;
		}

		return ChatColor.RED + "You cant use this command for some reason";
	}
}