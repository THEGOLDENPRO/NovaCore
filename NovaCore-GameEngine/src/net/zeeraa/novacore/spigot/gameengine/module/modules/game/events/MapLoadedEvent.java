package net.zeeraa.novacore.spigot.gameengine.module.modules.game.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.GameMapData;

public class MapLoadedEvent extends Event {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private GameMapData mapData;

	public MapLoadedEvent(GameMapData mapData) {
		this.mapData = mapData;
	}

	public GameMapData getMapData() {
		return mapData;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
}