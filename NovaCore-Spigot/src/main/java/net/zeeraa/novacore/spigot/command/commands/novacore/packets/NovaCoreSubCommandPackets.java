package net.zeeraa.novacore.spigot.command.commands.novacore.packets;

import net.zeeraa.novacore.spigot.abstraction.packet.MinecraftChannelDuplexHandler;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaSubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public class NovaCoreSubCommandPackets extends NovaSubCommand {

    public NovaCoreSubCommandPackets() {
        super("packet");

        this.setPermission("novacore.command.novacore.packet");
        this.setPermissionDefaultValue(PermissionDefault.OP);

        this.addHelpSubCommand();
        this.addSubCommand(new NovaCoreSubCommandPacketsDebug());

        this.setDescription("allow or dissalow packet debugging");

        this.setFilterAutocomplete(true);
        this.setEmptyTabMode(true);
        this.setAllowedSenders(AllowedSenders.PLAYERS);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;

        String imLazy = MinecraftChannelDuplexHandler.isDebug() ? ChatColor.RED + "" + ChatColor.BOLD + "Disabled." : ChatColor.GREEN + "" + ChatColor.BOLD + "Enabled.";
        player.sendMessage(ChatColor.GREEN + "Packet debugging is " + imLazy);

        return true;
    }
}
