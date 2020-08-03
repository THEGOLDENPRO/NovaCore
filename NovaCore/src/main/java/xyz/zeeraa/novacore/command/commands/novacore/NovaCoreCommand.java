package xyz.zeeraa.novacore.command.commands.novacore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.command.NovaCommand;
import xyz.zeeraa.novacore.command.commands.novacore.gotoworld.NovaCoreSubCommandGoToWorld;
import xyz.zeeraa.novacore.command.commands.novacore.logger.NovaCoreSubCommandLogger;
import xyz.zeeraa.novacore.command.commands.novacore.loottable.NovaCoreSubCommandLootTable;
import xyz.zeeraa.novacore.command.commands.novacore.module.NovaCoreSubCommandModules;
import xyz.zeeraa.novacore.command.commands.novacore.whereami.NovaCoreSubCommandWhereAmI;

public class NovaCoreCommand extends NovaCommand {

	public NovaCoreCommand() {
		super("novacore");

		this.setAliases(NovaCommand.generateAliasList("nc", "nova"));

		this.setDescription("command for NovaCore");

		this.setPermission("novacore.command.novacore");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.addHelpSubCommand();
		this.addSubCommand(new NovaCoreSubCommandModules());
		this.addSubCommand(new NovaCoreSubCommandLootTable());
		this.addSubCommand(new NovaCoreSubCommandLogger());
		this.addSubCommand(new NovaCoreSubCommandWhereAmI());
		this.addSubCommand(new NovaCoreSubCommandGoToWorld());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/novacore help" + ChatColor.GOLD + " to see all commands");
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}