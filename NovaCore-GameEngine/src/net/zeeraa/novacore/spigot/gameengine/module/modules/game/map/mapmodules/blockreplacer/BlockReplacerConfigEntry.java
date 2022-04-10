package net.zeeraa.novacore.spigot.gameengine.module.modules.game.map.mapmodules.blockreplacer;

import java.util.List;

import org.bukkit.Material;

public class BlockReplacerConfigEntry {
	private String dataFile;
	private List<Material> materialList;

	public BlockReplacerConfigEntry(String dataFile, List<Material> materialList) {
		this.dataFile = dataFile;
		this.materialList = materialList;
	}

	public String getDataFile() {
		return dataFile;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}
}