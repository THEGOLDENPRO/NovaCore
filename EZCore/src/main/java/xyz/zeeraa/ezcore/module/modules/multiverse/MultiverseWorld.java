package xyz.zeeraa.ezcore.module.modules.multiverse;

import org.bukkit.World;

public class MultiverseWorld {
	private String name;
	private World world;
	private WorldUnloadOption unloadOption;
	private boolean saveOnUnload;
	private boolean lockWeather;
	
	public MultiverseWorld(String name, World world, WorldUnloadOption unloadOption, boolean saveOnUnload, boolean lockWeather) {
		this.name = name;
		this.world = world;
		this.unloadOption = unloadOption;
		this.saveOnUnload = saveOnUnload;
		this.lockWeather = lockWeather;
	}

	public World getWorld() {
		return world;
	}
	
	public String getName() {
		return name;
	}
	
	public void setUnloadOption(WorldUnloadOption unloadOption) {
		this.unloadOption = unloadOption;
	}
	
	public WorldUnloadOption getUnloadOption() {
		return unloadOption;
	}
	
	public MultiverseWorld setSaveOnUnload(boolean saveOnUnload) {
		this.saveOnUnload = saveOnUnload;
		return this;
	}
	
	public MultiverseWorld getLockWeather(boolean lockWeather) {
		this.lockWeather = lockWeather;
		return this;
	}
	
	public boolean isSaveOnUnload() {
		return saveOnUnload;
	}
	
	public boolean isLockWeather() {
		return lockWeather;
	}
}