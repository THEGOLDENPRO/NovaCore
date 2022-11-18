package net.zeeraa.novacore.spigot.module.modules.customitems.consumable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependentUtils;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItem;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItemManager;
import net.zeeraa.novacore.spigot.utils.ItemBuilder;

/**
 * A type of custom item that allows the player to interact with the item and
 * then reduce the item if the item was used
 * 
 * @author Zeeraa
 * @since 2.0.0
 *
 */
public abstract class ConsumableCustomItem extends CustomItem {
	private List<RegisteredClickType> registeredClickTypes;
	private AllowedHand allowedHand;

	public ConsumableCustomItem(AllowedHand allowedHand, RegisteredClickType... types) {
		super();

		this.allowedHand = allowedHand;
		this.registeredClickTypes = new ArrayList<>();

		registeredClickTypes.addAll(Arrays.asList(types));
	}

	/**
	 * Get a list of all click types that this item listens for
	 * 
	 * @return {@link List} of {@link RegisteredClickType}
	 */
	public List<RegisteredClickType> getRegisteredClickTypes() {
		return registeredClickTypes;
	}

	/**
	 * Get the hand that the event will fire for
	 * 
	 * @return The {@link AllowedHand}
	 */
	public AllowedHand getAllowedHand() {
		return allowedHand;
	}

	/**
	 * Check if a player can use the item
	 * 
	 * @param player The {@link Player} to check if they can use the item
	 * @return <code>true</code> if the player is allowed to use this item
	 */
	public abstract boolean canUseItem(Player player);

	/**
	 * Called when a player uses the item. Use the return value to determine if the
	 * item should be removed from the players hand
	 * 
	 * @param player The {@link Player} that clicked with the item
	 * @param event  The {@link PlayerInteractEvent} triggering the usage of the
	 *               item
	 * @return <code>true</code> if the item should be removed from the players hand
	 */
	public abstract boolean onItemConsume(Player player, PlayerInteractEvent event);

	@Override
	public void onPlayerInteract(PlayerInteractEvent event) {
		super.onPlayerInteract(event);

		Player player = event.getPlayer();
		if (this.canUseItem(player)) {
			AllowedHand clickedHand = VersionIndependentUtils.get().isInteractEventMainHand(event) ? AllowedHand.MAIN_HAND : AllowedHand.OFF_HAND;
			RegisteredClickType clickType = RegisteredClickType.fromEvent(event);

			ItemStack handItem = clickedHand == AllowedHand.MAIN_HAND ? VersionIndependentUtils.get().getItemInMainHand(player) : VersionIndependentUtils.get().getItemInOffHand(player);
			if (!CustomItemManager.getInstance().isCustomItem(handItem)) {
				return;
			}

			if (!CustomItemManager.getInstance().isType(handItem, this.getClass())) {
				return;
			}

			if (clickType != null) {
				if (registeredClickTypes.contains(clickType)) {

					if (allowedHand == AllowedHand.BOTH || allowedHand == clickedHand) {
						boolean reduce = this.onItemConsume(player, event);
						if (reduce) {
							if (handItem.getAmount() > 1) {
								handItem.setAmount(handItem.getAmount() - 1);
							} else {
								if (clickedHand == AllowedHand.MAIN_HAND) {
									VersionIndependentUtils.get().setItemInMainHand(event.getPlayer(), ItemBuilder.AIR);
								} else {
									VersionIndependentUtils.get().setItemInOffHand(event.getPlayer(), ItemBuilder.AIR);
								}
							}
						}
					}
				}
			}
		}
	}
}