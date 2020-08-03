package xyz.zeeraa.novacore.command.commands.novacore.logger;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.command.AllowedSenders;
import xyz.zeeraa.novacore.command.NovaSubCommand;
import xyz.zeeraa.novacore.log.Log;

public class NovaCoreSubCommandLoggerUnsubscribe extends NovaSubCommand {
	public NovaCoreSubCommandLoggerUnsubscribe() {
		super("unsubscribe");
		this.setPermission("novacore.command.novacore.logger.unsubscribe");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.setAllowedSenders(AllowedSenders.PLAYERS);

		this.setDescription("Disable all log messages");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;

		if (Log.subscribedPlayers.containsKey(player.getUniqueId())) {
			Log.subscribedPlayers.remove(player.getUniqueId());
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