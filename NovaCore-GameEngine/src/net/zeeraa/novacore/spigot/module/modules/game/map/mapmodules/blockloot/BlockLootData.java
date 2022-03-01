package net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.blockloot;

import net.zeeraa.novacore.spigot.loottable.LootTable;

public class BlockLootData {
	private LootTable lootTable;
	private boolean cancelDrop;
	
	public BlockLootData(LootTable lootTable, boolean cancelDrop) {
		this.lootTable = lootTable;
		this.cancelDrop= cancelDrop;
	}
	
	public LootTable getLootTable() {
		return lootTable;
	}
	
	public boolean isCancelDrop() {
		return cancelDrop;
	}
}