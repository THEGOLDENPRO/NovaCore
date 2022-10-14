package net.zeeraa.novacore.spigot.version.v1_8_R3.packet;

import io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketManager extends net.zeeraa.novacore.spigot.abstraction.packet.PacketManager {


    private final PacketManager instance = this;

    public void registerOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(instance::registerPlayer);
    }

    public void registerPlayer(Player player) {
        MinecraftChannelDuplexHandler channelDuplexHandler = new MinecraftChannelDuplexHandler(player);
        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getUniqueId().toString(), channelDuplexHandler);
    }

    @Override
    public void removeOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(instance::removePlayer);
    }

    @Override
    public void removePlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getUniqueId().toString());
            return null;
        });
    }

}
