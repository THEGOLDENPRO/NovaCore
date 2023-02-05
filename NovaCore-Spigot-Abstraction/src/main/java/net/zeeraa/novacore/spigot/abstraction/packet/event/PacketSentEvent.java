package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PacketSentEvent extends AsyncPacketEvent {
    private static final HandlerList handlers = new HandlerList();
    private Object packet;
    public PacketSentEvent(Player player, Object packetSent) {
        super(player);
        packet = packetSent;
    }

    public Object getPacket() {
        return packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
