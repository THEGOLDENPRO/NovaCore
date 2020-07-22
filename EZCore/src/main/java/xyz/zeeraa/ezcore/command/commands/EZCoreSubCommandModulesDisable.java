package xyz.zeeraa.ezcore.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.md_5.bungee.api.ChatColor;
import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.module.EZModule;
import xyz.zeeraa.ezcore.module.ModuleManager;

public class EZCoreSubCommandModulesDisable extends EZSubCommand {
	public EZCoreSubCommandModulesDisable() {
		super("disable");
		setPermission("ezcore.ezcore.modules.disable");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		setDescription("Disable a module");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a module name");
			return false;
		}

		EZModule module = null;

		for (String key : ModuleManager.getModules().keySet()) {
			EZModule m = ModuleManager.getModule(key);
			if (m.getName().equalsIgnoreCase(args[0])) {
				module = m;
				break;
			}
		}

		if (module == null) {
			sender.sendMessage(ChatColor.RED + "Could not find a module with that name");
			return false;
		}

		if (!module.isEnabled()) {
			sender.sendMessage(ChatColor.RED + "That module is already disabled");
			return false;
		}

		if (module.disable()) {
			sender.sendMessage(ChatColor.GREEN + "Module " + module.getName() + " was disabled successfully");
			return true;
		} else {
			sender.sendMessage(ChatColor.RED + "The module " + module.getName() + " was disabled but an error occured.\nMore info might be avaliable in the console");
			return false;
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		ArrayList<String> modules = new ArrayList<String>();

		if (args.length == 1) {
			for (String key : ModuleManager.getModules().keySet()) {
				if (ModuleManager.isEnabled(key)) {
					modules.add(ModuleManager.getModule(key).getName());
				}
			}
		}

		return modules;
	}
}