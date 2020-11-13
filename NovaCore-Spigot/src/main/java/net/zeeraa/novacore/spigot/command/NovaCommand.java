package net.zeeraa.novacore.spigot.command;

import org.bukkit.plugin.Plugin;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;

/**
 * Represents a command managed by NovaCore
 * <p>
 * See {@link NovaCommandBase} for function documentation
 * 
 * @author Zeeraa
 */
public abstract class NovaCommand extends NovaCommandBase {
	private Plugin owner;

	/**
	 * @param name The name of the command. If the name is hello the command will be
	 *             /hello
	 *             <p>
	 *             Deprecated: Provide a owning plugin using
	 *             {@link NovaCommand#NovaCommand(String, Plugin)} instead
	 * 
	 */
	@Deprecated
	public NovaCommand(String name) {
		this(name, NovaCore.getInstance());

		Log.warn("NovaCommand", "The command " + this.getClass().getName() + " is using the legacy constructor NovaCommand#NovaCommand(String)");
	}

	/**
	 * @param name The name of the command. If the name is hello the command will be
	 *             /hello
	 */
	public NovaCommand(String name, Plugin owner) {
		super(name, NodeType.BASE_COMMAND);
		this.owner = owner;

	}

	/**
	 * Get the plugin that owns this command
	 * <p>
	 * If the deprecated method {@link NovaCommand#NovaCommand(String)} is used the
	 * owher will be set to {@link NovaCore}
	 * 
	 * @return The {@link Plugin} that own this command
	 */
	@Override
	public Plugin getOwner() {
		return owner;
	}
}