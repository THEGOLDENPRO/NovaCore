package net.zeeraa.novacore.spigot.abstraction.packet.event;

import org.bukkit.entity.Player;

/**
 * Check if player teleported as a spectator
 *
 * WARNING: This event is Asynchronous and might be dangerous to use
 */
public class SpectatorTeleportEvent extends AsyncPacketEvent {
    private final Player target;
    public SpectatorTeleportEvent(Player player, Player target) {
        super(player);
        this.target = target;
    }

    public Player getTo() {
        return target;
    }

}
