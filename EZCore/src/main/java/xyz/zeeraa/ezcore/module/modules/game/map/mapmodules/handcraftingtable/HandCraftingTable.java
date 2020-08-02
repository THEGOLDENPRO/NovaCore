package xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.handcraftingtable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.json.JSONObject;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.module.modules.game.Game;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodule.MapModule;

public class HandCraftingTable extends MapModule implements Listener {
	private Game game;

	public HandCraftingTable(JSONObject json) {
		super(json);
	}

	@Override
	public void onGameStart(Game game) {
		this.game = game;
		Bukkit.getServer().getPluginManager().registerEvents(this, EZCore.getInstance());
	}

	@Override
	public void onGameEnd(Game game) {
		this.game = null;
		HandlerList.unregisterAll(this);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (game.hasWorld()) {
				if (e.getPlayer().getLocation().getWorld() == game.getWorld()) {
					ItemStack item = EZCore.getInstance().getVersionIndependentUtils().getItemInMainHand(e.getPlayer());
					if (item != null) {
						if (item.getType() == Material.WORKBENCH) {
							e.getPlayer().openWorkbench(null, true);
						}
					}
				}
			}
		}
	}
}