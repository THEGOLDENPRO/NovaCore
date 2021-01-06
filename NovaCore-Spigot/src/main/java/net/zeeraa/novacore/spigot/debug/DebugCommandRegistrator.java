package net.zeeraa.novacore.spigot.debug;

import net.zeeraa.novacore.spigot.command.CommandRegistry;

public class DebugCommandRegistrator {
	private static DebugCommandRegistrator instance;

	public static DebugCommandRegistrator getInstance() {
		return instance;
	}

	private NovaDebugCommand debugCommand;

	public DebugCommandRegistrator() {
		instance = this;

		this.debugCommand = new NovaDebugCommand();

		CommandRegistry.registerCommand(debugCommand);
	}

	public void addDebugTrigger(DebugTrigger trigger) {
		debugCommand.addExternalSubCommand(new NovaDebugTriggerSubCommand(trigger));
	}
}