package net.zeeraa.novacore.spigot.module.modules.lootdrop.message;

import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropEffect;

public interface LootDropSpawnMessage {
	/**
	 * Show a message when a loot drop is spawning
	 * @param effect The {@link LootDropEffect} that spawned
	 */
	public void showLootDropSpawnMessage(LootDropEffect effect);
}