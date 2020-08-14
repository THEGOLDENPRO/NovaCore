package xyz.zeeraa.novacore.command.commands.novacore.loottable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.NovaCore;
import xyz.zeeraa.novacore.command.NovaSubCommand;
import xyz.zeeraa.novacore.loottable.LootTable;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandLootTableList extends NovaSubCommand {
	public NovaCoreSubCommandLootTableList() {
		super("list");

		this.setDescription("List loot tables");

		this.setPermission("novacore.command.novacore.loottable.list");
		this.setPermissionDefaultValue(PermissionDefault.OP);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String message = ChatColor.AQUA + "" + NovaCore.getInstance().getLootTableManager().getLootTables().size() + ChatColor.GOLD + " Loot tables loaded\n";

		String lootTableList = "";
		for (String key : NovaCore.getInstance().getLootTableManager().getLootTables().keySet()) {
			LootTable lootTable = NovaCore.getInstance().getLootTableManager().getLootTable(key);

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