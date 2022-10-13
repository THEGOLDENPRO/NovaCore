package net.zeeraa.novacore.spigot.loottable.loottables.randomiser;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import net.zeeraa.novacore.spigot.loottable.LootTable;
import net.zeeraa.novacore.spigot.loottable.LootTableLoader;

public class RandomizerLootTableLoader implements LootTableLoader {

	@Override
	public LootTable read(JSONObject json) {
		String lootTableName = json.getString("name");
		JSONObject lootTables = json.getJSONObject("loot_tables");

		Map<String, Integer> lootTablesToUse = new HashMap<>();

		lootTables.keySet().forEach(key -> {
			lootTablesToUse.put(key, lootTables.getInt(key));
		});

		return new RandomizerLootTable(lootTableName, 0, 0, lootTablesToUse);
	}

	@Override
	public String getLoaderName() {
		return "novacore.randomizer";
	}
}