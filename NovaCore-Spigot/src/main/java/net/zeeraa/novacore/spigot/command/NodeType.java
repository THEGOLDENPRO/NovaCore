package net.zeeraa.novacore.spigot.command;

/**
 * Represents what type a {@link NovaCommandBase} is
 * 
 * @author Zeeraa
 *
 */
public enum NodeType {
	/**
	 * The command is of type: {@link NovaCommand}
	 */
	BASE_COMMAND,
	/**
	 * The command is of type: {@link NovaSubCommand}
	 */
	SUB_COMMAND;
}