package xyz.zeeraa.ezcore.command.commands.ezcore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.command.EZCommand;
import xyz.zeeraa.ezcore.command.commands.ezcore.logger.EZCoreSubCommandLogger;
import xyz.zeeraa.ezcore.command.commands.ezcore.loottable.EZCoreSubCommandLootTable;
import xyz.zeeraa.ezcore.command.commands.ezcore.loottable.module.EZCoreSubCommandModules;
import xyz.zeeraa.ezcore.command.commands.ezcore.whereami.EZCoreSubCommandWhereAmI;

public class EZCoreCommand extends EZCommand {

	public EZCoreCommand() {
		super("ezcore");

		this.setAliases(EZCommand.generateAliasList("ez", "ezc"));

		this.setDescription("comamnd for EZCore");

		this.setPermission("ezcore.ezcore");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.addHelpSubCommand();
		this.addSubCommand(new EZCoreSubCommandModules());
		this.addSubCommand(new EZCoreSubCommandLootTable());
		this.addSubCommand(new EZCoreSubCommandLogger());
		this.addSubCommand(new EZCoreSubCommandWhereAmI());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/ezcore help" + ChatColor.GOLD + " to see all commands");
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}