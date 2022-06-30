package net.zeeraa.novacore.spigot.gamemapdesigntoolkit;

import org.bukkit.plugin.java.JavaPlugin;

import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.CopyItemBase64;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.CopyLocationCommand;
import net.zeeraa.novacore.spigot.gamemapdesigntoolkit.command.createblockreplacercache.CreateBlockReplacerCacheCommand;

public class GameMapDesignToolkit extends JavaPlugin {
	private static GameMapDesignToolkit instance;

	public static GameMapDesignToolkit getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		GameMapDesignToolkit.instance = this;
		
		getDataFolder().mkdir();

		CommandRegistry.registerCommand(new CopyLocationCommand(this));
		CommandRegistry.registerCommand(new CopyItemBase64(this));
		CommandRegistry.registerCommand(new CreateBlockReplacerCacheCommand(this));
	}
}