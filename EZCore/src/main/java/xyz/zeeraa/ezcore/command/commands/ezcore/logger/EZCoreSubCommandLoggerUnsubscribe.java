package xyz.zeeraa.ezcore.command.commands.ezcore.logger;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.command.AllowedSenders;
import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.log.EZLogger;

public class EZCoreSubCommandLoggerUnsubscribe extends EZSubCommand {
	public EZCoreSubCommandLoggerUnsubscribe() {
		super("unsubscribe");
		this.setPermission("ezcore.ezcore.logger.unsubscribe");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setAllowedSenders(AllowedSenders.PLAYERS);

		this.setDescription("Disable all log messages");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;

		if (EZLogger.subscribedPlayers.containsKey(player.getUniqueId())) {
			EZLogger.subscribedPlayers.remove(player.getUniqueId());
			player.sendMessage(ChatColor.GREEN + "You will no longer receive logger messages");
		} else {
			player.sendMessage(ChatColor.GREEN + "You already have logger messages disabled");
		}

		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}