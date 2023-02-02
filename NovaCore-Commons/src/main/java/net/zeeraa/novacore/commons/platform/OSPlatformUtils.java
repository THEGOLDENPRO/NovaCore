package net.zeeraa.novacore.commons.platform;

public class OSPlatformUtils {
	public static OSPlatform getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			return OSPlatform.WINDOWS;
		} else if (osName.contains("linux")) {
			return OSPlatform.LINUX;
		} else if (osName.contains("unix")) {
			return OSPlatform.UNIX;
		} else {
			return OSPlatform.UNKNOWN;
		}
	}
}