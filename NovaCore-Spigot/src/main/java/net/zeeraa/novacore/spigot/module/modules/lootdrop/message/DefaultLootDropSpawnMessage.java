package net.zeeraa.novacore.spigot.module.modules.lootdrop.message;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropEffect;

public class DefaultLootDropSpawnMessage implements LootDropSpawnMessage {
	@Override
	public void showLootDropSpawnMessage(LootDropEffect effect) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			player.sendMessage(LanguageManager.getString(player, "novacore.game.modules.chestloot.lootdrop", effect.getLocation().getBlockX(), effect.getLocation().getBlockZ()));
			// player.sendMessage( "A loot drop is spawning at X: " + ChatColor.AQUA + "" +
			// ChatColor.BOLD + effect.getLocation().getBlockX() + ChatColor.GOLD + "" +
			// ChatColor.BOLD + " Z: " + ChatColor.AQUA + "" + ChatColor.BOLD +
			// effect.getLocation().getBlockZ());
		}
	}
}