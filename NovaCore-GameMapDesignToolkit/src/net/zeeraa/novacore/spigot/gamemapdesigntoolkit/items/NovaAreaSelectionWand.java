package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.GameMapDesignToolkit;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.PlayerAreaSelection;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItem;
import net.zeeraa.novacore.spigot.utils.ItemBuilder;

public class NovaAreaSelectionWand extends CustomItem {
	@Override
	protected ItemStack createItemStack(Player player) {
		return new ItemBuilder(Material.STICK).setAmount(1).setName(ChatColor.GOLD + "Nova Area Selector Wand").addItemFlags(ItemFlag.HIDE_ENCHANTS).addEnchant(Enchantment.DURABILITY, 1, true).build();
	}

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		PlayerAreaSelection selection = GameMapDesignToolkit.getInstance().getPlayerSelection(player);
		if (event.getClickedBlock() != null) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				selection.setPosition1(event.getClickedBlock().getLocation().clone());
				player.sendMessage(ChatColor.AQUA + "Position 1 set to " + stringifyLocation(selection.getPosition1()));
			} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				selection.setPosition2(event.getClickedBlock().getLocation().clone());
				player.sendMessage(ChatColor.AQUA + "Position 2 set to " + stringifyLocation(selection.getPosition2()));
				event.setCancelled(true);
			}
		}
	}
	
	private String stringifyLocation(Location location) {
		return "X: " + location.getX() + " Y: " + location.getY() + " Z: " + location.getZ();
	}
}