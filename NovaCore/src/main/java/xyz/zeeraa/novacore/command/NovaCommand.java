package xyz.zeeraa.novacore.command;

/**
 * Represents a command managed by NovaCore
 * @author Zeeraa
 */
public abstract class NovaCommand extends NovaCommandBase {
	public NovaCommand(String name) {
		super(name);
	}
}