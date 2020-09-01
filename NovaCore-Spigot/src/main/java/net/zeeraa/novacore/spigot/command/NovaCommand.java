package net.zeeraa.novacore.spigot.command;

/**
 * Represents a command managed by NovaCore
 * <p>
 * See {@link NovaCommandBase} for function documentation
 * 
 * @author Zeeraa
 */
public abstract class NovaCommand extends NovaCommandBase {
	/**
	 * @param name The name of the command. If the name is hello the command will be
	 *             /hello
	 */
	public NovaCommand(String name) {
		super(name);
	}
}