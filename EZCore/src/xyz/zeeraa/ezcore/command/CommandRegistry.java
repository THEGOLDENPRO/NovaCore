package xyz.zeeraa.ezcore.command;

import xyz.zeeraa.ezcore.EZCore;

/**
 * This class is used to register {@link EZCommand}s
 * 
 * @author Zeeraa
 */
public class CommandRegistry {
	/**
	 * Register a {@link EZCommand}
	 * 
	 * @param command {@link EZCommand} to be registered
	 */
	public static void registerCommand(EZCommand command) {
		EZCommandProxy commandProxy = new EZCommandProxy(command);
		EZCore.getInstance().getCommandRegistrator().registerCommand(commandProxy);
	}
}