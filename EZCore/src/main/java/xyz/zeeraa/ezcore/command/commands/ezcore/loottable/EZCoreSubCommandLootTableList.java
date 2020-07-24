package xyz.zeeraa.ezcore.command.commands.ezcore.loottable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.loottable.LootTable;

public class EZCoreSubCommandLootTableList extends EZSubCommand {
	public EZCoreSubCommandLootTableList() {
		super("list");

		this.setDescription("List loot tables");

		this.setPermission("ezcore.ezcore.loottable.list");
		this.setPermissionDefaultValue(PermissionDefault.OP);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String message = ChatColor.AQUA + "" + EZCore.getInstance().getLootTableManager().getLootTables().size() + ChatColor.GOLD + " Loot tables loaded\n";

		String lootTableList = "";
		for (String key : EZCore.getInstance().getLootTableManager().getLootTables().keySet()) {
			LootTable lootTable = EZCore.getInstance().getLootTableManager().getLootTable(key);

			lootTableList += ChatColor.AQUA + lootTable.getName() + ChatColor.GOLD + " : " + ChatColor.AQUA + lootTable.getDisplayName() + ChatColor.RESET + "\n";
		}

		message += ChatColor.GOLD + "Loot table list:\n-- name -- | -- display name --\n\n" + lootTableList;

		sender.sendMessage(message);

		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}