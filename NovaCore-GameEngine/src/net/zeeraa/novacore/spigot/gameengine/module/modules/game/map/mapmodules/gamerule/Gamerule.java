package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.gamerule;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;

public class Gamerule extends MapModule {
	private Map<String, String> gamerules;

	public Gamerule(JSONObject json) {
		super(json);

		gamerules = new HashMap<>();

		json.keySet().forEach(key -> gamerules.put(key, json.getString(key)));

		if (gamerules.size() == 0) {
			Log.warn("Gamerule", "Gamerule module loaded but no rules was defined");
		}
	}

	@Override
	public void onGameStart(Game game) {
		gamerules.forEach((key, val) -> game.getWorld().setGameRuleValue(key, val));
	}
}