package net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.createblockreplacercache;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import net.zeeraa.novacore.spigot.command.AllowedSenders;
import net.zeeraa.novacore.spigot.command.NovaCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.GameMapDesignToolkit;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.PlayerAreaSelection;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.blockreplacercachetask.BlockReplacerCacheTask;
import net.zeeraa.novacore.spigot.utils.VectorArea;

public class CreateBlockReplacerCacheCommand extends NovaCommand {
	public CreateBlockReplacerCacheCommand(Plugin owner) {
		super("createblockreplacercache", owner);

		setAllowedSenders(AllowedSenders.PLAYERS);
		setPermission("gamemapdesigntoolkit.createblockreplacercache");
		setPermissionDefaultValue(PermissionDefault.OP);
		setUsage("/createblockreplacercache");
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player) sender;
		PlayerAreaSelection selection = GameMapDesignToolkit.getInstance().getPlayerSelection(player);

		int x1 = selection.getPosition1().getBlockX();
		int x2 = selection.getPosition2().getBlockX();

		int y1 = selection.getPosition1().getBlockY();
		int y2 = selection.getPosition2().getBlockY();

		int z1 = selection.getPosition1().getBlockZ();
		int z2 = selection.getPosition2().getBlockZ();

		VectorArea vectorArea = new VectorArea(x1, y1, z1, x2, y2, z2);
		new BlockReplacerCacheTask(vectorArea, player.getWorld());

		return true;
	}
}