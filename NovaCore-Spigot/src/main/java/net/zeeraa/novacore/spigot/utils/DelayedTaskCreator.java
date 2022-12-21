package net.zeeraa.novacore.spigot.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.zeeraa.novacore.spigot.NovaCore;

public class DelayedTaskCreator {
	public static final BukkitTask runLater(Runnable runnable, long delay) {
		return DelayedTaskCreator.runLater(NovaCore.getInstance(), runnable, delay);
	}

	public static final BukkitTask runLater(Plugin plugin, Runnable runnable, long delay) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskLater(plugin, delay);
	}

	public static final BukkitTask runLaterAsync(Runnable runnable, long delay) {
		return DelayedTaskCreator.runLaterAsync(NovaCore.getInstance(), runnable, delay);
	}

	public static final BukkitTask runLaterAsync(Plugin plugin, Runnable runnable, long delay) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				runnable.run();
			}
		}.runTaskLaterAsynchronously(plugin, delay);
	}
}