package net.zeeraa.novacore.commons.utils;

import java.util.UUID;

/**
 * Utilities for UUIDs
 * 
 * @author Zeeraa
 */
public class UUIDUtils {
	/**
	 * Compare 2 {@link UUID}s by using {@link String#equalsIgnoreCase(String)}
	 * 
	 * @param uuid1 UUID 1
	 * @param uuid2 UUID 2
	 * @return <code>true</code> if the UUIDs are matching
	 */
	public static boolean isSame(UUID uuid1, UUID uuid2) {
		return uuid1.toString().equalsIgnoreCase(uuid2.toString());
	}
}