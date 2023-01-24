package net.zeeraa.novacore.spigot.command.commands.novacore.debug.providers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.ModuleNameComparator;
import net.zeeraa.novacore.spigot.module.NovaModule;

public class ModuleListProvider implements NovaCoreDebugCommandInfoProvider {
	@Override
	public String getData() {
		String message = "";

		int enabledModuleCount = 0;
		int disabledModuleCount = 0;

		String moduleList = "-- NovaModules --\n";

		List<String> modulesKey = new ArrayList<>(ModuleManager.getModules().keySet());

		modulesKey.sort(new ModuleNameComparator());

		for (String key : modulesKey) {
			NovaModule module = ModuleManager.getModule(key);

			if (module.isEnabled()) {
				enabledModuleCount++;
			} else {
				disabledModuleCount++;
			}

			String style = "";

			if (ModuleManager.isEssential(module)) {
				style += ChatColor.BOLD;
			}

			moduleList += ChatColor.AQUA + module.getName() + ChatColor.GOLD + " : " + (module.isEnabled() ? ChatColor.GREEN + style + "Enabled" : ChatColor.RED + style + "Disabled") + ChatColor.RESET + "\n";
		}

		message += ChatColor.AQUA + "" + enabledModuleCount + ChatColor.GOLD + " Enabled, " + ChatColor.AQUA + disabledModuleCount + ChatColor.GOLD + " Disabled\n";

		message += ChatColor.GOLD + "Module list:\n\n" + moduleList;
		message += "\n\n" + ChatColor.RESET;
		return message;
	}
}