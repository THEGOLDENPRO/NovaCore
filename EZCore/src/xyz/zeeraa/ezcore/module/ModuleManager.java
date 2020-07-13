package xyz.zeeraa.ezcore.module;

import java.util.HashMap;

public class ModuleManager {
	private static HashMap<String, EZModule> modules = new HashMap<>();

	public static HashMap<String, EZModule> getModules() {
		return modules;
	}

	public static EZModule getModule(Class<? extends EZModule> clazz) {
		return modules.get(clazz.getName());
	}

	public static EZModule getModule(String className) {
		return modules.get(className);
	}

	public static boolean isEnabled(Class<? extends EZModule> clazz) {
		return isEnabled(clazz.getName());
	}

	public static boolean isEnabled(String className) {
		EZModule module = getModule(className);
		if (module != null) {
			return module.isEnabled();
		}
		return false;
	}
	
	public static boolean enable(Class<? extends EZModule> clazz) {
		return enable(clazz.getName());
	}

	public static boolean enable(String className) {
		EZModule module = getModule(className);
		if (module != null) {
			return module.enable();
		}
		return false;
	}
	
	public static boolean disable(Class<? extends EZModule> clazz) {
		return disable(clazz.getName());
	}

	public static boolean disable(String className) {
		EZModule module = getModule(className);
		if (module != null) {
			return module.disable();
		}
		return false;
	}
	
	public static ModuleEnableFailureReason getEnableFailureReason(Class<? extends EZModule> clazz) {
		return getEnableFailureReason(clazz.getName());
	}

	public static ModuleEnableFailureReason getEnableFailureReason(String className) {
		EZModule module = getModule(className);
		if (module != null) {
			return module.getEnableFailureReason();
		}
		return null;
	}
	
	
	public static boolean moduleExists(Class<? extends EZModule> clazz) {
		return moduleExists(clazz.getName());
	}

	public static boolean moduleExists(String className) {
		return modules.containsKey(className);
	}
}