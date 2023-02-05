package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.entity.Player;

public class ReadPacketSentEvent extends PacketSentEvent {
	public ReadPacketSentEvent(Player player, Object packetSent) {
		super(player, packetSent);
	}
}