package xyz.zeeraa.ezcore.command.commands.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.command.EZCommand;
import xyz.zeeraa.ezcore.command.commands.game.start.EZCoreSubCommandForceStartGame;
import xyz.zeeraa.ezcore.command.commands.game.start.EZCoreSubCommandStartGame;

public class EZCoreCommandGame extends EZCommand {

	public EZCoreCommandGame() {
		super("game");
		
		this.setDescription("Manage minigames");
		this.setPermission("ezcore.game");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game command");
		
		this.addHelpSubCommand();
		this.addSubCommand(new EZCoreSubCommandStartGame());
		this.addSubCommand(new EZCoreSubCommandForceStartGame());
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "Use " + ChatColor.AQUA + "/game help" + ChatColor.GOLD + " to see all commands");
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}