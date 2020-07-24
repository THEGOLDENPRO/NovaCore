package xyz.zeeraa.ezcore.log;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EZLogger {
	private static LogLevel consoleLogLevel = LogLevel.INFO;
	
	public static HashMap<UUID, LogLevel> subscribedPlayers = new HashMap<UUID, LogLevel>();
	
	public static void trace(String message) {
		EZLogger.log(message, LogLevel.TRACE);
	}

	public static void debug(String message) {
		EZLogger.log(message, LogLevel.DEBUG);
	}

	public static void info(String message) {
		EZLogger.log(message, LogLevel.INFO);
	}

	public static void warn(String message) {
		EZLogger.log(message, LogLevel.WARN);
	}

	public static void error(String message) {
		EZLogger.log(message, LogLevel.ERROR);
	}

	public static void fatal(String message) {
		EZLogger.log(message, LogLevel.FATAL);
	}

	public static void log(String message, LogLevel logLevel) {
		if (logLevel.shouldLog(consoleLogLevel)) {
			Bukkit.getServer().getConsoleSender().sendMessage(logLevel.getMessage(message));
		}

		for (UUID uuid : subscribedPlayers.keySet()) {
			LogLevel userLogLevel = subscribedPlayers.get(uuid);
			if (logLevel.shouldLog(userLogLevel)) {
				Player player = Bukkit.getServer().getPlayer(uuid);
				if (player != null) {
					if (player.isOnline()) {
						player.sendMessage(logLevel.getMessage(message));
					}
				}
			}
		}
	}
	
	/**
	 * Get the log level the console is set to use. Default is {@link LogLevel#INFO}
	 * @return The {@link LogLevel} the console is using
	 */
	public static LogLevel getConsoleLogLevel() {
		return consoleLogLevel;
	}
	
	/**
	 * Set the log level for the console to use. Default is {@link LogLevel#INFO}
	 * @param consoleLogLevel The {@link LogLevel} to use
	 */
	public static void setConsoleLogLevel(LogLevel consoleLogLevel) {
		EZLogger.consoleLogLevel = consoleLogLevel;
	}
	
	public static HashMap<UUID, LogLevel> getSubscribedPlayers() {
		return subscribedPlayers;
	}
}