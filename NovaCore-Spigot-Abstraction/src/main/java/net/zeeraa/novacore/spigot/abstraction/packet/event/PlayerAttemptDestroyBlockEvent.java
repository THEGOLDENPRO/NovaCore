package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerAttemptDestroyBlockEvent extends PlayerSwingEvent {

    Block block;

    public PlayerAttemptDestroyBlockEvent(Player player, long timestamp, Block block) {
        super(player, timestamp);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

}
