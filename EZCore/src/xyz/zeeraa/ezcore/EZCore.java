package xyz.zeeraa.ezcore;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EZCore extends JavaPlugin implements Listener {
	private static EZCore instance;

	public static EZCore getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll((Plugin) this);
		Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
	}
}