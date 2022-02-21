package net.zeeraa.novacore.spigot.abstraction;

import org.bukkit.inventory.ItemStack;

public abstract class VersionIndependantItems {
	public abstract ItemStack getPlayerSkull();

	public abstract boolean isPlayerSkull(ItemStack item);
}