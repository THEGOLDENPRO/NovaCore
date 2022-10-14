package net.zeeraa.novacore.spigot.abstraction.packet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class PacketManager implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.registerPlayer(e.getPlayer());
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerLeave(PlayerQuitEvent e) {
        this.removePlayer(e.getPlayer());
    }

    public abstract void registerOnlinePlayers();

    public abstract void registerPlayer(Player player);

    public abstract void removeOnlinePlayers();

    public abstract void removePlayer(Player player);

}
