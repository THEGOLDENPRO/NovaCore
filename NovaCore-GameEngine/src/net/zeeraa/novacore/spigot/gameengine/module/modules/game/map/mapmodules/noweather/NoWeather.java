package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.noweather;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.json.JSONObject;

import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;

public class NoWeather extends MapModule implements Listener {
	public NoWeather(JSONObject json) {
		super(json);
	}

	@Override
	public void onGameStart(Game game) {
		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());

		game.getWorld().setWeatherDuration(0);
		game.getWorld().setThundering(false);
	}

	@Override
	public void onGameEnd(Game game) {
		HandlerList.unregisterAll(this);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWeatherChange(WeatherChangeEvent e) {
		if (!e.toWeatherState()) {
			return;
		}

		e.setCancelled(true);
		e.getWorld().setWeatherDuration(0);
		e.getWorld().setThundering(false);
	}
}