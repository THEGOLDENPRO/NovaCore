package xyz.zeeraa.ezcore.log;

import org.bukkit.ChatColor;

/**
 * This is used to set the logging level for the server
 * 
 * Some of the descriptions is taken from here:
 * https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
 * 
 * @author Zeeraa
 */
public enum LogLevel {
	/**
	 * This log level is used to mute all log levels. This level should never be
	 * logged to
	 */
	NONE(0),
	/**
	 * Any error that is forcing a shutdown of the service or application to prevent
	 * data loss
	 */
	FATAL(1),
	/**
	 * Any error which is fatal to the operation, but not the service or application
	 */
	ERROR(2),
	/**
	 * Anything that can potentially cause application oddities
	 */
	WARN(3),
	/**
	 * Generally useful information to log
	 */
	INFO(4),
	/**
	 * Information that is diagnostically helpful
	 */
	DEBUG(5),
	/**
	 * This should only be used when trying to find out why a function is not
	 * working
	 */
	TRACE(6);

	private int severityLevel;

	private LogLevel(int severityLevel) {
		this.severityLevel = severityLevel;
	}

	public int getSeverityLevel() {
		return severityLevel;
	}

	public boolean shouldLog(LogLevel activeLogLevel) {
		return this.getSeverityLevel() <= activeLogLevel.getSeverityLevel();
	}

	public ChatColor getColor() {
		return LogLevel.getColor(this);
	}

	public String getMessage(String message) {
		return LogLevel.getMessage(this, message);
	}

	public static ChatColor getColor(LogLevel severity) {
		switch (severity) {
		case FATAL:
			return ChatColor.DARK_RED;

		case ERROR:
			return ChatColor.RED;

		case WARN:
			return ChatColor.YELLOW;

		case INFO:
			return ChatColor.BLUE;

		case DEBUG:
			return ChatColor.DARK_BLUE;

		case TRACE:
			return ChatColor.GRAY;
		default:
			break;
		}
		return ChatColor.WHITE;
	}

	public static String getMessage(LogLevel severity, String message) {
		String result = ChatColor.BOLD + "[" + getColor(severity) + ChatColor.BOLD;

		switch (severity) {
		case FATAL:
			result += "FATAL";
			break;

		case ERROR:
			result += "ERROR";
			break;

		case WARN:
			result += "WARNING";
			break;

		case INFO:
			result += "INFO";
			break;

		case DEBUG:
			result += "DEBUG";
			break;

		case TRACE:
			result += "TRACE";
			break;
		default:
			result += "UNKNOWN_LOG_LEVEL";
			break;
		}
		result += ChatColor.RESET + "" + ChatColor.BOLD + "]: " + ChatColor.RESET + message;

		return result;
	}
}