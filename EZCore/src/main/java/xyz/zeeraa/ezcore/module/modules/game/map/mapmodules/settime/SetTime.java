package xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.settime;

import org.json.JSONObject;

import xyz.zeeraa.ezcore.module.modules.game.Game;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodule.MapModule;

public class SetTime extends MapModule {
	private int time;

	public SetTime(JSONObject json) {
		super(json);

		this.time = 6000;

		if (json.has("time")) {
			this.time = json.getInt("time");
		}
	}

	@Override
	public void onGameStart(Game game) {
		if (game.hasWorld()) {
			game.getWorld().setTime(time);
		}
	}
}