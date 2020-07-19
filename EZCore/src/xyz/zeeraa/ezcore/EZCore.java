package xyz.zeeraa.ezcore;

import java.io.InvalidClassException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.zeeraa.ezcore.abstraction.CommandRegistrator;
import xyz.zeeraa.ezcore.abstraction.NMSHandler;
import xyz.zeeraa.ezcore.command.CommandRegistry;
import xyz.zeeraa.ezcore.command.commands.EZCoreCommand;
import xyz.zeeraa.ezcore.module.ModuleManager;
import xyz.zeeraa.ezcore.module.compass.CompassTracker;
import xyz.zeeraa.ezcore.module.game.GameManager;
import xyz.zeeraa.ezcore.module.gui.GUIManager;
import xyz.zeeraa.ezcore.module.multiverse.MultiverseManager;
import xyz.zeeraa.ezcore.module.scoreboard.EZSimpleScoreboard;

public class EZCore extends JavaPlugin implements Listener {
	private static EZCore instance;

	private CommandRegistrator bukkitCommandRegistrator;

	/**
	 * Get instance of the {@link EZCore} plugin
	 * 
	 * @return {@link EZCore} instance
	 */
	public static EZCore getInstance() {
		return instance;
	}

	public CommandRegistrator getCommandRegistrator() {
		return bukkitCommandRegistrator;
	}

	@Override
	public void onEnable() {
		instance = this;

		String packageName = this.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);

		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Server version: " + version);

		try {
			Class<?> clazz = Class.forName("xyz.zeeraa.ezcore.version." + version + ".NMSHandler");
			if (NMSHandler.class.isAssignableFrom(clazz)) {
				NMSHandler nmsHandler = (NMSHandler) clazz.getConstructor().newInstance();

				bukkitCommandRegistrator = nmsHandler.getCommandRegistrator();
				if (bukkitCommandRegistrator == null) {
					getLogger().warning("CammandRegistrator is not supported for this version");
				}
			} else {
				throw new InvalidClassException("xyz.zeeraa.ezcore.version." + version + ".NMSHandler is not assignable from " + NMSHandler.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			getLogger().severe("Could not find support for this CraftBukkit version.");
			getLogger().info("Check for updates at URL HERE");
			setEnabled(false);
			return;
		}

		// Register plugin channels
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		// Register events
		Bukkit.getPluginManager().registerEvents(this, this);

		// Load modules
		ModuleManager.loadModule(GUIManager.class);
		ModuleManager.loadModule(MultiverseManager.class);
		ModuleManager.loadModule(EZSimpleScoreboard.class);
		ModuleManager.loadModule(CompassTracker.class);
		ModuleManager.loadModule(GameManager.class);
		
		CommandRegistry.registerCommand(new EZCoreCommand());
	}

	@Override
	public void onDisable() {
		// Cancel scheduler tasks
		Bukkit.getScheduler().cancelTasks(this);

		// Disable modules
		ModuleManager.disableAll();

		// Unregister listeners
		HandlerList.unregisterAll((Plugin) this);

		// Unregister plugin channels
		Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
	}
}