package net.zeeraa.novacore.spigot.abstraction.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;

public abstract class MinecraftChannelDuplexHandler extends ChannelDuplexHandler {

    private final Player player;

    public MinecraftChannelDuplexHandler(Player player) {
        this.player = player;
    }


    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
        if (!readPacket(player, packet)) {
            return;
        }
        super.channelRead(channelHandlerContext,packet);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
        super.write(channelHandlerContext,packet,channelPromise);
    }

    public abstract boolean readPacket(Player player, Object packet) throws NoSuchFieldException, IllegalAccessException;
}
