package net.zeeraa.novacore.spigot.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Functions to do things with players
 * @author Zeeraa
 *
 */
public class PlayerUtils {
	/**
	 * Clear the players inventory including armor slots
	 * @param player The {@link Player} to clear
	 */
	public static void clearPlayerInventory(Player player) {
		player.getInventory().clear();
		for (int i = 0; i < 36; i++) {
			player.getInventory().setItem(i, new ItemStack(Material.AIR));
		}
		player.getInventory().setArmorContents(new ItemStack[player.getInventory().getArmorContents().length]);
	}
	
	/**
	 * Remove all the potion effects from a player
	 * @param player The {@link Player} to remove potion effects from
	 */
	public static void clearPotionEffects(Player player) {
		for(PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Set the player level and xp to 0
	 * @param player The {@link Player} to reset xp for
	 */
	public static void resetPlayerXP(Player player) {
		player.setExp(0);
		player.setLevel(0);
	}
}