package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.abstraction.enums.Hand;

public class PlayerAttemptDestroyBlockEvent extends PlayerSwingEvent {
	private Block block;

	public PlayerAttemptDestroyBlockEvent(Player player, long timestamp, Block block) {
		super(player, timestamp, Hand.MAIN_HAND);
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}
}