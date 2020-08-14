package xyz.zeeraa.novacore.command.commands.game.refill;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import xyz.zeeraa.novacore.command.NovaSubCommand;
import xyz.zeeraa.novacore.module.modules.chestloot.ChestLootManager;

/**
 * A command from NovaCore
 * 
 * @author Zeeraa
 */
public class NovaCoreSubCommandGameRefill extends NovaSubCommand {

	public NovaCoreSubCommandGameRefill() {
		super("refill");

		this.setDescription("Force a chest refill");
		this.setPermission("novacore.command.game.refill");
		this.setPermissionDefaultValue(PermissionDefault.OP);
		this.setPermissionDescription("Access to the game chest refill command");

		this.addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (ChestLootManager.getInstance().isEnabled()) {
			ChestLootManager.getInstance().refillChests();
			sender.sendMessage(ChatColor.GREEN + "OK");
		} else {
			sender.sendMessage(ChatColor.RED + "ChestLootManager is not enabled");
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return new ArrayList<String>();
	}
}