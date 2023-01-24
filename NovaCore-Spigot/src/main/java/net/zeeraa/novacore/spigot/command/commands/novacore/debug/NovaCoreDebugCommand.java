package net.zeeraa.novacore.spigot.command.commands.novacore.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.commons.async.AsyncManager;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import net.zeeraa.novacore.spigot.command.commands.novacore.debug.providers.ModuleListProvider;
import net.zeeraa.novacore.spigot.command.commands.novacore.debug.providers.NovaCoreDebugCommandInfoProvider;
import net.zeeraa.novacore.spigot.command.commands.novacore.debug.providers.NovaCoreMainInfoProvider;

public class NovaCoreDebugCommand extends NovaSubCommand {
	private static final List<NovaCoreDebugCommandInfoProvider> infoProviders = new ArrayList<>();

	static {
		infoProviders.add(new NovaCoreMainInfoProvider());
		infoProviders.add(new ModuleListProvider());
	}
	
	public static final void addInfoProvider(NovaCoreDebugCommandInfoProvider provider) {
		infoProviders.add(provider);
	}

	public NovaCoreDebugCommand() {
		super("debug");

		setAllowedSenders(AllowedSenders.ALL);
		setPermission("novacore.command.novacore.debug");
		setPermissionDefaultValue(PermissionDefault.OP);

		setFilterAutocomplete(true);
		setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String message = "";

		message += ChatColor.AQUA + "-=-=-= BEGIN DEBUG =-=-=-\n";
		message += ChatColor.GOLD + "Server version: " + ChatColor.AQUA + Bukkit.getServer().getVersion() + "\n";

		for (NovaCoreDebugCommandInfoProvider provider : infoProviders) {
			message += provider.getData();
		}

		message += ChatColor.AQUA + "-=-=-= END DEBUG =-=-=-\n";

		for (String line : message.split("\n")) {
			sender.sendMessage(line);
		}

		sender.sendMessage(ChatColor.AQUA + "Uploading report to hastebin....");
		final String finalHastebinMessage = ChatColor.stripColor(message);
		AsyncManager.runAsync(() -> {
			try {
				String url = NovaCore.getInstance().getHastebinInstance().post(finalHastebinMessage, true);
				sender.sendMessage(ChatColor.GOLD + "View the report online here: " + ChatColor.AQUA + url);
			} catch (IOException e) {
				Log.error("Debug", "Failed to upload message to hastebin. " + e.getClass().getName() + " " + e.getMessage());
				e.printStackTrace();
				sender.sendMessage(ChatColor.DARK_RED + "Failed to upload data to hastebin. " + e.getClass().getName() + " " + e.getMessage());
			}
		});

		return true;
	}
}