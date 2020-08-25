package net.zeeraa.novacore.command.commands.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.novacore.command.NovaCommand;
import net.zeeraa.novacore.command.commands.game.listplayers.NovaCoreSubCommandGameListplayers;
import net.zeeraa.novacore.command.commands.game.lootdrop.NovaCoreSubCommandGameLootdrop;
import net.zeeraa.novacore.command.commands.game.refill.NovaCoreSubCommandGameRefill;
import net.zeeraa.novacore.command.commands.game.resetcountdown.NovaCoreSubCommandResetCountdownGame;
import net.zeeraa.novacore.command.commands.game.start.NovaCoreSubCommandForceStartGame;
import net.zeeraa.novacore.command.commands.game.start.NovaCoreSubCommandStartGame;
import net.zeeraa.novacore.command.commands.game.stop.NovaCoreSubCommandStopGame;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreCommandGame extends NovaCommand {

	public NovaCoreCommandGame() {
		super("game");
		
		this.setDescription("Manage minigames");
		this.setPermission("novacore.command.game");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game command");
		
		this.addHelpSubCommand();
		this.addSubCommand(new NovaCoreSubCommandStartGame());
		this.addSubCommand(new NovaCoreSubCommandForceStartGame());
		this.addSubCommand(new NovaCoreSubCommandStopGame());
		this.addSubCommand(new NovaCoreSubCommandGameRefill());
		this.addSubCommand(new NovaCoreSubCommandGameLootdrop());
		this.addSubCommand(new NovaCoreSubCommandGameListplayers());
		this.addSubCommand(new NovaCoreSubCommandResetCountdownGame());
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