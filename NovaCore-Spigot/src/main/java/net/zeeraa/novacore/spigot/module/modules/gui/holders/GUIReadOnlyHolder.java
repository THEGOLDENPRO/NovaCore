package net.zeeraa.novacore.spigot.module.modules.gui.holders;

import org.bukkit.inventory.Inventory;

import net.zeeraa.novacore.spigot.module.modules.gui.GUIAction;
import net.zeeraa.novacore.spigot.module.modules.gui.GUIManager;

/**
 * Add this holder to and {@link Inventory} to make it read only and deny all
 * interact events. This only works if the module {@link GUIManager} is enabled.
 * This holder will ignore any {@link GUIAction} returned by any click event and
 * always deny the interaction
 * 
 * @author Zeeraa
 */
public class GUIReadOnlyHolder extends GUIHolder {
}