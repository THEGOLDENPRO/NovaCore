package net.zeeraa.novautils.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novautils.NovaUtils;

public class SudoCommand extends NovaCommand {

	public SudoCommand() {
		super("sudo", NovaUtils.getInstance());

		setAllowedSenders(AllowedSenders.PLAYERS);

		setPermission("novautils.command.sudo");
		setPermissionDefaultValue(PermissionDefault.OP);
		setPermissionDescription("Allow players to force another player to run a command");

		setDescription("Make another player run a command");
		setUseage("/sudo <Player> <Command>");

		addHelpSubCommand();

		setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length > 0) {
			Player player = Bukkit.getServer().getPlayer(args[0]);

			if (player != null) {
				if (player.isOnline()) {
					if (player.hasPermission("novautils.command.sudo.exempt")) {
						if (args.length > 1) {
							String commandArg[] = Arrays.copyOfRange(args, 1, args.length);

							String cmd = String.join(" ", commandArg);

							sender.sendMessage(ChatColor.GOLD + "Running " + ChatColor.AQUA + cmd + ChatColor.GOLD + " as " + ChatColor.AQUA + player.getDisplayName());
							player.chat(cmd);
						} else {
							sender.sendMessage(ChatColor.RED + "Please provide a command or message");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You cant use sudo on that player");
					}
					return true;
				}
			}

			sender.sendMessage(ChatColor.RED + "Could not find a player named " + args[0]);
		} else {
			sender.sendMessage(ChatColor.RED + "Please provide a player");
		}

		return true;
	}
}