package net.zeeraa.novacore.module.modules.gui.holders;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.zeeraa.novacore.module.modules.gui.GUIManager;
import net.zeeraa.novacore.module.modules.gui.callbacks.GUIClickCallback;
import net.zeeraa.novacore.module.modules.gui.callbacks.GUICloseCallback;

/**
 * Add this holder to and {@link Inventory} to listen for click and close
 * events. This only works if the module {@link GUIManager} is enabled
 * 
 * @author Zeeraa
 */
public class GUIHolder implements InventoryHolder {
	private ArrayList<GUICloseCallback> closeCallbacks = new ArrayList<>();
	private ArrayList<GUIClickCallback> clickCallbacks = new ArrayList<>();
	private HashMap<Integer, ArrayList<GUIClickCallback>> slotClickCallbacks = new HashMap<Integer, ArrayList<GUIClickCallback>>();

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
	 * Get a {@link HashMap} with slot specific {@link GUIClickCallback}
	 * @return {@link HashMap} with slot specific {@link GUIClickCallback}
	 */
	public HashMap<Integer, ArrayList<GUIClickCallback>> getSlotClickCallbacks() {
		return slotClickCallbacks;
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
	 * Add a {@link GUIClickCallback} to be executed when a specific slot in the inventory is clicked
	 * 
	 * @param slot The slot number to bind the callback to
	 * @param guiClickCallback {@link GUIClickCallback} to add
	 */
	public void addClickCallback(int slot, GUIClickCallback guiClickCallback) {
		if(!slotClickCallbacks.containsKey(slot)) {
			slotClickCallbacks.put(slot, new ArrayList<GUIClickCallback>());
		}
		
		slotClickCallbacks.get(slot).add(guiClickCallback);
	}

	/**
	 * Unused
	 */
	@Override
	@Deprecated
	public Inventory getInventory() {
		return null;
	}
};