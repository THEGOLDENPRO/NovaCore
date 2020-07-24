package xyz.zeeraa.ezcore.command.commands.ezcore.loottable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.command.EZSubCommand;

public class EZCoreSubCommandLootTable extends EZSubCommand {
	public EZCoreSubCommandLootTable() {
		super("loottable");

		this.setAliases(generateAliasList("loottables"));

		this.setDescription("Manage loot tables");

		this.setPermission("ezcore.ezcore.loottable");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.addHelpSubCommand();

		this.addSubCommand(new EZCoreSubCommandLootTableList());
		this.addSubCommand(new EZCoreSubCommandLootTableTest());
		this.addSubCommand(new EZCoreSubCommandLootTableLoaderList());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "" + EZCore.getInstance().getLootTableManager().getLootTables().size() + ChatColor.GOLD + " Loot tables loaded and " + ChatColor.AQUA + "" + EZCore.getInstance().getLootTableManager().getLoaders().size() + ChatColor.GOLD + " loaders added. use " + ChatColor.AQUA + "/ezcore loottable help" + ChatColor.GOLD + " for help");
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		ArrayList<String> lootTables = new ArrayList<String>();

		if (args.length == 1) {
			for (String key : EZCore.getInstance().getLootTableManager().getLootTables().keySet()) {
				lootTables.add(key);
			}
		}

		return lootTables;
	}
}