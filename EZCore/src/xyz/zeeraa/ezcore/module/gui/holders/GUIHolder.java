package xyz.zeeraa.ezcore.module.gui.holders;

import java.util.ArrayList;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import xyz.zeeraa.ezcore.module.gui.GUIManager;
import xyz.zeeraa.ezcore.module.gui.callbacks.GUIClickCallback;
import xyz.zeeraa.ezcore.module.gui.callbacks.GUICloseCallback;

/**
 * Add this holder to and {@link Inventory} to listen for click and close
 * events. This only works if the module {@link GUIManager} is enabled
 */
public class GUIHolder implements InventoryHolder {
	private ArrayList<GUICloseCallback> closeCallbacks = new ArrayList<>();
	private ArrayList<GUIClickCallback> clickCallbacks = new ArrayList<>();

	/**
	 * Get close callback list
	 * 
	 * @return {@link ArrayList} with {@link GUICloseCallback}
	 */
	public ArrayList<GUICloseCallback> getCloseCallbacks() {
		return closeCallbacks;
	}

	/**
	 * Get click callback list
	 * 
	 * @return {@link ArrayList} with {@link GUIClickCallback}
	 */
	public ArrayList<GUIClickCallback> getClickCallbacks() {
		return clickCallbacks;
	}

	/**
	 * Add a {@link GUICloseCallback} to be executed when the inventory is closed
	 * 
	 * @param guiCloseCallback {@link GUICloseCallback} to add
	 */
	public void addCloseCallback(GUICloseCallback guiCloseCallback) {
		closeCallbacks.add(guiCloseCallback);
	}

	/**
	 * Add a {@link GUIClickCallback} to be executed when the inventory is clicked
	 * 
	 * @param guiClickCallback {@link GUIClickCallback} to add
	 */
	public void addClickCallback(GUIClickCallback guiClickCallback) {
		clickCallbacks.add(guiClickCallback);
	}

	/**
	 * Unused
	 */
	@Override
	@Deprecated
	public Inventory getInventory() {
		return null;
	}
}