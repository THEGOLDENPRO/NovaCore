package net.zeeraa.novacore.spigot.command;

/**
 * Represents a sub command that can be added to a {@link NovaCommand}
 * @author Zeeraa
 */
public abstract class NovaSubCommand extends NovaCommandBase {
	public NovaSubCommand(String name) {
		super(name);
	}
}