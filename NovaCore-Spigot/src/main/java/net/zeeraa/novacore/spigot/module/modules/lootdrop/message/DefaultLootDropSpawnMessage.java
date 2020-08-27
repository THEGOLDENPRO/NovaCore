package net.zeeraa.novacore.spigot.module.modules.lootdrop.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropEffect;

public class DefaultLootDropSpawnMessage implements LootDropSpawnMessage {
	@Override
	public void showLootDropSpawnMessage(LootDropEffect effect) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "A loot drop is spawning at X: " + ChatColor.AQUA + "" + ChatColor.BOLD + effect.getLocation().getBlockX() + ChatColor.GOLD + "" + ChatColor.BOLD + " Z: " + ChatColor.AQUA + "" + ChatColor.BOLD + effect.getLocation().getBlockZ());
		}
	}
}