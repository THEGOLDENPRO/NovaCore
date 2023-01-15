package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.graceperiod.graceperiod.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GracePeriodFinishEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GracePeriodFinishEvent() {
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}