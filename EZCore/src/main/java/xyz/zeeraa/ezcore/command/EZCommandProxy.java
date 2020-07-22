package xyz.zeeraa.ezcore.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * This class is used to turn {@link EZCommand} into a normal bukkit
 * {@link Command}
 * 
 * @author Zeeraa
 */
public class EZCommandProxy extends Command {
	private EZCommand ezCommand;

	protected EZCommandProxy(EZCommand ezCommand) {
		super(ezCommand.getName());

		this.ezCommand = ezCommand;

		this.setAliases(ezCommand.getAliases());
	}

	/**
	 * Get the {@link EZCommand} being proxied by this class
	 * 
	 * @return {@link EZCommand} instance
	 */
	public EZCommand getEzCommand() {
		return ezCommand;
	}

	/**
	 * Get the name of the {@link EZCommand} being proxied by this class
	 * 
	 * @return name of the {@link EZCommand}
	 */
	public String getRealName() {
		return ezCommand.getName();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		return recursiceCommandExecutionCheck(ezCommand, sender, commandLabel, args);
	}

	private boolean recursiceCommandExecutionCheck(EZCommandBase command, CommandSender sender, String commandLabel, String[] args) {
		if (args.length > 0) {
			// Scan sub commands
			for (EZSubCommand subCommand : command.getSubCommands()) {
				boolean isMatching = false;
				if (subCommand.getName().equalsIgnoreCase(args[0])) {
					isMatching = true;
				} else {
					for (String alias : subCommand.getAliases()) {
						if (alias.equalsIgnoreCase(args[0])) {
							isMatching = true;
							break;
						}
					}
				}

				if (isMatching) {
					// remove first argument to prevent the sub command name to be the first entry
					// in arguments
					String[] newArgs = new String[args.length - 1];
					for (int i = 1; i < args.length; i++) {
						newArgs[i - 1] = args[i];
					}

					return recursiceCommandExecutionCheck(subCommand, sender, commandLabel, newArgs);
				}
			}
			// No sub command exists. Execute the current command
		}

		if (!(sender instanceof ConsoleCommandSender)) {
			if (!command.hasSenderPermission(sender)) {
				sender.sendMessage(command.getNoPermissionMessage());
				return false;
			}
		}

		if (!command.getAllowedSenders().isAllowed(sender)) {
			sender.sendMessage(command.getAllowedSenders().getErrorMessage());
			return false;
		}

		return command.execute(sender, commandLabel, args);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return recursiceCommandTabCheck(ezCommand, sender, alias, args);
	}

	private List<String> recursiceCommandTabCheck(EZCommandBase command, CommandSender sender, String alias, String[] args) {
		if (args.length == 1) {
			if (!command.hasSenderPermission(sender)) {
				return new ArrayList<String>();
			}

			List<String> result = new ArrayList<>();
			result.addAll(command.tabComplete(sender, alias, args));
			for (EZSubCommand subCommand : command.getSubCommands()) {
				result.add(subCommand.getName());
				result.addAll(subCommand.getAliases());
			}
			return result;
		} else {
			if (args.length >= 2) {
				for (EZSubCommand subCommand : command.getSubCommands()) {
					boolean isMatching = false;
					String newAlias = "";

					if (subCommand.getName().equalsIgnoreCase(args[0])) {
						isMatching = true;
						newAlias = subCommand.getName();
					} else {
						for (String subCommandAlias : subCommand.getAliases()) {
							if (subCommandAlias.equalsIgnoreCase(args[0])) {
								isMatching = true;
								newAlias = subCommandAlias;
								break;
							}
						}
					}

					if (isMatching) {
						// remove first argument to prevent the sub command name to be the first entry
						// in arguments
						String[] newArgs = new String[args.length - 1];
						for (int i = 1; i < args.length; i++) {
							newArgs[i - 1] = args[i];
						}
						
						return recursiceCommandTabCheck(subCommand, sender, newAlias, newArgs);
					}
				}
			}

			if (!command.hasSenderPermission(sender)) {
				return new ArrayList<String>();
			}

			// no matching sub commands found
			return command.tabComplete(sender, alias, args);
		}
	}
}