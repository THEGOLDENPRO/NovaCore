package net.zeeraa.novacore.spigot.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.md_5.bungee.api.ChatColor;

public class JSONItemParser {
	/**
	 * Convert a {@link JSONObject} to an {@link ItemStack}
	 * 
	 * @param json The {@link JSONObject}
	 * @return An {@link ItemStack} created from the json object
	 * @throws IOException   if base64 decoding fails
	 * @throws JSONException if any keys are missing
	 */
	@SuppressWarnings("deprecation")
	public static ItemStack itemFromJSON(JSONObject json) throws IOException, IOException {
		if (json.has("base64")) {
			return BukkitSerailization.itemStackFromBase64(json.getString("base64"));
		}

		ItemBuilder builder = null;

		if (json.has("record")) {
			builder = ItemBuilder.getRecordItemBuilder(json.getString("record"));
		} else {
			Material material = Material.valueOf(json.getString("material"));

			short damage = 0;
			byte data = 0;

			if (json.has("data")) {
				data = (byte) json.getInt("data");
			}

			if (json.has("damage")) {
				damage = (short) json.getInt("damage");
			}

			builder = new ItemBuilder(new ItemStack(material, 1, damage, data));
		}

		if (json.has("lore")) {
			JSONArray lore = json.getJSONArray("lore");

			for (int i = 0; i < lore.length(); i++) {
				builder.addLore(lore.getString(i));
			}
		}

		if (json.has("display_name")) {
			builder.setName(ChatColor.translateAlternateColorCodes('$', json.getString("display_name")));
		}

		if (json.has("amount")) {
			builder.setAmount(json.getInt("amount"));
		}

		if (json.has("unbreakable")) {
			builder.setUnbreakable(json.getBoolean("unbreakable"));
		}

		if (json.has("enchantments")) {
			JSONObject enchantments = json.getJSONObject("enchantments");

			for (String key : enchantments.keySet()) {
				builder.addEnchant(Enchantment.getByName(key), enchantments.getInt(key), true);
			}
		}

		if (json.has("stored_enchantments")) {
			JSONObject enchantments = json.getJSONObject("stored_enchantments");

			for (String key : enchantments.keySet()) {
				builder.addStoredEnchant(Enchantment.getByName(key), enchantments.getInt(key), true);
			}
		}

		if (json.has("item_flags")) {
			JSONArray itemFlags = json.getJSONArray("item_flags");

			for (int i = 0; i < itemFlags.length(); i++) {
				builder.addItemFlags(ItemFlag.valueOf(itemFlags.getString(i)));
			}
		}

		ItemStack item = builder.build();

		if (json.has("potion_data")) {
			PotionMeta meta = (PotionMeta) item.getItemMeta();

			JSONObject potionData = json.getJSONObject("potion_data");

			if (potionData.has("custom_effects")) {
				JSONArray customEffects = potionData.getJSONArray("custom_effects");
				for (int i = 0; i < customEffects.length(); i++) {
					meta.addCustomEffect(readPotionEffect(customEffects.getJSONObject(i)), true);
				}
			}

			if (potionData.has("main_effect")) {
				PotionEffectType type = PotionEffectType.getByName(potionData.getString("main_effect"));
				meta.setMainEffect(type);
			}

			item.setItemMeta(meta);
		}

		if (json.has("book_data")) {
			JSONObject bookData = json.getJSONObject("book_data");

			BookMeta meta = (BookMeta) item.getItemMeta();

			if (bookData.has("author")) {
				meta.setAuthor(bookData.getString("author"));
			}

			if (bookData.has("title")) {
				meta.setTitle(bookData.getString("title"));
			}

			if (json.has("pages")) {
				JSONArray pages = json.getJSONArray("pages");

				for (int i = 0; i < pages.length(); i++) {
					meta.addPage(pages.getString(i));
				}
			}

			item.setItemMeta(meta);
		}

		return item;
	}

	private static PotionEffect readPotionEffect(JSONObject potion) {
		PotionEffectType type = PotionEffectType.getByName(potion.getString("type"));
		int duration = potion.getInt("duration");

		int amplifier = 0;
		if (potion.has("amplifier")) {
			amplifier = potion.getInt("amplifier");
		}

		boolean ambient = false;
		if (potion.has("ambient")) {
			ambient = potion.getBoolean("ambient");
		}

		boolean particles = true;
		if (potion.has("particles")) {
			particles = potion.getBoolean("particles");
		}

		return new PotionEffect(type, duration, amplifier, ambient, particles);
	}
}