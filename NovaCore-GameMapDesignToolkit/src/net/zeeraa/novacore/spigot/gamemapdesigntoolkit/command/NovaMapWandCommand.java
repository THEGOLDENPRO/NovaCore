package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.items.NovaAreaSelectionWand;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItemManager;

public class NovaMapWandCommand extends NovaCommand {
	public NovaMapWandCommand(Plugin owner) {
		super("novamapwand", owner);
		
		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("gamemapdesigntoolkit.novamapwand");
		setPermissionDefaultValue(PermissionDefault.OP);
		setPermissionDescription("Give yourself a map editor wand");
		setUsage("/novamapwand");
		setDescription("Give yourself a map editor wand");
		setEmptyTabMode(true);
		setFilterAutocomplete(true);
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;

		player.getInventory().addItem(CustomItemManager.getInstance().getCustomItemStack(NovaAreaSelectionWand.class, player));

		return true;
	}
}