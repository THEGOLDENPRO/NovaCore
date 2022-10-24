package net.zeeraa.novacore.commons.utils;

import java.io.File;

public class PathUtil {
	public static File getParentSafe(File file) {
		return new File(file.getParentFile().getAbsolutePath());
	}
}