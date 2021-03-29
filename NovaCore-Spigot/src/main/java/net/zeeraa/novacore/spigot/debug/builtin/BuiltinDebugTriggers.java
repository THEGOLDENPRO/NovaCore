package net.zeeraa.novacore.spigot.debug.builtin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionDefault;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.debug.DebugCommandRegistrator;
import net.zeeraa.novacore.spigot.debug.DebugTrigger;
import net.zeeraa.novacore.spigot.utils.ItemBuilder;
import net.zeeraa.novacore.spigot.utils.LocationUtils;

public class BuiltinDebugTriggers {
	public BuiltinDebugTriggers() {
		DebugCommandRegistrator.getInstance().addDebugTrigger(new DebugTrigger() {

			@Override
			public void onExecute(CommandSender sender, String commandLabel, String[] args) {
				Player player = (Player) sender;

				int blockX = player.getLocation().getBlockX();
				int blockZ = player.getLocation().getBlockZ();

				double x = player.getLocation().getX();
				double z = player.getLocation().getZ();

				player.sendMessage(ChatColor.DARK_PURPLE + "Location#getX(): " + x);
				player.sendMessage(ChatColor.DARK_PURPLE + "Location#getZ(): " + z);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Location#getBlockX(): " + blockX);
				player.sendMessage(ChatColor.LIGHT_PURPLE + "Location#getBlockZ(): " + blockZ);
				player.sendMessage(ChatColor.GREEN + "LocationUtils#blockCenter(blockX): " + LocationUtils.blockCenter(blockX));
				player.sendMessage(ChatColor.GREEN + "LocationUtils#blockCenter(blockZ): " + LocationUtils.blockCenter(blockZ));
			}

			@Override
			public PermissionDefault getPermissionDefault() {
				return PermissionDefault.OP;
			}

			@Override
			public String getPermission() {
				return "novacore.debug.testblockcenter";
			}

			@Override
			public String getName() {
				return "testblockcenter";
			}

			@Override
			public AllowedSenders getAllowedSenders() {
				return AllowedSenders.PLAYERS;
			}
		});

		DebugCommandRegistrator.getInstance().addDebugTrigger(new DebugTrigger() {

			@Override
			public void onExecute(CommandSender sender, String commandLabel, String[] args) {
				Player player = (Player) sender;

				for (Material material : Material.values()) {
					if (material.name().toLowerCase().contains("disk") || material.name().toLowerCase().contains("record")) {
						ItemStack stack = new ItemStack(material);
						ItemMeta meta = stack.getItemMeta();

						meta.setLore(ItemBuilder.generateLoreList(ChatColor.WHITE + material.name()));

						stack.setItemMeta(meta);

						player.getInventory().addItem(stack);
					}
				}
			}

			@Override
			public PermissionDefault getPermissionDefault() {
				return PermissionDefault.OP;
			}

			@Override
			public String getPermission() {
				return "novacore.debug.dumprecordnames";
			}

			@Override
			public String getName() {
				return "dumprecordnames";
			}

			@Override
			public AllowedSenders getAllowedSenders() {
				return AllowedSenders.PLAYERS;
			}
		});

		DebugCommandRegistrator.getInstance().addDebugTrigger(new DebugTrigger() {

			@Override
			public void onExecute(CommandSender sender, String commandLabel, String[] args) {
				Player player = (Player) sender;

				if (args.length > 0) {
					if (ItemBuilder.hasRecordName(args[0])) {
						player.getInventory().addItem(ItemBuilder.getRecordItemBuilder(args[0]).build());
						player.sendMessage(ChatColor.GREEN + "ok");
						return;
					}
				}

				String validNames = "";

				for (String name : ItemBuilder.getAvailableRecordNames()) {
					validNames += name + " ";
				}

				player.sendMessage(ChatColor.RED + "Please provide a valid record name. Valid names are: " + ChatColor.AQUA + validNames);
			}

			@Override
			public PermissionDefault getPermissionDefault() {
				return PermissionDefault.OP;
			}

			@Override
			public String getPermission() {
				return "novacore.debug.testitembuilderrecord";
			}

			@Override
			public String getName() {
				return "testitembuilderrecord";
			}

			@Override
			public AllowedSenders getAllowedSenders() {
				return AllowedSenders.PLAYERS;
			}
		});
	}
}