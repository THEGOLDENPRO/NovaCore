package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.chunkloader;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.utils.VectorArea;

public class Chunkloader extends MapModule implements Listener {
	private List<Chunk> chunks;
	private List<VectorArea> areas;

	public Chunkloader(JSONObject json) {
		super(json);

		JSONArray areas = json.getJSONArray("areas");
		for (int i = 0; i < areas.length(); i++) {
			this.areas.add(VectorArea.fromJSON(areas.getJSONObject(i)));
		}
	}

	@Override
	public void onGameStart(Game game) {
		areas.forEach(area -> {
			for (int x = area.getPosition1().getBlockX(); x < area.getPosition2().getBlockX(); x++) {
				for (int z = area.getPosition1().getBlockZ(); z < area.getPosition2().getBlockZ(); z++) {
					Location location = new Location(game.getWorld(), x, 0, z);
					if (!chunks.contains(location.getChunk())) {
						chunks.add(location.getChunk());
					}
				}
			}
		});

		Log.info("Chunkloader", chunks.size() + " chunks will be loaded");

		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());
		chunks.forEach(chunk -> chunk.load());
	}

	@Override
	public void onGameEnd(Game game) {
		HandlerList.unregisterAll(this);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onChunkUnload(ChunkUnloadEvent e) {
		if (chunks.contains(e.getChunk())) {
			Log.trace("Chunkloader", "Preventing unloading of chunk at X: " + e.getChunk().getX() + " Z: " + e.getChunk().getZ());
			e.setCancelled(true);
		}
	}
}