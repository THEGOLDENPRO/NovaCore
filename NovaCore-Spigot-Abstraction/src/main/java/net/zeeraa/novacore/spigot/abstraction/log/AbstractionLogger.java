package net.zeeraa.novacore.spigot.abstraction.log;

public class AbstractionLogger {
	private static IAbstractionLogger logger;

	public static IAbstractionLogger getLogger() {
		return logger;
	}

	public static void setLogger(IAbstractionLogger logger) {
		AbstractionLogger.logger = logger;
	}
}