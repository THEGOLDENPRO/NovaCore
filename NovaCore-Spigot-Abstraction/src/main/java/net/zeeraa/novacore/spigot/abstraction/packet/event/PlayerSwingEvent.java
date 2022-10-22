package net.zeeraa.novacore.spigot.abstraction.packet.event;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.Hand;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
