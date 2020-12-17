package net.zeeraa.novacore.spigot.platformindependent;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.commons.platformindependent.PlatformIndependentBungeecordAPI;
import net.zeeraa.novacore.spigot.utils.BungeecordUtils;

public class SpigotPlatformIndependentBungeecordAPI implements PlatformIndependentBungeecordAPI {
	@Override
	public boolean sendPlayerToServer(UUID player, String server) {
		Player p = Bukkit.getServer().getPlayer(player);
		if (p != null) {
			BungeecordUtils.sendToServer(p, server);
			return true;
		}
		return false;
	}
}