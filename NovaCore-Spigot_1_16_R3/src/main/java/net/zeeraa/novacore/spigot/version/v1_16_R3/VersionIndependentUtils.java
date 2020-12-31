package net.zeeraa.novacore.spigot.version.v1_16_R3;

import java.lang.reflect.Field;

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
        IChatBaseComponent headerJSON = ChatSerializer.a("{\"text\": \"" + header +"\"}");
        IChatBaseComponent footerJSON = ChatSerializer.a("{\"text\": \"" + footer +"\"}");
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
}