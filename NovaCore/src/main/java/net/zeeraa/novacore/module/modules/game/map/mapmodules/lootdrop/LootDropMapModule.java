package net.zeeraa.novacore.module.modules.game.map.mapmodules.lootdrop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.NovaCore;
import net.zeeraa.novacore.log.Log;
import net.zeeraa.novacore.module.modules.game.Game;
import net.zeeraa.novacore.module.modules.game.map.GameMap;
import net.zeeraa.novacore.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.module.modules.lootdrop.LootDropManager;
import net.zeeraa.novacore.utils.LocationData;
import net.zeeraa.novacore.utils.RandomGenerator;

public class LootDropMapModule extends MapModule {
	private String lootTable;

	private int minDropTime;
	private int maxDropTime;

	private int taskId;

	private List<LocationData> locations;

	private List<Location> locationsReal;

	public LootDropMapModule(JSONObject json) {
		super(json);

		this.lootTable = null;

		this.taskId = -1;
		this.minDropTime = -1;
		this.maxDropTime = -1;

		this.locations = new ArrayList<LocationData>();

		this.locationsReal = null;

		JSONArray locationJson = json.getJSONArray("locations");

		for (int i = 0; i < locationJson.length(); i++) {
			locations.add(new LocationData(locationJson.getJSONObject(i)));
		}

		this.lootTable = json.getString("loot_table");

		this.minDropTime = json.getInt("min_drop_time");

		if (json.has("max_drop_time")) {
			this.maxDropTime = json.getInt("max_drop_time");
		} else {
			Log.warn("Missing max_drop_time for loot drop");
		}

		if (minDropTime != -1 && maxDropTime == -1) {
			maxDropTime = minDropTime;
		}
	}

	@Override
	public void onMapLoad(GameMap map) {
		if (lootTable != null && minDropTime > 0) {
			if (!LootDropManager.getInstance().isEnabled()) {
				LootDropManager.getInstance().enable();
			}

			locationsReal = LocationData.toLocations(locations, map.getWorld());
		} else {
			Log.error("Cant enable loot drop because loot table or min drop time is not set");
		}
	}

	@Override
	public void onGameStart(Game game) {
		if (lootTable != null && minDropTime > 0) {
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
		int delay = RandomGenerator.generate(minDropTime, maxDropTime);

		Log.debug("Next loot drop in " + delay + " seconds");

		this.taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(NovaCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				spawnDrop();

				startTask();
			}
		}, delay * 20);
	}

	public boolean spawnDrop() {
		if (lootTable != null && minDropTime > 0) {
			if (locationsReal.size() > 0) {
				for (int i = 0; i < 40; i++) {
					Location location = locationsReal.get(new Random().nextInt(locationsReal.size()));

					if (LootDropManager.getInstance().canSpawnAt(location)) {
						Log.debug("Loot drop spawned after " + i + " tries");
						LootDropManager.getInstance().spawnDrop(location, lootTable);
						return true;
					}
				}
			} else {
				Log.warn("No loot drop locations specified");
			}
		}

		return false;
	}
}