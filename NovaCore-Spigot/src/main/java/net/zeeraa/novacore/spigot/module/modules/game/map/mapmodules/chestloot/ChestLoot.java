package net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.chestloot;

import org.bukkit.Bukkit;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.RandomGenerator;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.modules.chestloot.ChestLootManager;
import net.zeeraa.novacore.spigot.module.modules.game.Game;
import net.zeeraa.novacore.spigot.module.modules.game.map.GameMap;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodule.MapModule;

public class ChestLoot extends MapModule {
	private String chestLootTable;
	private String enderChestLootTable;

	private int minRefillTime;
	private int maxRefillTime;

	private boolean announceRefills;

	private int taskId;

	public ChestLoot(JSONObject json) {
		super(json);

		this.chestLootTable = null;
		this.enderChestLootTable = null;
		this.minRefillTime = -1;
		this.maxRefillTime = -1;
		this.announceRefills = true;

		this.taskId = -1;

		if (json.has("ender_chest_loot")) {
			this.enderChestLootTable = json.getString("ender_chest_loot");
		}

		if (json.has("chest_loot")) {
			this.chestLootTable = json.getString("chest_loot");
		}

		if (json.has("min_refill_time")) {
			this.minRefillTime = json.getInt("min_refill_time");
		}

		if (json.has("max_refill_time")) {
			this.maxRefillTime = json.getInt("max_refill_time");
		}

		if (json.has("announce_refills")) {
			this.announceRefills = json.getBoolean("announce_refills");
		}

		if (minRefillTime != -1 && maxRefillTime == -1) {
			maxRefillTime = minRefillTime;
		} else if (maxRefillTime != -1 && minRefillTime == -1) {
			minRefillTime = maxRefillTime;
		}
	}

	public String getEnderChestLootTable() {
		return enderChestLootTable;
	}

	public boolean hasEnderChestLootTable() {
		return enderChestLootTable != null;
	}

	public String getChestLootTable() {
		return chestLootTable;
	}

	public boolean hasChestLootTable() {
		return chestLootTable != null;
	}

	public int getMinRefillTime() {
		return minRefillTime;
	}

	public int getMaxRefillTime() {
		return maxRefillTime;
	}

	public boolean isRefillsEnabled() {
		return minRefillTime > 0;
	}

	public boolean isAnnounceRefills() {
		return announceRefills;
	}

	@Override
	public void onMapLoad(GameMap map) {
		if (hasChestLootTable() || hasEnderChestLootTable()) {
			if (ModuleManager.isDisabled(ChestLootManager.class)) {
				Log.info("Loading ChestLootManager because the game map has a chest or ender chest loot table");
				ModuleManager.enable(ChestLootManager.class);
			}

			ChestLootManager.getInstance().setChestLootTable(getChestLootTable());
			ChestLootManager.getInstance().setEnderChestLootTable(getEnderChestLootTable());
		}
	}

	@Override
	public void onGameStart(Game game) {
		if (isRefillsEnabled()) {
			startTask();
		}
	}

	@Override
	public void onGameEnd(Game game) {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}

	private void startTask() {
		int delay = RandomGenerator.generate(minRefillTime, maxRefillTime);

		Log.debug("Next chest refill in " + delay + " seconds");

		this.taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				ChestLootManager.getInstance().refillChests(announceRefills);
				startTask();
			}
		}, delay * 20);
	}
}