package net.zeeraa.novautils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novautils.NovaUtils;

public class InvseeCommand extends NovaCommand {

	public InvseeCommand() {
		super("invsee", NovaUtils.getInstance());
		
		setAllowedSenders(AllowedSenders.PLAYERS);
		
		setPermission("novautils.command.invsee");
		setPermissionDefaultValue(PermissionDefault.OP);
		setPermissionDescription("Allow players to access other players inventory");
		
		setDescription("Open another players inventory");
		setUsage("/invsee <Player>");
		
		addHelpSubCommand();
		
		setFilterAutocomplete(true);
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(args.length >0) {
			Player player = Bukkit.getServer().getPlayer(args[0]);
			
			if(player != null) {
				if(player.isOnline()) {
					((Player) sender).openInventory(player.getInventory());
					sender.sendMessage(ChatColor.GREEN + "Showing " + player.getDisplayName() + "s inventory");
					return true;
				}
			}
			
			sender.sendMessage(ChatColor.RED + "Could not find a player named " + args[0]);
		} else {
			sender.sendMessage(ChatColor.RED + "Please provide a player");
		}
		
		return true;
	}
}