package net.zeeraa.novacore.spigot.gameengine.command.commands.gamelobby;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

import net.zeeraa.novacore.spigot.command.NovaCommand;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreCommandGameLobby extends NovaCommand {
	public NovaCoreCommandGameLobby(Plugin plugin) {
		super("gamelobby", plugin);

		this.setDescription("Game lobby commands");
		this.setPermission("novacore.command.gamelobby");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game lobby command");

		this.addHelpSubCommand();
		this.addSubCommand(new SCLeaveQueue());

		this.setEmptyTabMode(true);
		this.setFilterAutocomplete(true);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/gamelobby help" + ChatColor.GOLD + " to see all commands");
		return true;
	}
}