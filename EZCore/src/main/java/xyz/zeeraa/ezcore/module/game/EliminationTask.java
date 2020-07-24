package xyz.zeeraa.ezcore.module.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.callbacks.Callback;

public class EliminationTask {
	private String username;
	private int taskId;
	private int timeLeft;
	
	private Callback callback;

	public EliminationTask(UUID uuid, String username, int time, Callback callback) {
		this.username = username;
		this.timeLeft = time;
		this.callback = callback;

		this.taskId = -2;
		
		startTask();
	}
	
	private void startTask() {
		if(taskId != -2) {
			return;
		}
		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(EZCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				timeLeft--;

				if (timeLeft == 60) {
					Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "" + username + ChatColor.RED + ChatColor.BOLD + " will be eliminated in 1 minute");
				} else if (timeLeft == 30) {
					Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "" + username + ChatColor.RED + ChatColor.BOLD + " will be eliminated in 30 seconds");
				} else if (timeLeft == 0) {
					callback.execute();
					stop();
				}
			}
		}, 20L, 20L);
	}

	private void stop() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			this.taskId = -1;
		}
	}

	public void cancel() {
		stop();
	}
}