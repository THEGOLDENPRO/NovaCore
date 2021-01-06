package net.zeeraa.novacore.spigot.debug;

import org.bukkit.command.CommandSender;

import net.zeeraa.novacore.spigot.command.NovaSubCommand;

public class NovaDebugTriggerSubCommand extends NovaSubCommand {
	private DebugTrigger trigger;

	public NovaDebugTriggerSubCommand(DebugTrigger trigger) {
		super(trigger.getName());

		this.trigger = trigger;

		setPermission(trigger.getPermission());
		setPermissionDefaultValue(trigger.getPermissionDefault());
		setAllowedSenders(trigger.getAllowedSenders());

		setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		trigger.onExecute(sender, commandLabel, args);
		return true;
	}
}