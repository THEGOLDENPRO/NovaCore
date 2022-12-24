package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.json.JSONObject;

import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.GameMapDesignToolkit;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.PlayerAreaSelection;
import net.zeeraa.novacore.spigot.utils.LocationUtils;
import net.zeeraa.novacore.spigot.utils.VectorArea;

public class CopyVectorAreaCommand extends NovaCommand {
	public CopyVectorAreaCommand(Plugin owner) {
		super("copyvectorarea", owner);

		setAliases(generateAliasList("cva"));
		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("gamemapdesigntoolkit.copyvectorarea");
		setPermissionDefaultValue(PermissionDefault.OP);
		setPermissionDescription("Allow the player to use the copy vector area command");
		setUsage("/copylocation [-v | --verbose] [-c | --center]");
		setDescription("Copy the player selected area as a vector area json to the server clipboard");
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		int indent = 4;
		boolean verbose = false;
		boolean center = false;

		for (String arg : args) {
			if (arg.equalsIgnoreCase("-v") || arg.equalsIgnoreCase("--verbose")) {
				verbose = true;
			} else if (arg.equalsIgnoreCase("-c") || arg.equalsIgnoreCase("--center")) {
				center = true;
			}
		}

		Player player = (Player) sender;
		PlayerAreaSelection selection = GameMapDesignToolkit.getInstance().getPlayerSelection(player);

		Location pos1 = selection.getPosition1().clone();
		Location pos2 = selection.getPosition2().clone();

		if (center) {
			pos1.setX(LocationUtils.blockCenter(pos1.getBlockX()));
			pos1.setZ(LocationUtils.blockCenter(pos1.getBlockZ()));

			pos2.setX(LocationUtils.blockCenter(pos2.getBlockX()));
			pos2.setZ(LocationUtils.blockCenter(pos2.getBlockZ()));
		}

		VectorArea area = new VectorArea(pos1.toVector(), pos2.toVector());

		JSONObject json = area.toJSON();

		String string;

		if (indent == -1) {
			string = json.toString();
		} else {
			string = json.toString(indent);
		}

		StringSelection stringSelection = new StringSelection(string);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		player.sendMessage(ChatColor.GREEN + "ok");

		if (verbose) {
			player.sendMessage(ChatColor.AQUA + string);
		}

		return true;
	}
}