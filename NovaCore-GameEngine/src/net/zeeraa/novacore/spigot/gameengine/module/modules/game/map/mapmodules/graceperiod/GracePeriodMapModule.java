package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.graceperiod;

import java.util.ArrayList;
import java.util.List;

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
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameBeginEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.timers.BasicTimer;

public class GracePeriodMapModule extends MapModule implements Listener {
	private BasicTimer endTimer;
	private boolean isActive;
	private int seconds;

	public static final List<DamageCause> BLOCKED_CAUSES = new ArrayList<>();

	static {
		BLOCKED_CAUSES.add(DamageCause.BLOCK_EXPLOSION);
		BLOCKED_CAUSES.add(DamageCause.CONTACT);
		BLOCKED_CAUSES.add(DamageCause.DROWNING);
		BLOCKED_CAUSES.add(DamageCause.ENTITY_ATTACK);
		BLOCKED_CAUSES.add(DamageCause.ENTITY_EXPLOSION);
		BLOCKED_CAUSES.add(DamageCause.FALLING_BLOCK);
		BLOCKED_CAUSES.add(DamageCause.FIRE);
		BLOCKED_CAUSES.add(DamageCause.LAVA);
		BLOCKED_CAUSES.add(DamageCause.LIGHTNING);
		BLOCKED_CAUSES.add(DamageCause.MAGIC);
		BLOCKED_CAUSES.add(DamageCause.POISON);
		BLOCKED_CAUSES.add(DamageCause.PROJECTILE);
		BLOCKED_CAUSES.add(DamageCause.SUFFOCATION);
		BLOCKED_CAUSES.add(DamageCause.THORNS);
		BLOCKED_CAUSES.add(DamageCause.WITHER);
	}

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
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (isActive) {
				if (BLOCKED_CAUSES.contains(e.getCause())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onGameBegin(GameBeginEvent e) {
		Log.info("GracePeriodMapModule", "Received GameBeginEvent");
		isActive = true;
		// TODO: language file
		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Grace period will end in " + seconds + " seconds");
		endTimer.start();
	}

	@Override
	public void onGameStart(Game game) {
		Log.info("GracePeriodMapModule", "Grace period will be " + seconds + " seconds long");
		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());
	}

	@Override
	public void onGameEnd(Game game) {
		isActive = false;
		HandlerList.unregisterAll(this);
	}
}