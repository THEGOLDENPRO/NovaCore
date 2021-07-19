package net.zeeraa.novacore.spigot.version.v1_12_R1;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;


public class ActionBar implements net.zeeraa.novacore.spigot.abstraction.ActionBar {
	@Override
	public void sendMessage(Player player, String message) {
		PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"), ChatMessageType.GAME_INFO);
	     ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}