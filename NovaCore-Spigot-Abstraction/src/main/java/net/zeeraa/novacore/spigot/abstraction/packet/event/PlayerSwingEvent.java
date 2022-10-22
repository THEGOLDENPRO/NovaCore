package net.zeeraa.novacore.spigot.abstraction.packet.event;

import net.zeeraa.novacore.spigot.abstraction.enums.Hand;
import org.bukkit.entity.Player;

/**
 * Check if player swinged their arm
 *
 * WARNING: This event is Asynchronous and might be dangerous to use
 */
public class PlayerSwingEvent extends AsyncPacketEvent {
	private long timestamp;
	private Hand hand;

	public PlayerSwingEvent(Player player, long timestamp, Hand hand) {
		super(player);
		this.timestamp = timestamp;
		this.hand = hand;

	}

	public long getTimestamp() {
		return timestamp;
	}

	public Hand getHand() {
		return hand;
	}
}