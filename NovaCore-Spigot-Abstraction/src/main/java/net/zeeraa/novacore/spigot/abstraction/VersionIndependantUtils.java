package net.zeeraa.novacore.spigot.abstraction;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependantSound;

public abstract class VersionIndependantUtils {
	private static VersionIndependantUtils instance;
	
	public static VersionIndependantUtils get() {
		return VersionIndependantUtils.getInstance();
	}
	
	public static VersionIndependantUtils getInstance() {
		return instance;
	}
	
	public static void setInstance(VersionIndependantUtils instance) {
		VersionIndependantUtils.instance = instance;
	}
	
	public abstract void setBlockAsPlayerSkull(Block block);

	/**
	 * Get the item from the player main hand
	 * 
	 * @param player The player to get item from
	 * @return {@link ItemStack} from the players main hand
	 */
	public abstract ItemStack getItemInMainHand(Player player);

	/**
	 * Set the item in a players main hand
	 * 
	 * @param player The player
	 * @param item   The item so place the the players hand
	 */
	public abstract void setItemInMainHand(Player player, ItemStack item);

	/**
	 * Get the item from the player off hand
	 * 
	 * @param player The player to get item from
	 * @return {@link ItemStack} or <code>null</code> if the server version does not
	 *         have a off hand
	 */
	public abstract ItemStack getItemInOffHand(Player player);

	/**
	 * Set the item in a players off hand
	 * 
	 * @param player The player
	 * @param item   The item so place the the players hand
	 */
	public abstract void setItemInOffHand(Player player, ItemStack item);

	/**
	 * Get the ping of a player
	 * 
	 * @param player The player to get the ping of
	 * @return ping
	 */
	public abstract int getPlayerPing(Player player);

	public abstract void damagePlayer(Player player, PlayerDamageReason reason, float damage);

	/**
	 * Get the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to get the max health from
	 * 
	 * @return The max health of the player
	 */
	public abstract double getEntityMaxHealth(LivingEntity livingEntity);

	/**
	 * Set the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to reset the set health of
	 * @param health       The new health value
	 */
	public abstract void setEntityMaxHealth(LivingEntity livingEntity, double health);

	/**
	 * Reset the max heath of a {@link LivingEntity}
	 * 
	 * @param livingEntity The entity to reset the max health of
	 */
	public abstract void resetEntityMaxHealth(LivingEntity livingEntity);

	/**
	 * Get the recent server TPS
	 * 
	 * @return Array with recent TPS
	 */
	public abstract double[] getRecentTps();

	public abstract void cloneBlockData(Block source, Block target);

	public abstract void sendTabList(Player player, String header, String footer);

	public abstract void setColoredBlock(Block block, DyeColor color, ColoredBlockType type);

	public abstract ItemBuilderRecordList getItembBuilderRecordList();

	/**
	 * Set the {@link MapView} of a map {@link ItemStack}
	 * 
	 * @param item    The {@link ItemStack} to set the map view of
	 * @param mapView The {@link MapView} to apply
	 */
	public abstract void attachMapView(ItemStack item, MapView mapView);

	/**
	 * Get the {@link MapView} of a map {@link ItemStack}
	 * 
	 * @param item The {@link ItemStack} to get the map view of
	 * @return mapView The {@link MapView} used for that item
	 */
	public abstract MapView getAttachedMapView(ItemStack item);

	public abstract int getMapViewId(MapView mapView);

	/**
	 * Play a sound for a player
	 * 
	 * @param player   The player to play the sound for
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependantSound} to play
	 */
	public void playSound(Player player, Location location, VersionIndependantSound sound) {
		this.playSound(player, location, sound, 1F, 1F);
	}

	/**
	 * Play a sound for a player
	 * 
	 * @param player   The player to play the sound for
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependantSound} to play
	 * @param volume   The volume of the sound
	 * @param pitch    The pitck of the sound
	 */
	public abstract void playSound(Player player, Location location, VersionIndependantSound sound, float volume, float pitch);
}