package xyz.zeeraa.novacore.command.commands.novacore.module;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.command.NovaSubCommand;
import xyz.zeeraa.novacore.module.ModuleManager;

public class NovaCoreSubCommandModules extends NovaSubCommand {
	public NovaCoreSubCommandModules() {
		super("modules");

		this.setDescription("Manage modules");

		this.setAliases(generateAliasList("module"));

		this.setPermission("novacore.command.novacore.modules");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.addHelpSubCommand();
		this.addSubCommand(new NovaCoreSubCommandModulesList());
		this.addSubCommand(new NovaCoreSubCommandModulesDisable());
		this.addSubCommand(new NovaCoreSubCommandModulesEnable());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "" + ModuleManager.getModules().size() + ChatColor.GOLD + " Modules loaded. use " + ChatColor.AQUA + "/novacore modules help" + ChatColor.GOLD + " for help");
		return true;
	}
}