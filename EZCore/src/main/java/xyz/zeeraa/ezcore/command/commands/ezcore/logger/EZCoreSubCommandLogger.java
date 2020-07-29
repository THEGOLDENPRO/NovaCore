package xyz.zeeraa.ezcore.command.commands.ezcore.logger;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.md_5.bungee.api.ChatColor;
import xyz.zeeraa.ezcore.command.EZSubCommand;
import xyz.zeeraa.ezcore.log.EZLogger;

public class EZCoreSubCommandLogger extends EZSubCommand {
	public EZCoreSubCommandLogger() {
		super("logger");
		
		this.setAliases(EZSubCommand.generateAliasList("log"));
		this.setPermission("ezcore.ezcore.logger");
		this.setPermissionDefaultValue(PermissionDefault.OP);

		this.addHelpSubCommand();
		this.addSubCommand(new EZCoreSubCommandLoggerSet());
		this.addSubCommand(new EZCoreSubCommandLoggerUnsubscribe());
		
		this.setDescription("Set log level for player and console");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(EZLogger.getSubscribedPlayers().containsKey(p.getUniqueId())) {
				p.sendMessage(ChatColor.GOLD + "Your log level is: " + EZLogger.getSubscribedPlayers().get(p.getUniqueId()).name());
			} else {
				p.sendMessage(ChatColor.GOLD + "You dont have any log level set");
			}
		}
		
		sender.sendMessage(ChatColor.GOLD + "Use "+ChatColor.AQUA+"/ezcore logger help" + ChatColor.GOLD + " for help");
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}