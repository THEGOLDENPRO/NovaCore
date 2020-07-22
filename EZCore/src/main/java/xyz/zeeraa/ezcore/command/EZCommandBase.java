package xyz.zeeraa.ezcore.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

/**
 * This class is the shared code between {@link EZCommand} and
 * {@link EZSubCommand}
 * 
 * @author Zeeraa
 */
public abstract class EZCommandBase {
	private EZCommandBase parentCommand;
	private List<EZSubCommand> subCommands;

	private AllowedSenders allowedSenders;

	private String permission;
	private String permissionDescription;
	private PermissionDefault permissionDefaultValue;
	private boolean requireOp;

	private String description;

	private String name;
	private List<String> aliases;

	public EZCommandBase(String name) {
		this.name = name;
		this.description = "";
		this.permission = null;
		this.permissionDescription = "";
		this.permissionDefaultValue = PermissionDefault.FALSE;
		this.requireOp = false;
		this.subCommands = new ArrayList<EZSubCommand>();
		this.allowedSenders = AllowedSenders.ALL;
		this.parentCommand = null;

		this.aliases = new ArrayList<String>();
	}

	/**
	 * Get all sub commands
	 * 
	 * @return {@link List} with all sub commands for this command
	 */
	public List<EZSubCommand> getSubCommands() {
		return subCommands;
	}

	/**
	 * Get aliases for this command
	 * 
	 * @return {@link List} with aliases
	 */
	public List<String> getAliases() {
		return aliases;
	}

