package net.zeeraa.novacore.spigot.module.modules.game.countdown;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.language.LanguageManager;

public class DefaultGameCountdown extends GameCountdown {
	private boolean started;
	private int timeLeft;
	private int taskId;

	private int startTime;

	public DefaultGameCountdown() {
		this(60);
	}

	public DefaultGameCountdown(int time) {
		super();

		this.started = false;
		this.timeLeft = time;
		this.startTime = time;
		this.taskId = -1;
	}

	@Override
	public void resetTimeLeft() {
		this.timeLeft = startTime;
	}

	@Override
	public boolean hasCountdownStarted() {
		return started;
	}

	@Override
	public boolean isCountdownRunning() {
		return taskId != -1;
	}

	@Override
	public boolean startCountdown() {
		if (this.started) {
			return false;
		}
		this.started = true;

		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				timeLeft--;
				if (timeLeft <= 0) {
					Bukkit.getScheduler().cancelTask(taskId);
					taskId = -1;
					try {
						onCountdownFinished();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				if (timeLeft <= 10) {
					// Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD +
					// "Starting in " + ChatColor.AQUA + "" + ChatColor.BOLD + timeLeft);
					LanguageManager.broadcast("novacore.game.starting_in", timeLeft);
					
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
					}
				}
			}
		}, 20L, 20L);

		LanguageManager.broadcast("novacore.game.countdown", timeLeft);

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
		}

		return true;
	}

	@Override
	public boolean cancelCountdown() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			started = false;
			timeLeft = startTime;
			taskId = -1;
			return true;
		}

		return false;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
		if (!hasCountdownStarted()) {
			this.timeLeft = startTime;
		}
	}

	@Override
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	@Override
	public int getTimeLeft() {
		return timeLeft;
	}
}