package net.zeeraa.novacore.spigot.loottable.loottables.V1;

/**
 * At one point the V1 loot table was called EZLootTableV1 so to maintain legacy
 * support we just create a separate version of {@link LootTableLoaderV1} and
 * change the name to EZLootTableV1
 * 
 * @author Zeeraa
 */
public class LootTableLoaderV1Legacy extends LootTableLoaderV1 {
	@Override
	public String getLoaderName() {
		return "EZLootTableV1";
	}
}