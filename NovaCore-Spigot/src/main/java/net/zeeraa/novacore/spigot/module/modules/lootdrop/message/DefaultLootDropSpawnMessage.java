package net.zeeraa.novacore.spigot.module.modules.lootdrop.message;

import org.bukkit.Bukkit;

import net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils;
import net.zeeraa.novacore.spigot.abstraction.enums.VersionIndependantSound;
import net.zeeraa.novacore.spigot.language.LanguageManager;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropEffect;

public class DefaultLootDropSpawnMessage implements LootDropSpawnMessage {
	@Override
	public void showLootDropSpawnMessage(LootDropEffect effect) {
		Bukkit.getServer().getOnlinePlayers().forEach(player -> {
			VersionIndependantUtils.get().playSound(player, player.getLocation(), VersionIndependantSound.NOTE_PLING, 1F, 1F);
			player.sendMessage(LanguageManager.getString(player, "novacore.game.modules.chestloot.lootdrop", effect.getLocation().getBlockX(), effect.getLocation().getBlockZ()));
		});
	}
}