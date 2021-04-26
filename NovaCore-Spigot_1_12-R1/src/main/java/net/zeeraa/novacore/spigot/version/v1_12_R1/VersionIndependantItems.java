package net.zeeraa.novacore.spigot.version.v1_12_R1;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VersionIndependantItems extends net.zeeraa.novacore.spigot.abstraction.VersionIndependantItems{
	@Override
	public ItemStack getPlayerSkull() {
		return new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
	}
}