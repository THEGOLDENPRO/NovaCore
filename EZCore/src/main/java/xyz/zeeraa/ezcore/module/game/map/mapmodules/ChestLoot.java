package xyz.zeeraa.ezcore.module.game.map.mapmodules;

import org.json.JSONObject;

import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.ModuleManager;
import xyz.zeeraa.ezcore.module.chestloot.ChestLootManager;
import xyz.zeeraa.ezcore.module.game.map.GameMap;
import xyz.zeeraa.ezcore.module.game.map.mapmodule.MapModule;

public class ChestLoot extends MapModule {
	private String chestLootTable;
	private String enderChestLootTable;
	
	public ChestLoot(JSONObject json) {
		super(json);
		
		this.chestLootTable = null;
		this.enderChestLootTable = null;
		
		if(json.has("ender_chest_loot")) {
			this.enderChestLootTable = json.getString("ender_chest_loot");
		}
		
		if(json.has("chest_loot")) {
			this.chestLootTable = json.getString("chest_loot");
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

	@Override
	public void onMapLoad(GameMap map) {
		if (hasChestLootTable() || hasEnderChestLootTable()) {
			if (ModuleManager.isDisabled(ChestLootManager.class)) {
				EZLogger.info("Loading ChestLootManager because the game map has a chest or ender chest loot table");
				ModuleManager.enable(ChestLootManager.class);
			}

			ChestLootManager.getInstance().setChestLootTable(getChestLootTable());
			ChestLootManager.getInstance().setEnderChestLootTable(getEnderChestLootTable());
		}
	}
}