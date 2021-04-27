package net.zeeraa.novacore.spigot.command.commands.dumplanguagenodes;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.language.Language;
import net.zeeraa.novacore.spigot.language.LanguageManager;

public class DumpLanguageNodesCommand extends NovaCommand {
	public DumpLanguageNodesCommand() {
		super("dumplanguagenodes", NovaCore.getInstance());

		setAllowedSenders(AllowedSenders.CONSOLE);
		setPermission("novacore.command.dumplanguagenodes");
		setPermissionDefaultValue(PermissionDefault.OP);
		setDescription("Print all language nodes to the console");
		setEmptyTabMode(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		String colorChar = "§";

		System.out.println("Color char: " + colorChar);

		for (String key : LanguageManager.getLanguages().keySet()) {
			Language language = LanguageManager.getLanguages().get(key);
			System.out.println("----- Language: " + language.getDisplayName() + " (" + language.getLanguageCode() + ") -----");

			for (String node : language.getContent().keySet()) {
				System.out.println(node + " : " + language.getContent().get(node));
			}
		}

		return true;
	}
}