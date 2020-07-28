package xyz.zeeraa.ezcore.module.game.map.mapmodule;

import org.json.JSONObject;

import xyz.zeeraa.ezcore.module.game.map.GameMap;

public abstract class MapModule {
	public MapModule(JSONObject json) {
	}
	
	public void onMapLoad(GameMap map) {
	}
}