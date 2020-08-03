package xyz.zeeraa.novacore.command.commands.novacore.loottable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.NovaCore;
import xyz.zeeraa.novacore.command.AllowedSenders;
import xyz.zeeraa.novacore.command.NovaSubCommand;
import xyz.zeeraa.novacore.loottable.LootTable;

public class NovaCoreSubCommandLootTableTest extends NovaSubCommand {
	public NovaCoreSubCommandLootTableTest() {
		super("test");

		this.setDescription("Test the loot of a loot table");

		this.setPermission("novacore.command.novacore.loottable.test");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.setAllowedSenders(AllowedSenders.PLAYERS);
		
		this.addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a loot table name");
			return false;
		}

		LootTable lootTable = NovaCore.getInstance().getLootTableManager().getLootTable(args[0]);

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
			for (String key : NovaCore.getInstance().getLootTableManager().getLootTables().keySet()) {
				lootTables.add(key);
			}
		}

		return lootTables;
	}
}