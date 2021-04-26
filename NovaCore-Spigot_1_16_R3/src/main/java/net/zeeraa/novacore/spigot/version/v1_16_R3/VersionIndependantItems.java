package net.zeeraa.novacore.spigot.version.v1_16_R3;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VersionIndependantItems extends net.zeeraa.novacore.spigot.abstraction.VersionIndependantItems{
	@Override
	public ItemStack getPlayerSkull() {
		return new ItemStack(Material.PLAYER_HEAD, 1);
	}
}