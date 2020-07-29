package xyz.zeeraa.ezcore.command.commands.ezcore.logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.log.LogLevel;

public class EZCoreSubCommandLoggerSet extends EZSubCommand {
	public EZCoreSubCommandLoggerSet() {
		super("set");
		this.setPermission("ezcore.ezcore.logger.ser");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setDescription("Set your log level");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Missing argument: Log level");
		}

		LogLevel level;
		try {
			level = LogLevel.valueOf(args[0]);
		} catch (Exception e) {
			String valid = "";
			for (LogLevel l : LogLevel.values()) {
				valid += l.name() + " ";
			}
			sender.sendMessage(ChatColor.RED + "Invalid log level. Valid values are " + ChatColor.GREEN + valid);
			return false;
		}

		if (sender instanceof Player) {
			EZLogger.getSubscribedPlayers().put(((Player) sender).getUniqueId(), level);
		} else if (sender instanceof ConsoleCommandSender) {
			EZCore.getInstance().setLogLevel(level);
		} else {
			sender.sendMessage(ChatColor.RED + "This command can only be used by players and the console");
			return false;
		}

		sender.sendMessage(ChatColor.GREEN + "Your log level has been set to " + ChatColor.AQUA + level.name());

		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 0) {
			return ImmutableList.of();
		}

		String lastWord = args[args.length - 1];

		ArrayList<String> matchedLevels = new ArrayList<String>();
		for (LogLevel level : LogLevel.values()) {
			String name = level.name();
			if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
				matchedLevels.add(name);
			}
		}

		Collections.sort(matchedLevels, String.CASE_INSENSITIVE_ORDER);
		return matchedLevels;
	}
}