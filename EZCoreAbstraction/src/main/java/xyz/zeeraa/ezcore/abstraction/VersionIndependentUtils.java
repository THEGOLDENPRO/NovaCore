package xyz.zeeraa.ezcore.abstraction;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VersionIndependentUtils {
	public void setBlockAsPlayerSkull(Block block);
	
	public ItemStack getItemInMainHand(Player player);
}