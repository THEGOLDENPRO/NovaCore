package net.zeeraa.novacore.spigot.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.NovaCore;

public class ItemUtils {
	public static void removeOneFromHand(Player player) {
		final ItemStack item = NovaCore.getInstance().getVersionIndependentUtils().getItemInMainHand(player);
		final int a = item.getAmount();
		if (a <= 1) {
			NovaCore.getInstance().getVersionIndependentUtils().setItemInMainHand(player, new ItemStack(Material.AIR));
		} else {
			item.setAmount(a - 1);
			NovaCore.getInstance().getVersionIndependentUtils().setItemInMainHand(player, item);
		}
	}
}