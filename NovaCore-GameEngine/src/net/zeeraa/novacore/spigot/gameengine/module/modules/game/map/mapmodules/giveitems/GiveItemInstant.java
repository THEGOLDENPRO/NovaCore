package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.giveitems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.Game;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.events.GameBeginEvent;
import net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodule.MapModule;
import net.zeeraa.novacore.spigot.utils.JSONItemParser;

/**
 * Used to give players items when the game start. See {@link JSONItemParser}
 * for item format
 * 
 * @author Zeeraa
 */
public class GiveItemInstant extends MapModule implements Listener {
	private List<ItemStack> items;

	public GiveItemInstant(JSONObject json) {
		super(json);

		this.items = new ArrayList<ItemStack>();

		JSONArray items = json.getJSONArray("items");
		for (int i = 0; i < items.length(); i++) {
			try {
				this.items.add(JSONItemParser.itemFromJSON(items.getJSONObject(i)));
			} catch (JSONException | IOException e) {
				Log.error("GiveItemInstant", "Failed to parse item at index " + i + ". " + e.getClass().getName() + " " + e.getMessage());
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onGameBegin(GameBeginEvent e) {
		e.getGame().getPlayers().forEach(uuid -> {
			Player player = Bukkit.getServer().getPlayer(uuid);
			if (player != null) {
				items.forEach(item -> player.getInventory().addItem(item.clone()));
			}
		});
	}

	@Override
	public void onGameStart(Game game) {
		Bukkit.getServer().getPluginManager().registerEvents(this, game.getPlugin());
	}

	@Override
	public void onGameEnd(Game game) {
		HandlerList.unregisterAll(this);
	}
}