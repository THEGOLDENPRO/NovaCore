package xyz.zeeraa.ezcore.module.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import xyz.zeeraa.ezcore.module.EZModule;
import xyz.zeeraa.ezcore.module.gui.callbacks.GUIClickCallback;
import xyz.zeeraa.ezcore.module.gui.callbacks.GUICloseCallback;
import xyz.zeeraa.ezcore.module.gui.holders.GUIHolder;
import xyz.zeeraa.ezcore.module.gui.holders.GUIReadOnlyHolder;

/**
 * This module is used to make inventory GUI menus
 * 
 * @author Zeeraa
 */
public class GUIManager extends EZModule implements Listener {
	@Override
	public String getName() {
		return "GUI Manager";
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