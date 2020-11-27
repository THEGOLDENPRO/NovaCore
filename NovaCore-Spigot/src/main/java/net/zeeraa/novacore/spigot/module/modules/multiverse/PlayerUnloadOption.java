package net.zeeraa.novacore.spigot.module.modules.multiverse;

public enum PlayerUnloadOption {
	/**
	 * Kicks the player when the world they are in gets unloaded
	 */
	KICK,
	/**
	 * Send the player the the first available world when the world they are in gets
	 * unloaded
	 */
	SEND_TO_FIRST;
}