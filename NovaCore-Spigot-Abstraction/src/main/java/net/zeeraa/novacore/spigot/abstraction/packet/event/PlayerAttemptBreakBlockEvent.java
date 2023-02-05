package net.zeeraa.novacore.spigot.abstraction.packet.event;

import net.zeeraa.novacore.spigot.abstraction.enums.Hand;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Check if player is attempting to break a block
 *
 * WARNING: This event is Asynchronous and might be dangerous to use
 */
public class PlayerAttemptBreakBlockEvent extends PlayerSwingEvent {
	Block block;

	public PlayerAttemptBreakBlockEvent(Player player, long timestamp, Block block) {
		super(player, timestamp, Hand.MAIN_HAND);
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}
}