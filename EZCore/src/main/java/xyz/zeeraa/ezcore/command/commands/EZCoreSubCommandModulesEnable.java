package xyz.zeeraa.ezcore.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.md_5.bungee.api.ChatColor;
import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.module.EZModule;
import xyz.zeeraa.ezcore.module.ModuleEnableFailureReason;
import xyz.zeeraa.ezcore.module.ModuleManager;

public class EZCoreSubCommandModulesEnable extends EZSubCommand {
	public EZCoreSubCommandModulesEnable() {
		super("enable");
		setPermission("ezcore.ezcore.modules.enable");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		setDescription("Enable a module");
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

		if (module.isEnabled()) {
			sender.sendMessage(ChatColor.RED + "That module is already enabled");
			return false;
		}

		if (module.enable()) {
			sender.sendMessage(ChatColor.GREEN + "Module " + module.getName() + " was enabled successfully");
			return true;
		} else {
			ModuleEnableFailureReason reason = module.getEnableFailureReason();

			sender.sendMessage(ChatColor.RED + "An error occured while trying to enable module " + module.getName() + ".\nMore info might be avaliable in the console." + ChatColor.RED + reason.name());
			return false;
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		ArrayList<String> modules = new ArrayList<String>();

		if (args.length == 1) {
			for (String key : ModuleManager.getModules().keySet()) {
				if (ModuleManager.isDisabled(key)) {
					modules.add(ModuleManager.getModule(key).getName());
				}
			}
		}

		return modules;
	}
}