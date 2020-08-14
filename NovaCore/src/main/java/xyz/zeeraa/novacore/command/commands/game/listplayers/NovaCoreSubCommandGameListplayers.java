package xyz.zeeraa.novacore.command.commands.game.listplayers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.NovaCore;
import xyz.zeeraa.novacore.command.NovaSubCommand;
import xyz.zeeraa.novacore.module.modules.game.GameManager;
import xyz.zeeraa.novacore.teams.Team;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandGameListplayers extends NovaSubCommand {

	public NovaCoreSubCommandGameListplayers() {
		super("listplayers");

		this.setDescription("List all in game players");
		this.setPermission("novacore.command.game.listplayers");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game listplayers command");

		this.addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().hasGame()) {
			List<UUID> players = GameManager.getInstance().getActiveGame().getPlayers();
			sender.sendMessage(ChatColor.GOLD + "There are " + players.size() + " players in the game");
			String playerList = "";
			for (UUID uuid : players) {
				Player player = Bukkit.getServer().getPlayer(uuid);

				if (player != null) {
					if (NovaCore.getInstance().hasTeamManager()) {
						Team team = NovaCore.getInstance().getTeamManager().getPlayerTeam(player);
						if (team != null) {
							playerList += team.getTeamColor();
						} else {
							playerList += ChatColor.YELLOW;
						}
					} else {
						playerList += ChatColor.GREEN;
					}

					playerList += player.getName() + " ";
				} else {
					playerList += ChatColor.YELLOW + uuid.toString() + " ";
				}
			}
			
			sender.sendMessage(ChatColor.GOLD + "Player list: " + playerList);
		} else {
			sender.sendMessage(ChatColor.RED + "No game has been loaded");
		}

		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}