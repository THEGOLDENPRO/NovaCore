package net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.instantvoidkill;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.module.modules.game.Game;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;

public class InstantVoidKill extends MapModule {
	private Task taks;
	
	public InstantVoidKill(JSONObject json) {
		super(json);
		
		this.taks = new SimpleTask(new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().getOnlinePlayers().forEach(player -> {
					if(player.getLocation().getBlockY() < 0) {
						if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
							player.setHealth(0);
						}
					}
				});
			}
		}, 2L);
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