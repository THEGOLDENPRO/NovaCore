package xyz.zeeraa.ezcore.command.commands.ezcore.whereami;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.ezcore.command.AllowedSenders;
import xyz.zeeraa.ezcore.command.EZSubCommand;

public class EZCoreSubCommandWhereAmI extends EZSubCommand {
	public EZCoreSubCommandWhereAmI() {
		super("whereami");
		
		this.setAliases(EZSubCommand.generateAliasList("wai"));
		this.setPermission("ezcore.ezcore.whereami");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		
		this.setAllowedSenders(AllowedSenders.PLAYERS);
		
		this.setDescription("Show your location");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			p.sendMessage(ChatColor.GOLD +"-=-= Your location is =-=-");
			p.sendMessage(ChatColor.GOLD +"World: " + ChatColor.AQUA+ p.getLocation().getWorld().getName());
			p.sendMessage(ChatColor.GOLD +"X: " + ChatColor.AQUA+ p.getLocation().getX());
			p.sendMessage(ChatColor.GOLD +"Y: " + ChatColor.AQUA+ p.getLocation().getY());
			p.sendMessage(ChatColor.GOLD +"Z: " + ChatColor.AQUA+ p.getLocation().getZ());
			p.sendMessage(ChatColor.GOLD +"Yaw: " + ChatColor.AQUA+ p.getLocation().getYaw());
			p.sendMessage(ChatColor.GOLD +"Pitch: " + ChatColor.AQUA+ p.getLocation().getPitch());
		}
		
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}