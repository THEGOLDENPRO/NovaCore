package xyz.zeeraa.ezcore.loottable;

import org.json.JSONObject;

public interface LootTableLoader {
	/**
	 * Read loot table from file
	 * 
	 * @param json {@link JSONObject} read from the file by {@link LootTableManager}
	 * @return {@link LootTable}
	 */
	public LootTable read(JSONObject json);
	
	/**
	 * Get the loader name. The loader name is used in the loot table JSON file
	 * @return loot table loader name
	 */
	public String getLoaderName();
}