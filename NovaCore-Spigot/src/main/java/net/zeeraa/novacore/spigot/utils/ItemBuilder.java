package net.zeeraa.novacore.spigot.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.zeeraa.novacore.spigot.NovaCore;

/**
 * Useful tool to create item stacks with custom names and data using a single
 * line of code
 * 
 * @author Zeeraa
 */
public class ItemBuilder {
	protected ItemStack item;
	protected ItemMeta meta;

	public ItemBuilder(Material material) {
		this(material, 1);
	}

	public ItemBuilder(Material material, int ammount) {
		this(new ItemStack(material, ammount), false);
	}

	public ItemBuilder(ItemStack itemStack) {
		this(itemStack, false);
	}

	public ItemBuilder(ItemStack itemStack, boolean clone) {
		if (clone) {
			this.item = itemStack.clone();
		} else {
			this.item = itemStack;
		}
		this.meta = this.item.getItemMeta();
	}

	public ItemBuilder setName(String name) {
		meta.setDisplayName(name);
		return this;
	}

	public ItemBuilder addEnchant(Enchantment ench, int level) {
		return this.addEnchant(ench, level, false);
	}

	public ItemBuilder addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
		meta.addEnchant(ench, level, ignoreLevelRestriction);
		return this;
	}

	public ItemBuilder removeEnchant(Enchantment ench) {
		meta.removeEnchant(ench);
		return this;
	}

	public ItemBuilder addItemFlags(ItemFlag itemFlag) {
		meta.addItemFlags(itemFlag);
		return this;
	}

	public ItemBuilder removeItemFlags(ItemFlag itemFlag) {
		meta.removeItemFlags(itemFlag);
		return this;
	}

	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}

	public ItemBuilder setItemMeta(ItemMeta meta) {
		item.setItemMeta(meta);
		this.meta = meta;
		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {
		meta.spigot().setUnbreakable(unbreakable);
		return this;
	}

	public ItemBuilder addLore(String string) {
		List<String> lore;
		if (meta.hasLore()) {
			lore = meta.getLore();
		} else {
			lore = new ArrayList<String>();
		}

		lore.add(string);
		meta.setLore(lore);
		return this;
	}

	public ItemBuilder setDurability(short durability) {
		item.setDurability(durability);
		return this;
	}

	public ItemStack build() {
		this.item.setItemMeta(meta);
		return this.item;
	}

	/**
	 * Get a {@link List} containing all lines from a array of strings
	 * 
	 * @param lines The lines to add to the {@link List}
	 * @return {@link List} containing all lines
	 */
	public static List<String> generateLoreList(String... lines) {
		List<String> lore = new ArrayList<String>();

		for (String line : lines) {
			lore.add(line);
		}

		return lore;

	}

	/**
	 * Get a empty {@link List}
	 * 
	 * @return An empty {@link List}
	 */
	public static List<String> generateLoreList() {
		return new ArrayList<String>();
	}

	/**
	 * Create an item stack of the provided {@link Material} with 1 item
	 * 
	 * @param material The {@link Material} of the item stack
	 * @return {@link ItemStack} of the provided material
	 */
	public static ItemStack materialToItemStack(Material material) {
		return ItemBuilder.materialToItemStack(material, 1);
	}

	/**
	 * Create an item stack of the provided {@link Material} and size
	 * 
	 * @param material The {@link Material} of the item stack
	 * @param size     The size of the item stack
	 * @return {@link ItemStack} of the provided material and size
	 */
	public static ItemStack materialToItemStack(Material material, int size) {
		return new ItemBuilder(material).setAmount(size).build();
	}

	/**
	 * Get a {@link List} with all available record names
	 * 
	 * @return List with all available record names
	 */
	public static List<String> getAvailableRecordNames() {
		return new ArrayList<>(NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().keySet());
	}

	/**
	 * Check if a record with the provided name exists
	 * 
	 * @param name The name of the record
	 * @return <code>true</code> if the record exits
	 */
	public static boolean hasRecordName(String name) {
		for (String s : NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().keySet()) {
			if (s.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get an instance of a {@link ItemBuilder} containing a record with the
	 * provided name
	 * <p>
	 * This will return null if an invalid name is provided, you can check if the
	 * name is valid by using {@link ItemBuilder#hasRecordName(String)}
	 * 
	 * @param recordName The name of the record
	 * @return {@link ItemBuilder} with the provided record or <code>null</code> if
	 *         the record name could not be found
	 */
	public static ItemBuilder getRecordItemBuilder(String recordName) {
		Material material = null;

		for (String key : NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().keySet()) {
			if (key.equalsIgnoreCase(recordName)) {
				material = NovaCore.getInstance().getVersionIndependentUtils().getItembBuilderRecordList().getRecordMap().get(key);
				break;
			}
		}

		if (material == null) {
			return null;
		}

		return new ItemBuilder(material);
	}
}