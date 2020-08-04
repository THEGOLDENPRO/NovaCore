package xyz.zeeraa.novacore.abstraction;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VersionIndependantUtils {
	public void setBlockAsPlayerSkull(Block block);
	
	/**
	 * Get the item from the player main hand
	 * @param player The player to get item from
	 * @return {@link ItemStack} from the players main hand
	 */
	public ItemStack getItemInMainHand(Player player);
	
	/**
	 * Get the item from the player off hand
	 * @param player The player to get item from
	 * @return {@link ItemStack} or <code>null</code> if the server version does not have a off hand
	 */
	public ItemStack getItemInOffHand(Player player);
	
	/**
	 * Get the max heath of a {@link LivingEntity}
	 * @param entity The entity to get the max health from
	 */
	public double getEntityMaxHealth(LivingEntity livingEntity);
	
	/**
	 * Set the max heath of a {@link LivingEntity}
	 * @param entity The entity to reset the set health of
	 */
	public void setEntityMaxHealth(LivingEntity livingEntity);
	
	/**
	 * Reset the max heath of a {@link LivingEntity}
	 * @param entity The entity to reset the max health of
	 */
	public void resetEntityMaxHealth(LivingEntity livingEntity);
	
	/**
	 * Get the recent server TPS
	 * @return Array with recent TPS
	 */
	public double[] getRecentTps();
}