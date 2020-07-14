package xyz.zeeraa.ezcore;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.zeeraa.ezcore.module.ModuleManager;
import xyz.zeeraa.ezcore.module.gui.GUIManager;

public class EZCore extends JavaPlugin implements Listener {
	private static EZCore instance;

	/**
	 * Get instance of the {@link EZCore} plugin
	 * 
	 * @return {@link EZCore} instance
	 */
	public static EZCore getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		// Register plugin channels
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		// Register events
		Bukkit.getPluginManager().registerEvents(this, this);

		// Load modules
		ModuleManager.loadModule(GUIManager.class);
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