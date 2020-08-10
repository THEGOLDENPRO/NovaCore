package xyz.zeeraa.novacore.module.modules.lootdrop.message;

import xyz.zeeraa.novacore.module.modules.lootdrop.LootDropEffect;

public interface LootDropSpawnMessage {
	/**
	 * Show a message when a loot drop is spawning
	 * @param effect The {@link LootDropEffect} that spawned
	 */
	public void showLootDropSpawnMessage(LootDropEffect effect);
}