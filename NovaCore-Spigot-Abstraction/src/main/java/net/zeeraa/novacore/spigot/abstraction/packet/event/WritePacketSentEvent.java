package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.entity.Player;

public class WritePacketSentEvent extends PacketSentEvent{
    public WritePacketSentEvent(Player player, Object packetSent) {
        super(player, packetSent);
    }
}
