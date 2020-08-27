package net.zeeraa.novacore.spigot.module;

/**
 * Represents the reason why a module failed to enable
 * 
 * @author Zeeraa
 */
public enum ModuleEnableFailureReason {
	/**
	 * An exception occurred while enabling the module
	 */
	EXCEPTION,
	/**
	 * A module that this module depends on has not been loaded
	 */
	MISSING_DEPENDENCY,
	/**
	 * Failed to enable one of this modules dependencies
	 */
	DEPENDENCY_ENABLE_FAILED,
	/**
	 * The module has already been enabled
	 */
	ALREADY_ENABLED;
}