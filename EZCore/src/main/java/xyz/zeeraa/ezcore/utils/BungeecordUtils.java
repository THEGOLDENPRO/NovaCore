package xyz.zeeraa.ezcore.utils;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import xyz.zeeraa.ezcore.EZCore;

/**
 * This class contains some useful utilities for bungecord communication
 * 
 * @author Zeeraa
 */
public class BungeecordUtils {
	/**
	 * Send a player to another server in a bungeecord setup
	 * 
	 * @param player {@link Player} to send
	 * @param server Name of the server to send the player to
	 */
	public static void sendToServer(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Connect");
		out.writeUTF(server);

		player.sendPluginMessage(EZCore.getInstance(), "BungeeCord", out.toByteArray());
	}
}