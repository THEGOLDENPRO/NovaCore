package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.entity.Player;

public class SpectatorTeleportEvent extends PacketEvent {
	private final Player target;

	public SpectatorTeleportEvent(Player player, Player target) {
		super(player);
		this.target = target;
	}

	public Player getTo() {
		return target;
	}
}