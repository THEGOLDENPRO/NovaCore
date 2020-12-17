package net.zeeraa.novacore.commons.log;

/**
 * This logger will call functions in {@link Log} using a predefined name
 * 
 * @author Zeeraa
 */
public class NovaLogger {
	private String name;

	public NovaLogger(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void trace(String message) {
		Log.trace(name, message);
	}

	public void debug(String message) {
		Log.debug(name, message);
	}

	public void info(String message) {
		Log.info(name, message);
	}

	public void warn(String message) {
		Log.warn(name, message);
	}

	public void error(String message) {
		Log.error(name, message);
	}

	public void fatal(String message) {
		Log.fatal(name, message);
	}
}