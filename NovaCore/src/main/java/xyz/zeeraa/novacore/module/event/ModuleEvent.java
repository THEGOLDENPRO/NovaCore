package xyz.zeeraa.novacore.module.event;

import org.bukkit.event.Event;

import xyz.zeeraa.novacore.module.NovaModule;

/**
 * Represents a event with a {@link NovaModule} involved
 * 
 * @author Zeeraa
 */
public abstract class ModuleEvent extends Event {
	private NovaModule module;

	public ModuleEvent(NovaModule module) {
		this.module = module;
	}

	/**
	 * Get the module involved in this event
	 * 
	 * @return The {@link NovaModule}
	 */
	public NovaModule getModule() {
		return module;
	}
}