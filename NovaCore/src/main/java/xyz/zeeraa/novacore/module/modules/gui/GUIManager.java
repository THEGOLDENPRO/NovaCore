package xyz.zeeraa.novacore.module.modules.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import xyz.zeeraa.novacore.module.NovaModule;
import xyz.zeeraa.novacore.module.modules.gui.callbacks.GUIClickCallback;
import xyz.zeeraa.novacore.module.modules.gui.callbacks.GUICloseCallback;
import xyz.zeeraa.novacore.module.modules.gui.holders.GUIHolder;
import xyz.zeeraa.novacore.module.modules.gui.holders.GUIReadOnlyHolder;

/**
 * This module is used to make inventory GUI menus
 * 
 * @author Zeeraa
 */
public class GUIManager extends NovaModule implements Listener {
	private static GUIManager instance;
	
	/**
	 * Get instance of {@link GUIManager}
	 * @return Instance
	 */
	public static GUIManager getInstance() {
		return instance;
	}
	
	@Override
		public void onLoad() {
			instance = this;
		}
	
	@Override
	public String getName() {
		return "GUIManager";
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getHolder() instanceof GUIHolder) {
			GUIHolder holder = (GUIHolder) e.getInventory().getHolder();

			GUIAction action = GUIAction.CANCEL_INTERACTION;

			for (GUIClickCallback gcc : holder.getClickCallbacks()) {
				GUIAction newAction = gcc.onClick(e.getClickedInventory(), e.getInventory(), e.getWhoClicked(), e.getSlot(), e.getSlotType(), e.getAction());
				if (!(newAction == null || newAction == GUIAction.NONE)) {
					action = newAction;
				}
			}
			
			if(holder.getSlotClickCallbacks().containsKey(e.getSlot())) {
				for (GUIClickCallback gcc : holder.getSlotClickCallbacks().get(e.getSlot())) {
					GUIAction newAction = gcc.onClick(e.getClickedInventory(), e.getInventory(), e.getWhoClicked(), e.getSlot(), e.getSlotType(), e.getAction());
					if (!(newAction == null || newAction == GUIAction.NONE)) {
						action = newAction;
					}
				}
			}

			if (holder instanceof GUIReadOnlyHolder) {
				e.setCancelled(true);
				return;
			}

			if (action == GUIAction.CANCEL_INTERACTION) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof GUIHolder) {
			GUIHolder holder = (GUIHolder) e.getInventory().getHolder();

			for (GUICloseCallback gcc : holder.getCloseCallbacks()) {
				gcc.onClose(e);
			}
		}
	}
}