	/**
	 * FSet the aliases for this command.
	 * {@link EZCommandBase#generateAliasList(String...)} can be used to create the
	 * list in one line
	 * 
	 * @param aliases {@link List} with aliases
	 */
	protected void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	/**
	 * Get command description
	 * 
	 * @return Description for this command
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description for this command
	 * 
	 * @param description command description
	 */
	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the permission that this command requires. if null and
	 * {@link EZCommandBase#isRequireOp()} is false the command can be used by
	 * anyone.
	 * 
	 * @return permission for this command
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * Set the permission for this command. if null and
	 * {@link EZCommandBase#isRequireOp()} is false the command can be used by
	 * anyone.
	 * 
	 * @param permission permission for this command
	 */
	protected void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * Check if this command has a permission set
	 * 
	 * @return <code>true</code> is permission is not null
	 */
	public boolean hasPermission() {
		return permission != null;
	}

	/**
	 * Get the permission description
	 * 
	 * @return permission description
	 */
	public String getPermissionDescription() {
		return permissionDescription;
	}

	/**
	 * Set the permission description
	 * 
	 * @param permissionDescription permission description
	 */
	protected void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}

	/**
	 * Get the default value for the permission
	 * 
	 * @return {@link PermissionDefault}
	 */
	public PermissionDefault getPermissionDefaultValue() {
		return permissionDefaultValue;
	}

	/**
	 * Set the default permission value for the permisison
	 * 
	 * @param permissionDefaultValue {@link PermissionDefault}
	 */
	protected void setPermissionDefaultValue(PermissionDefault permissionDefaultValue) {
		this.permissionDefaultValue = permissionDefaultValue;
	}

	/**
	 * Set if the command requires of. if a permission is set and this is
	 * <code>true</code> the player can have either op or the permission to be able
	 * to use this command
	 * 
	 * @param requireOp <code>true</code> if the command requires op
	 */
	protected void setRequireOp(boolean requireOp) {
		this.requireOp = requireOp;
	}

	/**
	 * Check if the command requires of. if a permission is set and this is
	 * <code>true</code> the player can have either op or the permission to be able
	 * to use this command
	 * 
	 * @return <code>true</code> if the command requires op
	 */
	public boolean isRequireOp() {
		return requireOp;
	}

	/**
	 * Add the help sub command to this command
	 */
	protected void addHelpSubCommand() {
		this.addHelpSubCommand(null);
	}

	/**
	 * Add the help sub command to this command
	 * 
	 * @param permission permission for the help sub command. Default is the same as
	 *                   the parent
	 */
	protected void addHelpSubCommand(String permission) {
		this.addHelpSubCommand(permission, PermissionDefault.FALSE);
	}

	/**
	 * Add the help sub command to this command
	 * 
	 * @param permission             permission for the help sub command. Default is
	 *                               the same as the parent
	 * @param permissionDefaultValue the {@link PermissionDefault} to use
	 */
	protected void addHelpSubCommand(String permission, PermissionDefault permissionDefaultValue) {
		this.addHelpSubCommand(permission, "Show help", permissionDefaultValue);
	}

	/**
	 * Add the help sub command to this command
	 * 
	 * @param permission            permission for the help sub command. Default is
	 *                              the same as the parent
	 * @param permissionDescription the permission description
	 */
	protected void addHelpSubCommand(String permission, String permissionDescription) {
		this.addHelpSubCommand(permission, permissionDescription, PermissionDefault.FALSE);
	}

	/**
	 * Add the help sub command to this command
	 * 
	 * @param permission             permission for the help sub command. Default is
	 *                               the same as the parent
	 * @param permissionDescription  the permission description
	 * @param permissionDefaultValue the {@link PermissionDefault} to use
	 */
	protected void addHelpSubCommand(String permission, String permissionDescription, PermissionDefault permissionDefaultValue) {
		HelpSubCommand helpSubCommand = new HelpSubCommand(permission, permissionDescription, permissionDefaultValue);

		helpSubCommand.setRequireOp(this.isRequireOp());

		if (permission == null) {
			helpSubCommand.setPermission(this.getPermission());

			helpSubCommand.setPermissionDefaultValue(this.getPermissionDefaultValue());
			helpSubCommand.setPermissionDescription(this.getPermissionDescription());
		}

		addSubCommand(helpSubCommand);
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

	/**
	 * Add a {@link EZSubCommand}
	 * 
	 * @param subCommand {@link EZSubCommand} to be added
	 */
	protected void addSubCommand(EZSubCommand subCommand) {
		subCommands.add(subCommand);
		subCommand.setParentCommand(this);
	}

	/**
	 * Check if this command has a parent command. This should always be
	 * <code>false</code> for {@link EZCommand} and should always be
	 * <code>true</code> for {@link EZSubCommand}
	 * 
	 * @return <code>true</code> if a parent command exists
	 */
	public boolean hasParentCommand() {
		return parentCommand != null;
	}

	/**
	 * Set the parent command. After the parent command has been set all other calls
	 * to this function will be ignored
	 * 
	 * @param parentCommand {@link EZCommandBase} instance of the parent command
	 */
	protected void setParentCommand(EZCommandBase parentCommand) {
		if (this.parentCommand != null) {
			return;
		}

		this.parentCommand = parentCommand;
	}

	/**
	 * Get the parent command for this command. This only works on
	 * {@link EZSubCommand}
	 * 
	 * @return the {@link EZCommandBase} for the parent command or null if the
	 *         command has no parents
	 */
	public EZCommandBase getParentCommand() {
		return parentCommand;
	}

	/**
	 * Get the name of this command
	 * 
	 * @return command name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create a {@link List} containing all aliases from strings
	 * 
	 * @param aliases The aliases for the command
	 * @return {@link List} with aliases
	 */
	public static List<String> generateAliasList(String... aliases) {
		List<String> result = new ArrayList<>();

		for (String alias : aliases) {
			result.add(alias);
		}

		return result;
	}

	/**
	 * Get the message that is shown to {@link CommandSender} that does not have
	 * permission
	 * 
	 * @return a {@link String} that will be sent to {@link CommandSender} without
	 *         permission
	 */
	public String getNoPermissionMessage() {
		return ChatColor.RED + "You dont have permission to use this command";
	}

	/**
	 * Check if a {@link CommandSender} has permission to use this command
	 * 
	 * @param sender the {@link CommandSender} to check
	 * @return <code>true</code> if allowed
	 */
	public boolean hasSenderPermission(CommandSender sender) {
		if (!hasPermission() && !isRequireOp()) {
			return true;
		}

		if (hasPermission()) {
			if (sender.hasPermission(this.getPermission())) {
				return true;
			}
		}

		if (this.isRequireOp()) {
			if (sender.isOp()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get the allowed senders for this command
	 * 
	 * @return {@link AllowedSenders}
	 */
	public AllowedSenders getAllowedSenders() {
		return allowedSenders;
	}

	/**
	 * Set the allowed senders for this command
	 * 
	 * @param allowedSenders {@link AllowedSenders}
	 */
	protected void setAllowedSenders(AllowedSenders allowedSenders) {
		this.allowedSenders = allowedSenders;
	}
}