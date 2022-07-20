package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.potioneffect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.NovaCoreGameEngine;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;

public class AddPotionEffect extends MapModule {
	private List<PotionEffect> effects;

	public AddPotionEffect(JSONObject json) {
		super(json);

		effects = new ArrayList<>();

		JSONArray effectsJson = json.getJSONArray("effects");
		for (int i = 0; i < effectsJson.length(); i++) {
			JSONObject effect = effectsJson.getJSONObject(i);

			PotionEffectType type = PotionEffectType.getByName(effect.getString("type"));
			if (type == null) {
				Log.error("AddPotionEffect", "Invalid potion effect type: " + effect.getString("type"));
				continue;
			}

			int duration = effect.getInt("duration");
			int amplifier = effect.getInt("amplifier");

			boolean ambient = effect.getBoolean("ambient");
			boolean particles = effect.getBoolean("particles");

			effects.add(new PotionEffect(type, duration, amplifier, ambient, particles));
		}
	}

	@Override
	public void onGameStart(Game game) {
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getServer().getOnlinePlayers().forEach(player -> {
					effects.forEach(effect -> player.addPotionEffect(effect, true));
				});
			}
		}.runTaskLater(NovaCoreGameEngine.getInstance(), 2L);
	}
}