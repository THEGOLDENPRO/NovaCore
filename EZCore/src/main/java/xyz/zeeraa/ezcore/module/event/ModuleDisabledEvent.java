package xyz.zeeraa.ezcore.module.event;

import org.bukkit.event.HandlerList;

import xyz.zeeraa.ezcore.module.EZModule;

/**
 * Called when a module is disabled
 * 
 * @author Zeeraa
 */
public class ModuleDisabledEvent extends ModuleEvent {
	private static final HandlerList HANDLERS = new HandlerList();

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public ModuleDisabledEvent(EZModule module) {
		super(module);
	}
}