package xyz.zeeraa.novacore.module;

import java.util.HashMap;

import xyz.zeeraa.novacore.log.Log;

/**
 * This class is used to register, enable and disable {@link NovaModule}s
 * 
 * @author Zeeraa
 */
public class ModuleManager {
	private static HashMap<String, NovaModule> modules = new HashMap<>();

	/**
	 * Get a {@link HashMap} containing all modules with the module class name as
	 * key and the module as value
	 * 
	 * @return {@link HashMap} containing all modules
	 */
	public static HashMap<String, NovaModule> getModules() {
		return modules;
	}

	/**
	 * Get a loaded module
	 * 
	 * @param clazz The class of the module
	 * @return {@link NovaModule} or null if not loaded
	 */
	public static NovaModule getModule(Class<? extends NovaModule> clazz) {
		return modules.get(clazz.getName());
	}

	/**
	 * Get a loaded module
	 * 
	 * @param className The class name of the module
	 * @return {@link NovaModule} or null if not loaded
	 */
	public static NovaModule getModule(String className) {
		return modules.get(className);
	}

	/**
	 * Check if a module is enabled
	 * 
	 * @param clazz The class of the module
	 * @return <code>true</code> if the module is disabled
	 */
	public static boolean isEnabled(Class<? extends NovaModule> clazz) {
		return isEnabled(clazz.getName());
	}

	/**
	 * Check if a module is enabled
	 * 
	 * @param className The class name of the module
	 * @return <code>true</code> if the module is enabled
	 */
	public static boolean isEnabled(String className) {
		NovaModule module = getModule(className);
		if (module != null) {
			return module.isEnabled();
		}
		return false;
	}

	/**
	 * Check if a module is disabled
	 * 
	 * @param clazz The class of the module
	 * @return <code>true</code> if the module is disabled
	 */
	public static boolean isDisabled(Class<? extends NovaModule> clazz) {
		return !isEnabled(clazz);
	}

	/**
	 * Check if a module is disabled
	 * 
	 * @param className The class name of the module
	 * @return <code>true</code> if the module is disabled
	 */
	public static boolean isDisabled(String className) {
		return !isEnabled(className);
	}

	/**
	 * Enable a module
	 * 
	 * @param clazz The class of the module
	 * @return <code>false</code> if {@link NovaModule#onEnable()} generated a
	 *         exception or if the module failed to enable
	 */
	public static boolean enable(Class<? extends NovaModule> clazz) {
		return enable(clazz.getName());
	}

	/**
	 * Enable a module
	 * 
	 * @param className The class name of the module
	 * @return <code>false</code> if {@link NovaModule#onEnable()} generated a
	 *         exception or if the module failed to enable
	 */
	public static boolean enable(String className) {
		NovaModule module = getModule(className);
		if (module != null) {
			return module.enable();
		}
		return false;
	}

	/**
	 * Disable a module
	 * 
	 * @param clazz The class of the module
	 * @return <code>false</code> if {@link NovaModule#onDisable()} generated a
	 *         exception or if the module failed to disable
	 */
	public static boolean disable(Class<? extends NovaModule> clazz) {
		return disable(clazz.getName());
	}

	/**
	 * Disable a module
	 * 
	 * @param className The class name of the module
	 * @return <code>false</code> if {@link NovaModule#onDisable()} generated a
	 *         exception or if the module failed to disable
	 */
	public static boolean disable(String className) {
		NovaModule module = getModule(className);
		if (module != null) {
			return module.disable();
		}
		return false;
	}

	/**
	 * Get the reason why a module failed to load
	 * 
	 * @param clazz The class of the module
	 * @return The {@link ModuleEnableFailureReason} or <code>null</code> if the
	 *         module did not fail or was disabled
	 */
	public static ModuleEnableFailureReason getEnableFailureReason(Class<? extends NovaModule> clazz) {
		return getEnableFailureReason(clazz.getName());
	}

	/**
	 * Get the reason why a module failed to load
	 * 
	 * @param className The class name of the module
	 * @return The {@link ModuleEnableFailureReason} or <code>null</code> if the
	 *         module did not fail or was disabled
	 */
	public static ModuleEnableFailureReason getEnableFailureReason(String className) {
		NovaModule module = getModule(className);
		if (module != null) {
			return module.getEnableFailureReason();
		}
		return null;
	}

	/**
	 * Check if a module has been loaded
	 * 
	 * @param clazz The class of the module
	 * @return <code>true</code> if the module has been loaded
	 */
	public static boolean moduleExists(Class<? extends NovaModule> clazz) {
		return moduleExists(clazz.getName());
	}

	/**
	 * Check if a module has been loaded
	 * 
	 * @param className The class name of the module
	 * @return <code>true</code> if the module has been loaded
	 */
	public static boolean moduleExists(String className) {
		return modules.containsKey(className);
	}

	/**
	 * Load a module
	 * 
	 * @param clazz The class of the module
	 * @return <code>true</code> on success
	 * 
	 * @throws InvalidModuleNameException if the module name contains spaces
	 */
	public static boolean loadModule(Class<? extends NovaModule> clazz) {
		if (moduleExists(clazz)) {
			Log.warn("Module " + clazz.getName() + " was already loaded");
			return false;
		}
		Log.info("Loading module " + clazz.getName());
		try {
			Object module = clazz.getConstructor().newInstance(new Object[] {});

			if (module instanceof NovaModule) {
				if(((NovaModule) module).getName().contains(" ")) {
					Log.error("The module name can't contain spaces. Module class: " + module.getClass().getName());
					throw new InvalidModuleNameException("The module name can't contain spaces. Module class: " + module.getClass().getName());
				}
				
				((NovaModule) module).onLoad();
				modules.put(((NovaModule) module).getClassName(), (NovaModule) module);
				return true;
			} else {
				Log.error("Module " + clazz.getName() + " does not extend EZModule");
				return false;
			}
		} catch (Exception e) {
			Log.error("An exception occured while loading " + clazz.getName());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Try to disable all modules
	 */
	public static void disableAll() {
		Log.info("Disabling all modules");
		for (String module : modules.keySet()) {
			if (isEnabled(module)) {
				disable(module);
			}
		}
	}
}