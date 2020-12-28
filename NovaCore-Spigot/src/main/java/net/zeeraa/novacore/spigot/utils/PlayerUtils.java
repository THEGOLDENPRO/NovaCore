package net.zeeraa.novacore.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import net.zeeraa.novacore.spigot.NovaCore;

/**
 * Functions to do things with players
 * 
 * @author Zeeraa
 *
 */
public class PlayerUtils {
	/**
	 * Clear the players inventory including armor slots
	 * 
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
	 * 
	 * @param player The {@link Player} to remove potion effects from
	 */
	public static void clearPotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Set the player level and xp to 0
	 * 
	 * @param player The {@link Player} to reset xp for
	 */
	public static void resetPlayerXP(Player player) {
		player.setExp(0);
		player.setLevel(0);
	}

	/**
	 * Reset the max heath of a {@link Player}
	 * 
	 * @param player The player to reset the max health of
	 */
	public static void resetMaxHealth(Player player) {
		NovaCore.getInstance().getVersionIndependentUtils().resetEntityMaxHealth(player);
	}

	/**
	 * Set the max heath of a {@link Player}
	 * 
	 * @param player The player to reset the set health of
	 * @param health The new health value
	 */
	public static void setMaxHealth(Player player, double health) {
		NovaCore.getInstance().getVersionIndependentUtils().setEntityMaxHealth(player, health);
	}

	/**
	 * Get the max heath of a {@link Player}
	 * 
	 * @param player The player to get the max health from
	 * 
	 * @return The max health of the player
	 */
	public static double getPlayerMaxHealth(Player player) {
		return NovaCore.getInstance().getVersionIndependentUtils().getEntityMaxHealth(player);
	}

	/**
	 * Fully heal a player and return the new health
	 * 
	 * @param player The player to heal
	 * @return The new player health
	 */
	public static double fullyHealPlayer(Player player) {
		double maxHealth = PlayerUtils.getPlayerMaxHealth(player);

		player.setHealth(maxHealth);

		return maxHealth;
	}

	/**
	 * Convert a list of players to a list of names
	 * 
	 * @param players The list of players
	 * @return The list on names
	 */
	public static List<String> getNames(List<Player> players) {
		List<String> names = new ArrayList<String>();

		for (Player player : players) {
			names.add(player.getName());
		}

		return names;
	}
}