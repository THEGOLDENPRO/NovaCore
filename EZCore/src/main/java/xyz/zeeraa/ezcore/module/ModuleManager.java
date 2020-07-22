package xyz.zeeraa.ezcore.module;

import java.util.HashMap;

/**
 * This class is used to register, enable and disable {@link EZModule}s
 * 
 * @author Zeeraa
 */
public class ModuleManager {
	private static HashMap<String, EZModule> modules = new HashMap<>();

	/**
	 * Get a {@link HashMap} containing all modules with the module class name as
	 * key and the module as value
	 * 
	 * @return {@link HashMap} containing all modules
	 */
	public static HashMap<String, EZModule> getModules() {
		return modules;
	}

	/**
	 * Get a loaded module
	 * 
	 * @param clazz The class of the module
	 * @return {@link EZModule} or null if not loaded
	 */
	public static EZModule getModule(Class<? extends EZModule> clazz) {
		return modules.get(clazz.getName());
	}

	/**
	 * Get a loaded module
	 * 
	 * @param className The class name of the module
	 * @return {@link EZModule} or null if not loaded
	 */
	public static EZModule getModule(String className) {
		return modules.get(className);
	}

	/**
	 * Check if a module is enabled
	 * 
	 * @param clazz The class of the module
	 * @return <code>true</code> if the module is disabled
	 */
	public static boolean isEnabled(Class<? extends EZModule> clazz) {
		return isEnabled(clazz.getName());
	}

	/**
	 * Check if a module is enabled
	 * 
	 * @param className The class name of the module
	 * @return <code>true</code> if the module is enabled
	 */
	public static boolean isEnabled(String className) {
		EZModule module = getModule(className);
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
	public static boolean isDisabled(Class<? extends EZModule> clazz) {
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
	 * @return <code>false</code> if {@link EZModule#onEnable()} generated a
	 *         exception or if the module failed to enable
	 */
	public static boolean enable(Class<? extends EZModule> clazz) {
		return enable(clazz.getName());
	}

	/**
	 * Enable a module
	 * 
	 * @param className The class name of the module
	 * @return <code>false</code> if {@link EZModule#onEnable()} generated a
	 *         exception or if the module failed to enable
	 */
	public static boolean enable(String className) {
		EZModule module = getModule(className);
		if (module != null) {
			return module.enable();
		}
		return false;
	}

	/**
	 * Disable a module
	 * 
	 * @param clazz The class of the module
	 * @return <code>false</code> if {@link EZModule#onDisable()} generated a
	 *         exception or if the module failed to disable
	 */
	public static boolean disable(Class<? extends EZModule> clazz) {
		return disable(clazz.getName());
	}

	/**
	 * Disable a module
	 * 
	 * @param className The class name of the module
	 * @return <code>false</code> if {@link EZModule#onDisable()} generated a
	 *         exception or if the module failed to disable
	 */
	public static boolean disable(String className) {
		EZModule module = getModule(className);
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
	public static ModuleEnableFailureReason getEnableFailureReason(Class<? extends EZModule> clazz) {
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
		EZModule module = getModule(className);
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
	public static boolean moduleExists(Class<? extends EZModule> clazz) {
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
	public static boolean loadModule(Class<? extends EZModule> clazz) {
		if (moduleExists(clazz)) {
			// TODO: Log error
			return false;
		}
		// TODO: Log load
		try {
			Object module = clazz.getConstructor().newInstance(new Object[] {});

			if (module instanceof EZModule) {
				if(((EZModule) module).getName().contains(" ")) {
					throw new InvalidModuleNameException("The module name can't contain spaces. Module class: " + module.getClass().getName());
				}
				
				((EZModule) module).onLoad();
				modules.put(((EZModule) module).getClassName(), (EZModule) module);
				return true;
			} else {
				// TODO: Log error
				return false;
			}
		} catch (Exception e) {
			// TODO: Log error
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Try to disable all modules
	 */
	public static void disableAll() {
		for (String module : modules.keySet()) {
			if (isEnabled(module)) {
				disable(module);
			}
		}
	}
}