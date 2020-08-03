package xyz.zeeraa.novacore.module.modules.game.map.mapmodules.worldborder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import xyz.zeeraa.novacore.NovaCore;
import xyz.zeeraa.novacore.callbacks.Callback;
import xyz.zeeraa.novacore.log.Log;
import xyz.zeeraa.novacore.module.modules.game.Game;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodule.MapModule;
import xyz.zeeraa.novacore.timers.BasicTimer;

public class WorldborderMapModule extends MapModule {
	private double centerX;
	private double centerZ;

	private double startSize;
	private double endSize;

	private double damage;
	private double damageBuffer;

	private int shrinkDuration;
	private int startDelay;

	private BasicTimer startTimer;

	private Game game;

	private int taskId = -1;

	private int stepTime;
	private double stepShrinkValue;
	private int totalSteps;
	private int activeStep;
	private double lastSize;

	public WorldborderMapModule(JSONObject json) {
		super(json);

		this.centerX = 0.5;
		this.centerZ = 0.5;

		this.startSize = 501;
		this.endSize = 51;

		this.damage = 5;
		this.damageBuffer = 2;

		this.shrinkDuration = 900;
		this.startDelay = 600;

		this.stepTime = 30;

		this.taskId = -1;

		if (json.has("center_x")) {
			this.centerX = json.getDouble("center_x");
		} else {
			Log.warn("Missing center_x for wordborder map module");
		}

		if (json.has("center_z")) {
			this.centerZ = json.getDouble("center_z");
		} else {
			Log.warn("Missing center_z for wordborder map module");
		}

		if (json.has("start_size")) {
			this.startSize = json.getDouble("start_size");
		} else {
			Log.warn("Missing start_size for wordborder map module");
		}

		if (json.has("end_size")) {
			this.endSize = json.getDouble("end_size");
		} else {
			Log.warn("Missing end_size for wordborder map module");
		}

		if (json.has("shrink_duration")) {
			this.shrinkDuration = json.getInt("shrink_duration");
		} else {
			Log.warn("Missing shrink_duration for wordborder map module");
		}

		if (json.has("start_delay")) {
			this.startDelay = json.getInt("start_delay");
		} else {
			Log.warn("Missing start_delay for wordborder map module");
		}

		if (json.has("damage")) {
			this.damage = json.getDouble("damage");
		}

		if (json.has("damage_buffer")) {
			this.damageBuffer = json.getDouble("damage_buffer");
		}

		if (json.has("step_time")) {
			this.stepTime = json.getInt("step_time");
		}

		this.totalSteps = shrinkDuration / stepTime;

		this.stepShrinkValue = (startSize - endSize) / (double) totalSteps;

		this.lastSize = startSize;

		this.taskId = -1;
		this.activeStep = 0;
		
		this.startTimer = new BasicTimer(startDelay, 20L);
		this.startTimer.addFinishCallback(new Callback() {
			@Override
			public void execute() {
				Bukkit.getServer().broadcastMessage(ChatColor.GOLD + ""+ChatColor.BOLD + "The world border is starting to shrink");
				for(Player player: Bukkit.getServer().getOnlinePlayers()) {
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
				}
				start();
			}
		});
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterZ() {
		return centerZ;
	}

	public double getStartSize() {
		return startSize;
	}

	public double getEndSize() {
		return endSize;
	}

	public int getShrinkDuration() {
		return shrinkDuration;
	}

	public double getDamage() {
		return damage;
	}

	public double getDamageBuffer() {
		return damageBuffer;
	}

	public BasicTimer getStartTimer() {
		return startTimer;
	}

	@Override
	public void onGameStart(Game game) {
		this.game = game;

		if (game.hasWorld()) {
			game.getWorld().getWorldBorder().setCenter(centerX, centerZ);
			game.getWorld().getWorldBorder().setSize(startSize);
			game.getWorld().getWorldBorder().setDamageAmount(damage);
			game.getWorld().getWorldBorder().setDamageBuffer(damageBuffer);
			Log.info("The worldborder in world " + game.getWorld().getName() + " has been reset");
		} else {
			Log.fatal("Worldborder cant set initial size because the game does not have a world set");
			return;
		}

		startTimer.start();
	}

	@Override
	public void onGameEnd(Game game) {
		this.game = null;
	}

	public boolean cancel() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			taskId = -1;
			return true;
		}
		return false;
	}

	public boolean start() {
		if (taskId == -1) {
			taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(NovaCore.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (activeStep >= totalSteps) {
						cancel();
						return;
					}

					game.getWorld().getWorldBorder().setSize(lastSize - stepShrinkValue, (long) stepTime);

					lastSize -= stepShrinkValue;

					activeStep++;
				}
			}, 0, stepTime * 20);
			return true;
		}
		return false;
	}

	public boolean isRunning() {
		return taskId != -1;
	}
}