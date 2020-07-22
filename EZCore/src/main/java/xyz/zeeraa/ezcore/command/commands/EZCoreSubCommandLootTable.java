package xyz.zeeraa.ezcore.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.command.AllowedSenders;
import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.loottable.LootTable;

public class EZCoreSubCommandLootTable extends EZSubCommand {
	public EZCoreSubCommandLootTable() {
		super("loottable");

		this.setAliases(generateAliasList("loottables"));

		this.setDescription("Manage loot tables");

		this.setPermission("ezcore.ezcore.loottable");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.addHelpSubCommand();

		setAllowedSenders(AllowedSenders.PLAYERS);

		this.addSubCommand(new EZCoreSubCommandLootTableList());
		this.addSubCommand(new EZCoreSubCommandLootTableTest());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a loot table name");
			return false;
		}

		LootTable lootTable = EZCore.getInstance().getLootTableManager().getLootTable(args[0]);

		if (lootTable == null) {
			sender.sendMessage(ChatColor.RED + "Could not find a loot table with that name");
			return false;
		}

		Player player = (Player) sender;

		ArrayList<ItemStack> items = lootTable.generateLoot();

		player.getInventory().clear();
		while (items.size() > 0) {

			player.getInventory().addItem(items.remove(0));
		}

		player.sendMessage(ChatColor.GOLD + "Added loot from the loot table " + ChatColor.AQUA + lootTable.getDisplayName() + ChatColor.GOLD + " to your inventory");
		
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