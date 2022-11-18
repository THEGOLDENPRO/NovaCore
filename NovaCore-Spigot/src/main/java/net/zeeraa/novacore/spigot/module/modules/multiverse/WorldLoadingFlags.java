package net.zeeraa.novacore.spigot.module.modules.multiverse;

public enum WorldLoadingFlags {
	PREVENT_AG_RELOAD;

	public static boolean hasFlag(WorldLoadingFlags[] flags, WorldLoadingFlags flag) {
		for (WorldLoadingFlags worldLoadingFlags : flags) {
			if (worldLoadingFlags == flag) {
				return true;
			}
		}
		return false;
	}
}