package net.zeeraa.novacore.spigot.abstraction.packet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


/**
 * Packet Manager for registering players
 *
 * @author Bruno
 */
public abstract class PacketManager implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.registerPlayer(e.getPlayer());
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerLeave(PlayerQuitEvent e) {
        this.removePlayer(e.getPlayer());
    }

    /**
     * Registers/injects all online {@link Player}s.
     */
    public void registerOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(this::registerPlayer);
    }

    /**
     * Registers/injects specific {@link Player}.
     * @param player {@link Player}.
     */
    public abstract void registerPlayer(Player player);

    /**
     * Removes all online {@link Player}s
     */
    public void removeOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(this::removePlayer);
    }

    /**
     * Removes specific {@link Player}
     * @param player {@link Player}.
     */
    public abstract void removePlayer(Player player);

}
