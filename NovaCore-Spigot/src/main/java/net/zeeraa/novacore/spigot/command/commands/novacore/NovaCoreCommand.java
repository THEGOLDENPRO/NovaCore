package net.zeeraa.novacore.spigot.command.commands.novacore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.command.commands.novacore.gotoworld.NovaCoreSubCommandGoToWorld;
import net.zeeraa.novacore.spigot.command.commands.novacore.logger.NovaCoreSubCommandLogger;
import net.zeeraa.novacore.spigot.command.commands.novacore.loottable.NovaCoreSubCommandLootTable;
import net.zeeraa.novacore.spigot.command.commands.novacore.module.NovaCoreSubCommandModules;
import net.zeeraa.novacore.spigot.command.commands.novacore.whereami.NovaCoreSubCommandWhereAmI;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
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
		
		this.setFilterAutocomplete(true);
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