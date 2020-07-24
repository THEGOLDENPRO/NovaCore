package xyz.zeeraa.ezcore.command.commands.ezcore.loottable.module;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.module.ModuleManager;

public class EZCoreSubCommandModules extends EZSubCommand {
	public EZCoreSubCommandModules() {
		super("modules");

		this.setDescription("Manage modules");

		this.setAliases(generateAliasList("module"));

		this.setPermission("ezcore.ezcore.modules");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.addHelpSubCommand();
		this.addSubCommand(new EZCoreSubCommandModulesList());
		this.addSubCommand(new EZCoreSubCommandModulesDisable());
		this.addSubCommand(new EZCoreSubCommandModulesEnable());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "" + ModuleManager.getModules().size() + ChatColor.GOLD + " Modules loaded. use " + ChatColor.AQUA + "/ezcore modules help" + ChatColor.GOLD + " for help");
		return true;
	}
}