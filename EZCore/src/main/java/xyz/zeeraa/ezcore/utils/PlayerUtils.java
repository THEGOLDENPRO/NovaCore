package xyz.zeeraa.ezcore.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {
	public static void clearPlayerInventory(Player player) {
		player.getInventory().clear();
		for (int i = 0; i < 36; i++) {
			player.getInventory().setItem(i, new ItemStack(Material.AIR));
		}
		player.getInventory().setArmorContents(new ItemStack[player.getInventory().getArmorContents().length]);
	}
	
	public static void clearPotionEffects(Player player) {
		for(PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	public static void resetPlayerXP(Player player) {
		player.setExp(0);
		player.setLevel(0);
	}
}