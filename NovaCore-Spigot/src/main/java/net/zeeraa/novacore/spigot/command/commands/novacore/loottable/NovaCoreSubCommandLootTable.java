package net.zeeraa.novacore.spigot.command.commands.novacore.loottable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandLootTable extends NovaSubCommand {
	public NovaCoreSubCommandLootTable() {
		super("loottable");

		this.setAliases(generateAliasList("loottables"));

		this.setDescription("Manage loot tables");

		this.setPermission("novacore.command.novacore.loottable");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.addHelpSubCommand();

		this.addSubCommand(new NovaCoreSubCommandLootTableList());
		this.addSubCommand(new NovaCoreSubCommandLootTableTest());
		this.addSubCommand(new NovaCoreSubCommandLootTableLoaderList());
		
		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "" + NovaCore.getInstance().getLootTableManager().getLootTables().size() + ChatColor.GOLD + " Loot tables loaded and " + ChatColor.AQUA + "" + NovaCore.getInstance().getLootTableManager().getLoaders().size() + ChatColor.GOLD + " loaders added. use " + ChatColor.AQUA + "/novacore loottable help" + ChatColor.GOLD + " for help");
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		List<String> lootTables = new ArrayList<String>();

		if (args.length == 1) {
			NovaCore.getInstance().getLootTableManager().getLootTables().keySet().forEach(key -> lootTables.add(key));
		}

		return lootTables;
	}
}