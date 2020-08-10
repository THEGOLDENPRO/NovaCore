package xyz.zeeraa.novacore.module.modules.game.countdown;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import xyz.zeeraa.novacore.NovaCore;

public class DefaultGameCountdown extends GameCountdown {
	private boolean started;
	private int timeLeft;
	private int taskId;
	
	public DefaultGameCountdown() {
		this.started = false;
		this.timeLeft = 60;
		this.taskId = -1;
	}

	@Override
	public boolean hasCountdownStarted() {
		return started;
	}

	@Override
	public boolean startCountdown() {
		if (this.started) {
			return false;
		}
		this.started = false;

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
					Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Starting in " + ChatColor.AQUA + "" + ChatColor.BOLD + timeLeft);

					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
					}
				}
			}
		}, 20L, 20L);

		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The game will start in " + ChatColor.AQUA + "" + ChatColor.BOLD + timeLeft + ChatColor.GOLD + "" + ChatColor.BOLD + " seconds!");

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
		}

		return true;
	}

	@Override
	public boolean cancelCountdown() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			taskId = -1;
			return true;
		}

		return false;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getTimeLeft() {
		return timeLeft;
	}
}