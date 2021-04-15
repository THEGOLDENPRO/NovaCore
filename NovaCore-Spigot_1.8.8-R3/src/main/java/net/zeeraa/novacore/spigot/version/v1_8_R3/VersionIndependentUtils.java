package net.zeeraa.novacore.spigot.version.v1_8_R3;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.zeeraa.novacore.spigot.abstraction.PlayerDamageReason;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependantSound;
import net.zeeraa.novacore.spigot.abstraction.ColoredBlockType;
import net.zeeraa.novacore.spigot.abstraction.ItemBuilderRecordList;

public class VersionIndependentUtils extends net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils {
	private ItemBuilderRecordList itemBuilderRecordList;

	public VersionIndependentUtils() {
		itemBuilderRecordList = new ItemBuilderRecordList1_8();
	}

	@Override
	public ItemBuilderRecordList getItembBuilderRecordList() {
		return itemBuilderRecordList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBlockAsPlayerSkull(Block block) {
		block.setType(Material.SKULL);
		Skull skull = (Skull) block.getState();
		skull.setSkullType(SkullType.PLAYER);

		block.getState().update(true);

		block.setData((byte) 1);
	}

	@Override
	public ItemStack getItemInMainHand(Player player) {
		return player.getItemInHand();
	}

	@Override
	public ItemStack getItemInOffHand(Player player) {
		return null; // 1.8 does not have a player off hand
	}

	@Override
	public double getEntityMaxHealth(LivingEntity livingEntity) {
		return livingEntity.getMaxHealth();
	}

	@Override
	public void setEntityMaxHealth(LivingEntity livingEntity, double health) {
		livingEntity.setMaxHealth(health);
	}

	@Override
	public void resetEntityMaxHealth(LivingEntity livingEntity) {
		livingEntity.resetMaxHealth();
	}

	@Override
	public double[] getRecentTps() {
		return MinecraftServer.getServer().recentTps;
	}

	@Override
	public int getPlayerPing(Player player) {
		return ((CraftPlayer) player).getHandle().ping;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void cloneBlockData(Block source, Block target) {
		target.setData(source.getData());
	}

	@Override
	public void setItemInMainHand(Player player, ItemStack item) {
		player.getInventory().setItemInHand(item);
	}

	@Override
	public void setItemInOffHand(Player player, ItemStack item) {
		// nothing to do here
	}

	@Override
	public void sendTabList(Player player, String header, String footer) {
		CraftPlayer craftplayer = (CraftPlayer) player;
		PlayerConnection connection = craftplayer.getHandle().playerConnection;
		IChatBaseComponent headerJSON = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent footerJSON = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

		try {
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, headerJSON);
			headerField.setAccessible(!headerField.isAccessible());

			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, footerJSON);
			footerField.setAccessible(!footerField.isAccessible());
		} catch (Exception e) {
			e.printStackTrace();
		}

		connection.sendPacket(packet);
	}

	@Override
	public void damagePlayer(Player player, PlayerDamageReason reason, float damage) {
		DamageSource source;

		switch (reason) {
		case FALL:
			source = DamageSource.FALL;

		case FALLING_BLOCK:
			source = DamageSource.FALLING_BLOCK;
			break;
		case OUT_OF_WORLD:
			source = DamageSource.OUT_OF_WORLD;
			break;

		case BURN:
			source = DamageSource.BURN;
			break;

		case LIGHTNING:
			source = DamageSource.LIGHTNING;
			break;

		case MAGIC:
			source = DamageSource.MAGIC;
			break;

		case DROWN:
			source = DamageSource.DROWN;
			break;

		case STARVE:
			source = DamageSource.STARVE;
			break;

		case LAVA:
			source = DamageSource.LAVA;
			break;

		case GENERIC:
			source = DamageSource.GENERIC;
			break;

		default:
			source = DamageSource.GENERIC;
			break;
		}

		((CraftPlayer) player).getHandle().damageEntity(source, damage);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setColoredBlock(Block block, DyeColor color, ColoredBlockType type) {
		Material material;

		switch (type) {
		case GLASS_PANE:
			material = Material.STAINED_GLASS_PANE;
			break;

		case GLASS_BLOCK:
			material = Material.STAINED_GLASS;
			break;

		case WOOL:
			material = Material.WOOL;
			break;

		default:
			material = Material.STAINED_GLASS;
			break;
		}

		block.setType(material);

		block.setData(color.getWoolData());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void attachMapView(ItemStack item, MapView mapView) {
		item.setDurability(mapView.getId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public MapView getAttachedMapView(ItemStack item) {
		return Bukkit.getMap(item.getDurability());
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMapViewId(MapView mapView) {
		return (int) mapView.getId();
	}

	@Override
	public void playSound(Player player, Location location, VersionIndependantSound sound, float volume, float pitch) {
		Sound realSound = null;

		switch (sound) {
		case NOTE_PLING:
			realSound = Sound.NOTE_PLING;
			break;

		case WITHER_DEATH:
			realSound = Sound.WITHER_DEATH;
			break;

		case WITHER_HURT:
			realSound = Sound.WITHER_HURT;
			break;

		default:
			System.err.println("[VersionIndependentUtils] VersionIndependantSound " + sound.name() + " is not defined in this version. Please add it to " + this.getClass().getName());
			return;
		}

		player.playSound(location, realSound, volume, pitch);
	}
}