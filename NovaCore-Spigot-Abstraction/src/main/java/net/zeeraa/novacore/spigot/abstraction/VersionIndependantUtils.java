package net.zeeraa.novacore.spigot.abstraction;

import java.lang.reflect.Field;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.map.MapView;

import net.zeeraa.novacore.spigot.abstraction.enums.ColoredBlockType;
import net.zeeraa.novacore.spigot.abstraction.enums.NovaCoreGameVersion;
import net.zeeraa.novacore.spigot.abstraction.enums.PlayerDamageReason;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependantMetarial;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependantSound;
import net.zeeraa.novacore.spigot.abstraction.log.AbstractionLogger;

public abstract class VersionIndependantUtils {
	private static VersionIndependantUtils instance;

	public static VersionIndependantUtils get() {
		return instance;
	}

	public static VersionIndependantUtils getInstance() {
		return instance;
	}

	public static void setInstance(VersionIndependantUtils instance) {
		VersionIndependantUtils.instance = instance;
	}

	public abstract LabyModProtocol getLabyModProtocol();

	/**
	 * Get the {@link NovaCoreGameVersion} nova core is using for the server
	 * 
	 * @return The {@link NovaCoreGameVersion} for thie server
	 */
	public abstract NovaCoreGameVersion getNovaCoreGameVersion();

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

	public abstract void setShapedRecipeIngredientAsColoredBlock(ShapedRecipe recipe, char ingredient, ColoredBlockType type, DyeColor color);

	public abstract void addShapelessRecipeIngredientAsColoredBlock(ShapelessRecipe recipe, char ingredient, ColoredBlockType type, DyeColor color);

	public abstract ItemStack getColoredItem(DyeColor color, ColoredBlockType type);

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
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Player player, Location location, VersionIndependantSound sound) {
		return this.playSound(player, location, sound, 1F, 1F);
	}

	/**
	 * Play a sound for a player
	 * 
	 * @param player   The player to play the sound for
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependantSound} to play
	 * @param volume   The volume of the sound
	 * @param pitch    The pitch of the sound
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Player player, Location location, VersionIndependantSound sound, float volume, float pitch) {
		Sound realSound = this.getSound(sound);

		if (sound == null) {
			return false;
		}

		player.playSound(location, realSound, volume, pitch);
		return true;
	}

	/**
	 * Play a sound at a location
	 * 
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependantSound} to play
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Location location, VersionIndependantSound sound) {
		return this.playSound(location, sound, 1F, 1F);
	}

	/**
	 * Play a sound at a location
	 * 
	 * @param location The location of the sound
	 * @param sound    The {@link VersionIndependantSound} to play
	 * @param volume   The volume of the sound
	 * @param pitch    The pitch of the sound
	 * @return <code>true</code> on success. <code>false</code> if the sound is not
	 *         configured
	 */
	public boolean playSound(Location location, VersionIndependantSound sound, float volume, float pitch) {
		Sound realSound = this.getSound(sound);

		if (sound == null) {
			return false;
		}

		location.getWorld().playSound(location, realSound, volume, pitch);
		return true;
	}

	/**
	 * Get the {@link Sound} from a {@link VersionIndependantSound}
	 * 
	 * @param sound The {@link VersionIndependantSound} to get
	 * @return resulting {@link Sound}
	 */
	public abstract Sound getSound(VersionIndependantSound sound);

	public abstract void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut);

	public abstract ItemStack getPlayerSkullWithBase64Texture(String b64stringtexture);

	public static <T> Field getField(Class<?> target, String name, Class<T> fieldType, int index) {
		for (final Field field : target.getDeclaredFields()) {
			if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
				field.setAccessible(true);
				return field;
			}
		}

		// Search in parent classes
		if (target.getSuperclass() != null)
			return getField(target.getSuperclass(), name, fieldType, index);
		throw new IllegalArgumentException("Cannot find field with type " + fieldType);
	}

	public abstract VersionIndependantItems getVersionIndependantItems();

	public abstract void setShapedRecipeIngredientAsPlayerSkull(ShapedRecipe recipe, char ingredient);

	/**
	 * Get the {@link Material} from {@link VersionIndependantMetarial}
	 * 
	 * @param material The {@link VersionIndependantMetarial}
	 * @return The minecraft {@link Material}
	 */
	public abstract Material getMaterial(VersionIndependantMetarial material);

	public ItemStack getItemStack(VersionIndependantMetarial material) {
		Material mcMaterial = this.getMaterial(material);
		if (mcMaterial == null) {
			AbstractionLogger.getLogger().error("VersionIndependantUtils", "Failed to get version independant material " + material.name() + " for version " + this.getNovaCoreGameVersion().name() + ". This needs to be added");
			return null;
		}

		return new ItemStack(mcMaterial);
	}

	public abstract ItemStack getPlayerSkullitem();

	public boolean isSign(Block block) {
		return this.isSign(block.getType());
	}

	public abstract boolean isSign(Material material);
	
	public abstract void sendActionBarMessage(Player player, String message);
}
