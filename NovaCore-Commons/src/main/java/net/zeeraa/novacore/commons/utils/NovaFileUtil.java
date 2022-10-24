package net.zeeraa.novacore.commons.utils;

import java.io.File;

public class NovaFileUtil {
	public static File getParentSafe(File file) {
		return new File(file.getParentFile().getAbsolutePath());
	}
}