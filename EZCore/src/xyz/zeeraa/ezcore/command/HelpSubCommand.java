package xyz.zeeraa.ezcore.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public class HelpSubCommand extends EZSubCommand {

	public HelpSubCommand() {
		super("help");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		// TODO:
		return false;
	}

	private String getParentCommandText(EZCommandBase command) {
		if (command.hasParentCommand()) {
			return getParentCommandText(command.getParentCommand()) + " " + command.getName();
		}

		return "/" + command.getName();
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<>();
	}
}