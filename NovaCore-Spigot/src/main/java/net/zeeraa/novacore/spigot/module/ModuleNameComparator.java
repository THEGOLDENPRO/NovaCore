package net.zeeraa.novacore.spigot.module;

import java.util.Comparator;

/**
 * Used to sort module names in alphabetical order
 * <p>
 * For now this is kind of broken but i might fix it later
 * 
 * @since 2.0.0
 * @author Zeeraa
 */
public class ModuleNameComparator implements Comparator<String> {
	@Override
	public int compare(String string1, String string2) {
		return string1.compareTo(string2);
	}
}