package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.falldamagegraceperiod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameBeginEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.timers.BasicTimer;

public class FallDamageGracePeriodMapModule extends MapModule implements Listener {
	private BasicTimer endTimer;
	private boolean isActive;
	private int seconds;

	public FallDamageGracePeriodMapModule(JSONObject json) {
		super(json);

		seconds = 15;

		if (json.has("time")) {
			seconds = json.getInt("time");
		} else {
			// TODO: language file
			Log.warn("FallDamageGracePeriodMapModule", "No time defined. Using the default of 30 seconds");
		}

		isActive = false;

		endTimer = new BasicTimer(seconds, 20L);

		endTimer.addFinishCallback(new Callback() {
			@Override
			public void execute() {
				isActive = false;
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Fall damage is now enabled");
			}
		});
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.FALL) {
				if (isActive) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onGameBegin(GameBeginEvent e) {
		Log.info("FallDamageGracePeriodMapModule", "Received GameBeginEvent");
		isActive = true;
		// TODO: language file
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Fall damage will be enabled in " + seconds + " seconds");
		endTimer.start();
	}

	@Override
	public void onGameStart(Game game) {
		Log.info("FallDamageGracePeriodMapModule", "Fall damage grace period will be " + seconds + " seconds long");
		Bukkit.getServer().getPluginManager().registerEvents(this, NovaCore.getInstance());
	}

	@Override
	public void onGameEnd(Game game) {
		isActive = false;
		HandlerList.unregisterAll(this);
	}
}