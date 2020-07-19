package xyz.zeeraa.ezcore.module;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import xyz.zeeraa.ezcore.EZCore;

/**
 * Represents a module that can be loaded, enabled and disabled.<br>
 * Modules need to be loaded with {@link ModuleManager#loadModule(Class)}<br>
 * <br>
 * All modules will be disabled in {@link EZCore#onDisable()}
 * 
 * @author Zeeraa
 */
public abstract class EZModule {
	private boolean enabled = false;
	private ModuleEnableFailureReason enableFailureReason = null;

	/**
	 * Get the module display name
	 * 
	 * @return name
	 */
	public abstract String getName();

	/**
	 * Get module dependencies. This will return the class of all modules this
	 * module depends on. All the dependencies will be enabled before this module is
	 * enabled. The dependencies can in no way have this module as a dependency or
	 * the enable function will cause a {@link StackOverflowError}
	 * 
	 * @return Array with all dependencies
	 */
	public Class<? extends EZModule>[] getDependencies() {
		return null;
	}

	/**
	 * Called when the module is loaded by {@link ModuleManager}
	 */
	public void onLoad() {
	}

	/**
	 * Called when the module is enabling. this is called before registering events
	 */
	public void onEnable() {
	}

	/**
	 * Called when the module is disabling. this is called after disabling events
	 */
	public void onDisable() {
	}

	/**
	 * Check why the module failed to enable. This will return <code>null</code> if
	 * the module got disabled or enabled again after the failure
	 * 
	 * @return {@link ModuleEnableFailureReason} on fail, <code>null</code> on
	 *         success
	 */
	public ModuleEnableFailureReason getEnableFailureReason() {
		return this.enableFailureReason;
	}

	/**
	 * Enable the module and register events. If this fails
	 * {@link EZModule#getEnableFailureReason()} can be used to get the reason for
	 * the failure
	 * 
	 * @return <code>true</code> if successful, <code>false</code> if
	 *         {@link EZModule#onEnable()} failed
	 */
	public boolean enable() {
		if (this.enabled) {
			this.enableFailureReason = ModuleEnableFailureReason.ALREADY_ENABLED;
			return false;
		}

		Class<? extends EZModule>[] dependencies = this.getDependencies();

		if (dependencies != null) {
			for (Class<? extends EZModule> clazz : dependencies) {
				if (!ModuleManager.moduleExists(clazz)) {
					this.enableFailureReason = ModuleEnableFailureReason.MISSING_DEPENDENCY;
					return false;
				}

				if (!ModuleManager.isEnabled(clazz)) {
					if (!ModuleManager.enable(clazz)) {
						// TODO: log dependency failure reason
						this.enableFailureReason = ModuleEnableFailureReason.DEPENDENCY_ENABLE_FAILED;
						return false;
					}
				}
			}
		}

		try {
			this.onEnable();
			if (this instanceof Listener) {
				Bukkit.getPluginManager().registerEvents((Listener) this, EZCore.getInstance());
			}
			this.enableFailureReason = null;
			this.enabled = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.enableFailureReason = ModuleEnableFailureReason.EXCEPTION;
			return false;
		}
		return true;
	}

	/**
	 * Disable the module and unregister events
	 *
	 * @return <code>false</code> if an {@link Exception} was thrown by
	 *         {@link EZModule#onDisable()} or if the module was already disabled
	 */
	public boolean disable() {
		if (!this.enabled) {
			return false;
		}

		this.enableFailureReason = null;
		boolean returnValue;
		if (this instanceof Listener) {
			HandlerList.unregisterAll((Listener) this);
		}
		try {
			this.onDisable();
			returnValue = true;
		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
		}

		return returnValue;
	}

	/**
	 * Get the class name of the module. used to identify the module in
	 * {@link ModuleManager}
	 * 
	 * @return Call name of the module
	 */
	public String getClassName() {
		return this.getClass().getName();
	}

	/**
	 * CheckS if the module has been enabled
	 * 
	 * @return <code>true</code> if the module has been enabled
	 */
	public boolean isEnabled() {
		return this.enabled;
	}
}