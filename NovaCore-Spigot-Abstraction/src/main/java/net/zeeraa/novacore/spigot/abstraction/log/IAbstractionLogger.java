package net.zeeraa.novacore.spigot.abstraction.log;

public interface IAbstractionLogger {
	public void trace(String message);

	public void trace(String source, String message);

	public void debug(String message);

	public void debug(String source, String message);

	public void info(String message);

	public void info(String source, String message);

	public void success(String message);

	public void success(String source, String message);

	public void warning(String message);

	public void warning(String source, String message);

	public void error(String message);

	public void error(String source, String message);

	public void fatal(String message);

	public void fatal(String source, String message);
}