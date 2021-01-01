package net.zeeraa.novacore.spigot.version.v1_16_R3;

import java.lang.reflect.Field;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.DamageSource;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.zeeraa.novacore.spigot.abstraction.PlayerDamageReason;
import net.zeeraa.novacore.spigot.abstraction.ColoredBlockType;

public class VersionIndependentUtils implements net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils {
	@Override
	public void setBlockAsPlayerSkull(Block block) {
		block.setType(Material.PLAYER_HEAD);

		block.getState().update(true);
	}

	@Override
	public ItemStack getItemInMainHand(Player player) {
		return player.getInventory().getItemInMainHand();
	}

	@Override
	public ItemStack getItemInOffHand(Player player) {
		return player.getInventory().getItemInOffHand();
	}

	@Override
	public double getEntityMaxHealth(LivingEntity livingEntity) {
		return livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	}

	@Override
	public void setEntityMaxHealth(LivingEntity livingEntity, double health) {
		livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
	}

	@Override
	public void resetEntityMaxHealth(LivingEntity livingEntity) {
		livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
	}

	@SuppressWarnings("deprecation")
	@Override
	public double[] getRecentTps() {
		// Deprecated but still the way spigot does it in the /tps command
		return MinecraftServer.getServer().recentTps;
	}

	@Override
	public int getPlayerPing(Player player) {
		return ((CraftPlayer) player).getHandle().ping;
	}

	@Override
	public void cloneBlockData(Block source, Block target) {
		target.setBlockData(source.getBlockData());
	}

	@Override
	public void setItemInMainHand(Player player, ItemStack item) {
		player.getInventory().setItemInMainHand(item);
	}

	@Override
	public void setItemInOffHand(Player player, ItemStack item) {
		player.getInventory().setItemInOffHand(item);
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

	@Override
	public void setColoredBlock(Block block, DyeColor color, ColoredBlockType type) {
		Material material;
		if (type == ColoredBlockType.GLASS_BLOCK) {
			switch (color) {
			case BLACK:
				material = Material.BLACK_STAINED_GLASS;
				break;
				
			case BLUE:
				material = Material.BLUE_STAINED_GLASS;
				break;
				
			case BROWN:
				material = Material.BROWN_STAINED_GLASS;
				break;
				
			case CYAN:
				material = Material.CYAN_STAINED_GLASS;
				break;
				
			case GRAY:
				material = Material.LIGHT_GRAY_STAINED_GLASS;
				break;
				
			case GREEN:
				material = Material.GREEN_STAINED_GLASS;
				break;
				
			case LIGHT_BLUE:
				material = Material.LIGHT_BLUE_STAINED_GLASS;
				break;
				
			case LIGHT_GRAY:
				material = Material.LIGHT_GRAY_STAINED_GLASS;
				break;
				
			case LIME:
				material = Material.LIME_STAINED_GLASS;
				break;
				
			case MAGENTA:
				material = Material.MAGENTA_STAINED_GLASS;
				break;

			case ORANGE:
				material = Material.ORANGE_STAINED_GLASS;
				break;
				
			case PINK:
				material = Material.PINK_STAINED_GLASS;
				break;
				
			case PURPLE:
				material = Material.PURPLE_STAINED_GLASS;
				break;
				
			case RED:
				material = Material.RED_STAINED_GLASS;
				break;
				
			case YELLOW:
				material = Material.YELLOW_STAINED_GLASS;
				break;

			default:
				material = Material.AIR;
				break;
			}
		} else if (type == ColoredBlockType.GLASS_PANE) {
			switch (color) {
			case BLACK:
				material = Material.BLACK_STAINED_GLASS_PANE;
				break;
				
			case BLUE:
				material = Material.BLUE_STAINED_GLASS_PANE;
				break;
				
			case BROWN:
				material = Material.BROWN_STAINED_GLASS_PANE;
				break;
				
			case CYAN:
				material = Material.CYAN_STAINED_GLASS_PANE;
				break;
				
			case GRAY:
				material = Material.LIGHT_GRAY_STAINED_GLASS_PANE;
				break;
				
			case GREEN:
				material = Material.GREEN_STAINED_GLASS_PANE;
				break;
				
			case LIGHT_BLUE:
				material = Material.LIGHT_BLUE_STAINED_GLASS_PANE;
				break;
				
			case LIGHT_GRAY:
				material = Material.LIGHT_GRAY_STAINED_GLASS_PANE;
				break;
				
			case LIME:
				material = Material.LIME_STAINED_GLASS_PANE;
				break;
				
			case MAGENTA:
				material = Material.MAGENTA_STAINED_GLASS_PANE;
				break;

			case ORANGE:
				material = Material.ORANGE_STAINED_GLASS_PANE;
				break;
				
			case PINK:
				material = Material.PINK_STAINED_GLASS_PANE;
				break;
				
			case PURPLE:
				material = Material.PURPLE_STAINED_GLASS_PANE;
				break;
				
			case RED:
				material = Material.RED_STAINED_GLASS_PANE;
				break;
				
			case YELLOW:
				material = Material.YELLOW_STAINED_GLASS_PANE;
				break;
				
			default:
				material = Material.AIR;
				break;
			}
		} else if(type == ColoredBlockType.WOOL) {
			switch (color) {
			case BLACK:
				material = Material.BLACK_WOOL;
				break;
				
			case BLUE:
				material = Material.BLUE_WOOL;
				break;
				
			case BROWN:
				material = Material.BROWN_WOOL;
				break;
				
			case CYAN:
				material = Material.CYAN_WOOL;
				break;
				
			case GRAY:
				material = Material.LIGHT_GRAY_WOOL;
				break;
				
			case GREEN:
				material = Material.GREEN_WOOL;
				break;
				
			case LIGHT_BLUE:
				material = Material.LIGHT_BLUE_WOOL;
				break;
				
			case LIGHT_GRAY:
				material = Material.LIGHT_GRAY_WOOL;
				break;
				
			case LIME:
				material = Material.LIME_WOOL;
				break;
				
			case MAGENTA:
				material = Material.MAGENTA_WOOL;
				break;

			case ORANGE:
				material = Material.ORANGE_WOOL;
				break;
				
			case PINK:
				material = Material.PINK_WOOL;
				break;
				
			case PURPLE:
				material = Material.PURPLE_WOOL;
				break;
				
			case RED:
				material = Material.RED_WOOL;
				break;
				
			case YELLOW:
				material = Material.YELLOW_WOOL;
				break;
				
			default:
				material = Material.AIR;
				break;
			}
		} else {
			material = Material.AIR;
		}

		block.setType(material);
	}
}