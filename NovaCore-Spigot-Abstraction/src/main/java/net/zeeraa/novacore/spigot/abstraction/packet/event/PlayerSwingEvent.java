package net.zeeraa.novacore.spigot.abstraction.packet.event;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.Hand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Set;

public class PlayerSwingEvent extends PacketEvent {
	private long timestamp;
	private Hand hand;

	public PlayerSwingEvent(Player player, long timestamp, Hand hand) {
		super(player);
		this.timestamp = timestamp;
		this.hand = hand;
		try {
			if (canBreak(player, player.getTargetBlock((Set<Material>) null, 5))) {
				Bukkit.getPluginManager().callEvent(new PlayerAttemptDestroyBlockEvent(player, timestamp, player.getTargetBlock((Set<Material>) null, 5)));
			}
		} catch (Exception ignored) {
		}
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Hand getHand() {
		return hand;
	}

	private boolean canBreak(Player player, Block block) {
		if (hand != Hand.MAIN_HAND) {
			return false;
		}
		if (block.getType() == Material.AIR) {
			return false;
		}
		if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
			return false;
		}
		if (player.getGameMode() == GameMode.SURVIVAL) {
			return true;
		}
		try {
			return VersionIndependentUtils.get().canBreakBlock(player.getItemInHand(), block.getType());
		} catch (Exception ignored) {
			return false;
		}
	}
}