package net.zeeraa.novacore.spigot.module.modules.multiverse;

public enum WorldLoadingFlags {
	PREVENT_AG_RELOAD;

	public static boolean hasFlag(WorldLoadingFlags[] flags, WorldLoadingFlags flag) {
		for (int i = 0; i < flags.length; i++) {
			if (flags[i] == flag) {
				return true;
			}
		}
		return false;
	}
}