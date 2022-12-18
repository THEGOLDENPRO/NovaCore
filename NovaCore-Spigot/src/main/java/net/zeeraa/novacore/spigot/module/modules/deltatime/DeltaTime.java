package net.zeeraa.novacore.spigot.module.modules.deltatime;

import java.time.Duration;
import java.time.Instant;

import net.zeeraa.novacore.commons.tasks.Task;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.tasks.SimpleTask;

public class DeltaTime extends NovaModule {
	private Instant lastCall;
	private Task task;
	private long deltaTimeMS;
	private double deltaTime;

	public DeltaTime() {
		super("NovaCore.DeltaTime");
	}

	@Override
	public void onLoad() {
		task = new SimpleTask(() -> {
			Instant now = Instant.now();

			deltaTimeMS = Duration.between(lastCall, now).toMillis();
			deltaTime = (((double) deltaTimeMS) / 1000D);

			lastCall = now;
		}, 0L, 0L);
	}

	public long getDeltaTimeMS() {
		return deltaTimeMS;
	}

	public double getDeltaTime() {
		return deltaTime;
	}

	@Override
	public void onEnable() throws Exception {
		lastCall = Instant.now();
		Task.tryStartTask(task);
	}

	@Override
	public void onDisable() throws Exception {
		Task.tryStopTask(task);
		deltaTimeMS = 0;
		deltaTime = 0;
	}
}