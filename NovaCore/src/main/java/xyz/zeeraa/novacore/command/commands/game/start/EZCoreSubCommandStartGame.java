package xyz.zeeraa.novacore.command.commands.game.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.command.NovaSubCommand;
import xyz.zeeraa.novacore.module.modules.game.GameManager;
import xyz.zeeraa.novacore.module.modules.gamelobby.GameLobby;

public class EZCoreSubCommandStartGame extends NovaSubCommand {

	public EZCoreSubCommandStartGame() {
		super("start");

		this.setDescription("Start the game countdown");
		this.setPermission("ezcore.game.start");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game start command");

		this.addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (GameManager.getInstance().isEnabled()) {
			if (GameManager.getInstance().hasGame()) {
				if (!GameManager.getInstance().getActiveGame().hasStarted()) {
					if (GameManager.getInstance().hasCountdown()) {
						if(!GameManager.getInstance().getCountdown().hasCountdownStarted()) {
							GameManager.getInstance().getCountdown().startCountdown();
							sender.sendMessage(ChatColor.GREEN + "Countdown started");
						} else {
							sender.sendMessage(ChatColor.RED + "Countdown has already been started");
						}
					} else {
						if (GameLobby.getInstance().isEnabled()) {
							GameLobby.getInstance().startGame();
							sender.sendMessage(ChatColor.GREEN + "Game started");
						} else {
							try {
								GameManager.getInstance().start();
								sender.sendMessage(ChatColor.GREEN + "Game started");
							} catch (IOException e) {
								e.printStackTrace();
								sender.sendMessage(ChatColor.DARK_RED + "An exception occured while trying to start the game");
							}
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Game has already been started");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "No game has been loaded");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "GameManager is not enabled");
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}