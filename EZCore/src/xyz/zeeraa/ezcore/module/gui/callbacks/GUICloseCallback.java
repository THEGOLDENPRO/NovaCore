package xyz.zeeraa.ezcore.module.gui.callbacks;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Called when a GUI is closed
 */
public interface GUICloseCallback {
	public void onClose(InventoryCloseEvent event);
}