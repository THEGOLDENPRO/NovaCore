package net.zeeraa.novacore.spigot.version.v1_16_R3;

import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.json.JSONObject;

public class ParsePotionEffect1_16 {
	public static void readBasePotionData(JSONObject json, PotionMeta meta) {
		PotionData data = readPotionData(json);
		meta.setBasePotionData(data);
	}

	private static PotionData readPotionData(JSONObject potion) {
		PotionType type = PotionType.valueOf(potion.getString("type"));

		boolean extended = false;
		if (potion.has("extended")) {
			extended = potion.getBoolean("extended");
		}

		boolean upgraded = false;
		if (potion.has("upgraded")) {
			upgraded = potion.getBoolean("upgraded");
		}

		return new PotionData(type, extended, upgraded);
	}
}