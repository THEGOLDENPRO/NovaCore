package xyz.zeeraa.ezcore.module.gui.callbacks;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;

import xyz.zeeraa.ezcore.module.gui.GUIAction;

/**
 * Called when a GUI is clicked
 * 
 * @author Zeeraa
 */
public interface GUIClickCallback {
	/**
	 * Called when someone clicks a GUI. The return value is used to either allow or
	 * deny the interaction
	 * 
	 * @param clickedInventory
	 * @param inventory
	 * @param entity
	 * @param clickedSlot
	 * @param slotType
	 * @param clickType
	 * @return return {@link GUIAction#NONE} or <code>null</code> to use the default
	 *         outcome and deny the interaction. use the other values to either
	 *         allow or deny the interaction
	 */
	public GUIAction onClick(Inventory clickedInventory, Inventory inventory, HumanEntity entity, int clickedSlot,
			SlotType slotType, InventoryAction clickType);
}