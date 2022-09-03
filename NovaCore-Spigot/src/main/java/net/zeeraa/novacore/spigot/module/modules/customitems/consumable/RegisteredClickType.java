package net.zeeraa.novacore.spigot.module.modules.customitems.consumable;

import org.bukkit.event.player.PlayerInteractEvent;

public enum RegisteredClickType {
	RIGHT_CLICK_BLOCK, LEFT_CLICK_BLOCK, RIGHT_CLICK_AIR, LEFT_CLICK_AIR, PHYSICAL;

	public static RegisteredClickType fromEvent(PlayerInteractEvent event) {
		switch (event.getAction()) {
		case RIGHT_CLICK_AIR:
			return RIGHT_CLICK_AIR;

		case LEFT_CLICK_AIR:
			return LEFT_CLICK_BLOCK;

		case LEFT_CLICK_BLOCK:
			return LEFT_CLICK_BLOCK;

		case RIGHT_CLICK_BLOCK:
			return RIGHT_CLICK_BLOCK;

		case PHYSICAL:
			return PHYSICAL;
		}
		return null;
	}
}