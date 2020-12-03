package net.zeeraa.novacore.spigot.command.commands.game.debug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.modules.game.GameManager;

public class GameDebugCommand extends NovaSubCommand {

	public GameDebugCommand() {
		super("debug");
		setAllowedSenders(AllowedSenders.ALL);
		setDescription("Show debug info");
		setPermission("novacore.command.game.debug");
		setPermissionDescription("Debug command for games");
		setPermissionDefaultValue(PermissionDefault.OP);

		setEmptyTabMode(true);
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "-=-=-= BEGIN DEBUG =-=-=-");

		sender.sendMessage(ChatColor.GOLD + "Server version: " + ChatColor.AQUA + Bukkit.getServer().getVersion());

		if (GameManager.getInstance().isEnabled()) {
			sender.sendMessage(ChatColor.GREEN + "GameManager is enabled");

			if (GameManager.getInstance().hasGame()) {
				sender.sendMessage(ChatColor.GOLD + "Loaded game name: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getName());
				sender.sendMessage(ChatColor.GOLD + "Display name: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getName());
				sender.sendMessage(ChatColor.GOLD + "Class: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getClass().getName());
				boolean started = GameManager.getInstance().getActiveGame().hasStarted();
				boolean ended = GameManager.getInstance().getActiveGame().hasEnded();
				sender.sendMessage(ChatColor.GOLD + "Started: " + (started ? ChatColor.GREEN : ChatColor.RED) + "" + started + ChatColor.GOLD + " Ended: " + (ended ? ChatColor.GREEN : ChatColor.RED) + "" + ended + ChatColor.GOLD);

				sender.sendMessage(ChatColor.GOLD + "Player count: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().getPlayers().size());

				sender.sendMessage(ChatColor.GOLD + "Auto end: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().autoEndGame());
				sender.sendMessage(ChatColor.GOLD + "Can start: " + ChatColor.AQUA + GameManager.getInstance().getActiveGame().canStart());

			} else {
				sender.sendMessage(ChatColor.RED + "No game loaded");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "GameManager is disabled");
		}

		sender.sendMessage(ChatColor.AQUA + "-=-=-= END DEBUG =-=-=-");

		return true;
	}
}