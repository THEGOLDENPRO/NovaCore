package net.zeeraa.novacore.spigot.mapdisplay.command;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.BlockIterator;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplay;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplayManager;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDCreateSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDDeleteSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDListSubCommand;
import net.zeeraa.novacore.spigot.mapdisplay.command.subcommand.MDSetImageSubCommand;

public class MapDisplayCommand extends NovaCommand {

	public MapDisplayCommand() {
		super("mapdisplay", NovaCore.getInstance());

		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("novacore.command.mapdisplay");
		setPermissionDefaultValue(PermissionDefault.OP);
		setEmptyTabMode(true);
		setFilterAutocomplete(true);

		addSubCommand(new MDListSubCommand());
		addSubCommand(new MDCreateSubCommand());
		addSubCommand(new MDDeleteSubCommand());
		addSubCommand(new MDSetImageSubCommand());

		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;

		Location location = null;
		BlockIterator iter = new BlockIterator(player, 10);

		Block lastBlock = iter.next();
		while (iter.hasNext()) {
			Block next = iter.next();

			if (next.getType() == Material.AIR) {
				lastBlock = next;
				continue;
			}

			location = lastBlock.getLocation();

			break;
		}

		if (location != null) {
			ItemFrame frame = MapDisplayManager.getInstance().getItemFrameAtLocation(location);

			if (frame != null) {
				MapDisplay display = MapDisplayManager.getInstance().createMapDisplay(frame, true, args[0]);

				try {
					URL url = new URL(args[1]);
					BufferedImage image = ImageIO.read(url);

					try {
						display.setImage(image);
					} catch (Exception e) {
						player.sendMessage(ChatColor.RED + e.getClass().getName() + " " + e.getMessage());
						e.printStackTrace();
					}
				} catch (IOException e) {
					player.sendMessage(ChatColor.RED + "Networking error");
					player.sendMessage(ChatColor.RED + e.getClass().getName() + " " + e.getMessage());
					e.printStackTrace();
				}

				return true;
			}
		}
		player.sendMessage(ChatColor.RED + "Please look at an item frame before running this");

		return false;
	}

}