package xyz.zeeraa.ezcore.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.module.EZModule;
import xyz.zeeraa.ezcore.module.ModuleManager;

public class EZCoreSubCommandModuleList extends EZSubCommand {

	public EZCoreSubCommandModuleList() {
		super("list");

		this.setDescription("list modules");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String message = ChatColor.AQUA + "" + ModuleManager.getModules().size() + ChatColor.GOLD + " modules loaded\n";

		int enabled = 0;
		int disabled = 0;

		String moduleList = "";
		for (String key : ModuleManager.getModules().keySet()) {
			EZModule module = ModuleManager.getModule(key);

			if (module.isEnabled()) {
				enabled++;
			} else {
				disabled++;
			}

			moduleList += ChatColor.AQUA + module.getName() + ChatColor.GOLD + " : " + (module.isEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled") + ChatColor.RESET + "\n";
		}

		message += ChatColor.AQUA + "" + enabled + ChatColor.GOLD + " Enabled, " + ChatColor.AQUA + disabled + ChatColor.GOLD + " Disabled\n";

		message += ChatColor.GOLD + "Module list:\n\n" + moduleList;

		sender.sendMessage(message);

		return false;
	}
}