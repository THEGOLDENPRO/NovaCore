package net.zeeraa.novacore.spigot.abstraction.log;

public interface IAbstractionLogger {
	void trace(String message);

	void trace(String source, String message);

	void debug(String message);

	void debug(String source, String message);

	void info(String message);

	void info(String source, String message);

	void success(String message);

	void success(String source, String message);

	void warning(String message);

	void warning(String source, String message);

	void error(String message);

	void error(String source, String message);

	void fatal(String message);

	void fatal(String source, String message);
}