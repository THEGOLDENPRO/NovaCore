package xyz.zeeraa.ezcore.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

public abstract class EZCommandBase implements CommandExecutor, TabCompleter{
	private EZCommandBase parentCommand;
	private List<EZSubCommand> subCommands;

	private String name;
	private List<String> aliases;

	public EZCommandBase(String name) {
		this(name, null);
	}

	public EZCommandBase(String name, EZCommandBase parentCommand) {
		this.name = name;
		this.subCommands = new ArrayList<EZSubCommand>();
		this.parentCommand = parentCommand;

		this.aliases = new ArrayList<String>();
	}

	public List<EZSubCommand> getSubCommands() {
		return subCommands;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	/**
	 * Executes the command, returning its success
	 *
	 * @param sender       Source object which is executing this command
	 * @param commandLabel The alias of the command used
	 * @param args         All arguments passed to the command, split via ' '
	 * @return true if the command was successful, otherwise false
	 */
	public abstract boolean execute(CommandSender sender, String commandLabel, String[] args);

	/**
	 * Executed on tab completion for this command, returning a list of options the
	 * player can tab through.
	 *
	 * @param sender Source object which is executing this command
	 * @param alias  the alias being used
	 * @param args   All arguments passed to the command, split via ' '
	 * @return a list of tab-completions for the specified arguments. This will
	 *         never be null. List may be immutable.
	 * @throws IllegalArgumentException if sender, alias, or args is null
	 */
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		Validate.notNull(sender, "Sender cannot be null");
		Validate.notNull(args, "Arguments cannot be null");
		Validate.notNull(alias, "Alias cannot be null");

		if (args.length == 0) {
			return ImmutableList.of();
		}

		String lastWord = args[args.length - 1];

		Player senderPlayer = sender instanceof Player ? (Player) sender : null;

		ArrayList<String> matchedPlayers = new ArrayList<String>();
		for (Player player : sender.getServer().getOnlinePlayers()) {
			String name = player.getName();
			if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord)) {
				matchedPlayers.add(name);
			}
		}

		Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
		return matchedPlayers;
	}

	public void addSubCommand(EZSubCommand subCommand) {
		subCommands.add(subCommand);
	}

	public boolean hasParentCommand() {
		return parentCommand != null;
	}

	public EZCommandBase getParentCommand() {
		return parentCommand;
	}

	public String getName() {
		return name;
	}
}