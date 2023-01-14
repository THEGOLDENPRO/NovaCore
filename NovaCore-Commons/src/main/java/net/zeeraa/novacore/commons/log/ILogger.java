package net.zeeraa.novacore.commons.log;

public interface ILogger extends IBasicLogger {
	void trace(String name, String message);

	void debug(String name, String message);

	void info(String name, String message);

	void warn(String name, String message);

	void error(String name, String message);

	void fatal(String name, String message);
}