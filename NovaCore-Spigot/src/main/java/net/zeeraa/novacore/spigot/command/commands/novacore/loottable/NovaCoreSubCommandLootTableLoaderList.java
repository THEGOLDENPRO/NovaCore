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
public class NovaCoreSubCommandLootTableLoaderList extends NovaSubCommand {
	public NovaCoreSubCommandLootTableLoaderList() {
		super("loaders");

		this.setAliases(generateAliasList("loader"));

		this.setDescription("List loot table loaders");

		this.setPermission("novacore.command.novacore.loottable.loaders");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String message = ChatColor.AQUA + "" + NovaCore.getInstance().getLootTableManager().getLoaders().size() + ChatColor.GOLD + " Loot tables loaders added\n";

		String loadersList = "";
		for (String key : NovaCore.getInstance().getLootTableManager().getLoaders().keySet()) {
			loadersList += ChatColor.AQUA + key + ChatColor.RESET + "\n";
		}

		message += ChatColor.GOLD + "Loot table loader list:\n" + loadersList;

		sender.sendMessage(message);

		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}