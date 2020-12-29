package net.zeeraa.novacore.spigot.version.v1_8_R3;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class VersionIndependentUtils implements net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils {
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
}