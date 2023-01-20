package net.zeeraa.novacore.spigot.command.commands.novacore.module;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.md_5.bungee.api.ChatColor;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.ModuleNameComparator;
import net.zeeraa.novacore.spigot.module.NovaModule;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandModulesReload extends NovaSubCommand {
	public NovaCoreSubCommandModulesReload() {
		super("reload");
		setPermission("novacore.command.novacore.modules.reload");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		setDescription("Reloads a module");

		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a module name");
			return false;
		}

		NovaModule module = null;

		for (String key : ModuleManager.getModules().keySet()) {
			NovaModule m = ModuleManager.getModule(key);
			if (m.getName().equalsIgnoreCase(args[0])) {
				module = m;
				break;
			}
		}

		if (module == null) {
			sender.sendMessage(ChatColor.RED + "Could not find a module with that name");
			return false;
		}

		if (ModuleManager.isEssential(module)) {
			sender.sendMessage(ChatColor.RED + "This module cant be disabled using this command");
			return false;
		}

		if (!module.isEnabled()) {
			sender.sendMessage(ChatColor.RED + "That module is disabled");
			return false;
		}

		if (module.reload()) {
			sender.sendMessage(ChatColor.GREEN + "Module " + module.getName() + " reloaded");
			return true;
		} else {
			sender.sendMessage(ChatColor.RED + "Failed to reload module. check console for details");
			return false;
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		List<String> modules = new ArrayList<String>();

		if (args.length == 1) {
			ModuleManager.getModules().keySet().forEach(key -> {
				if (ModuleManager.isEnabled(key)) {
					if (!ModuleManager.isEssential(ModuleManager.getModules().get(key))) {
						modules.add(ModuleManager.getModule(key).getName());
					}
				}
			});
		}

		modules.sort(new ModuleNameComparator());

		return modules;
	}
}