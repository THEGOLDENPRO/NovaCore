package xyz.zeeraa.ezcore.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import xyz.zeeraa.ezcore.command.EZCommand;

public class EZCoreCommand extends EZCommand {

	public EZCoreCommand() {
		super("ezcore");

		this.setAliases(EZCommand.generateAliasList("ez", "ezc"));

		this.setDescription("comamnd for EZCore");

		this.setPermission("ezcore.ezcore");
		this.addHelpSubCommand();
		this.addSubCommand(new EZCoreSubCommandModules());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/ezcore help" + ChatColor.GOLD + " to see all commands");
		return false;
	}

}
