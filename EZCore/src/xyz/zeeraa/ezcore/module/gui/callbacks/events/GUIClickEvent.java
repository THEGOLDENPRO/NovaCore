package xyz.zeeraa.ezcore.module.gui.callbacks.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GUIClickEvent extends Event {
	private static HandlerList HANDLERS_LIST = new HandlerList();
	
	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
}