package net.zeeraa.novacore.spigot.abstraction;

import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

public interface VersionIndependantUtils {
	public void setBlockAsPlayerSkull(Block block);

	/**
	 * Get the item from the player main hand
	 * 
	 * @param player The player to get item from
	 * @return {@link ItemStack} from the players main hand
	 */
	public ItemStack getItemInMainHand(Player player);

	/**
	 * Set the item in a players main hand
	 * 
	 * @param player The player
	 * @param item   The item so place the the players hand
	 */
	public void setItemInMainHand(Player player, ItemStack item);

	/**
	 * Get the item from the player off hand
	 * 
	 * @param player The player to get item from
	 * @return {@link ItemStack} or <code>null</code> if the server version does not
	 *         have a off hand
	 */
	public ItemStack getItemInOffHand(Player player);

	/**
	 * Set the item in a players off hand
	 * 
	 * @param player The player
	 * @param item   The item so place the the players hand
	 */
	public void setItemInOffHand(Player player, ItemStack item);

	/**
	 * Get the ping of a player
	 * 
	 * @param player The player to get the ping of
	 * @return ping
	 */
	public int getPlayerPing(Player player);

	public void damagePlayer(Player player, PlayerDamageReason reason, float damage);

	/**
	 * Get the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to get the max health from
	 * 
	 * @return The max health of the player
	 */
	public double getEntityMaxHealth(LivingEntity livingEntity);

	/**
	 * Set the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to reset the set health of
	 * @param health       The new health value
	 */
	public void setEntityMaxHealth(LivingEntity livingEntity, double health);

	/**
	 * Reset the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to reset the max health of
	 */
	public void resetEntityMaxHealth(LivingEntity livingEntity);

	/**
	 * Get the recent server TPS
	 * 
	 * @return Array with recent TPS
	 */
	public double[] getRecentTps();

	public void cloneBlockData(Block source, Block target);

	public void sendTabList(Player player, String header, String footer);

	public void setColoredBlock(Block block, DyeColor color, ColoredBlockType type);
	
	public ItemBuilderRecordList getItembBuilderRecordList();
	
	/**
	 * Set the {@link MapView} of a map {@link ItemStack}
	 * @param item The {@link ItemStack} to set the map view of
	 * @param mapView The {@link MapView} to apply
	 */
	public void attachMapView(ItemStack item, MapView mapView);
}