package xyz.zeeraa.ezcore.module.event;

import org.bukkit.event.Event;

import xyz.zeeraa.ezcore.module.EZModule;

/**
 * Represents a event with a {@link EZModule} involved
 * 
 * @author Zeeraa
 */
public abstract class ModuleEvent extends Event {
	private EZModule module;

	public ModuleEvent(EZModule module) {
		this.module = module;
	}

	/**
	 * Get the module involved in this event
	 * 
	 * @return The {@link EZModule}
	 */
	public EZModule getModule() {
		return module;
	}
}