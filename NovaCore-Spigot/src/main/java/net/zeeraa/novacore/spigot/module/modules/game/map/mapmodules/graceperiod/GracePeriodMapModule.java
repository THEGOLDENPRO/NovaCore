package net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.graceperiod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.Callback;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.module.modules.game.Game;
import net.zeeraa.novacore.spigot.module.modules.game.events.GameBeginEvent;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.timers.BasicTimer;

public class GracePeriodMapModule extends MapModule implements Listener {
	private BasicTimer endTimer;
	private boolean isActive;
	private int seconds;

	public GracePeriodMapModule(JSONObject json) {
		super(json);

		seconds = 15;

		if (json.has("time")) {
			seconds = json.getInt("time");
		} else {
			// TODO: language file
			Log.warn("GracePeriodMapModule", "No time defined. Using the default of 15 seconds");
		}

		isActive = false;

		endTimer = new BasicTimer(seconds, 20L);

		endTimer.addFinishCallback(new Callback() {
			@Override
			public void execute() {
				isActive = false;
				Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Grace period is over");
			}
		});

		Log.info("GracePeriodMapModule", "Grace period will be " + seconds + " seconds long");
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (isActive) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onGameBegin(GameBeginEvent e) {
		Log.info("GracePeriodMapModule", "Received GameBeginEvent");
		isActive = true;
		Bukkit.getServer().getPluginManager().registerEvents(this, NovaCore.getInstance());
		// TODO: language file
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Grace period will end in " + seconds + " seconds");
		endTimer.start();
	}

	@Override
	public void onGameEnd(Game game) {
		isActive = false;
		HandlerList.unregisterAll(this);
	}
}