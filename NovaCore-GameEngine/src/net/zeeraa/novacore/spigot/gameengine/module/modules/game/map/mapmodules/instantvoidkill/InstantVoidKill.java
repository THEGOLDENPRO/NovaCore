package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.instantvoidkill;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;

public class InstantVoidKill extends MapModule {
	private Task taks;

	private int y;

	public InstantVoidKill(JSONObject json) {
		super(json);

		y = 0;
		
		if(!NovaCore.getInstance().isNoNMSMode()) {
			y = VersionIndependantUtils.get().getMinY();
		}

		if (json.has("y")) {
			y = json.getInt("y");
		}

		this.taks = new SimpleTask(new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getOnlinePlayers().forEach(player -> {
					if (player.getLocation().getBlockY() < y) {
						if (!player.isDead()) {
							if (player.getHealth() > 0) {
								if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
									player.setHealth(0);
								}
							}
						}
					}
				});
			}
		}, 2L);
	}

	public int getY() {
		return y;
	}

	@Override
	public void onGameStart(Game game) {
		Task.tryStartTask(taks);
	}

	@Override
	public void onGameEnd(Game game) {
		Task.tryStopTask(taks);
	}
}