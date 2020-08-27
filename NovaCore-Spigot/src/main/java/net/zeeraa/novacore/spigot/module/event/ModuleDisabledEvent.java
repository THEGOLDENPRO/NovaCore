package net.zeeraa.novacore.spigot.module.event;

import org.bukkit.event.HandlerList;

import net.zeeraa.novacore.spigot.module.NovaModule;

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

	public ModuleDisabledEvent(NovaModule module) {
		super(module);
	}
}