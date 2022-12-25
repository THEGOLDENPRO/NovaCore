package net.zeeraa.novacore.spigot.utils;

import org.bukkit.inventory.ItemStack;

import dev.lone.itemsadder.api.CustomStack;

public class NovaItemsAdderUtils {
	public static ItemStack getItemStack(String namespace) {
		CustomStack stack = CustomStack.getInstance(namespace);
		return stack.getItemStack();
	}
}