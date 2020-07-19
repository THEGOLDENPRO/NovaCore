package xyz.zeeraa.ezcore.command;

/**
 * Represents the way a command checks for permissions
 * 
 * @author Zeeraa
 *
 */
public enum CommandPermissionMode {
	/**
	 * Use the permission from the command
	 */
	DEFAULT,
	/**
	 * Use the permission from the parent command. If the parent is null the command
	 * will require no permission
	 */
	INHERIT;
}