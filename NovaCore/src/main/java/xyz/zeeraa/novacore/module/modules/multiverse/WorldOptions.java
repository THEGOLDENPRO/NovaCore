package xyz.zeeraa.novacore.module.modules.multiverse;

import org.bukkit.World.Environment;

public class WorldOptions {
	private String name;
	private Environment environment;
	private boolean hasSeed;
	private long seed;
	private boolean generateStructures;
	private WorldUnloadOption unloadOption;
	private boolean saveOnUnload;
	private boolean lockWeather;
	
	public WorldOptions(String name) {
		this.name = name;
		this.seed = 1L;
		this.environment = Environment.NORMAL;
		this.hasSeed = false;
		this.generateStructures = true;
		this.unloadOption = WorldUnloadOption.KEEP;
		this.saveOnUnload = true;
		this.lockWeather = false;
	}

	public WorldOptions withSeed(long seed) {
		this.hasSeed = true;
		this.seed = seed;

		return this;
	}

	public WorldOptions withEnvironment(Environment environment) {
		this.environment = environment;
		return this;
	}

	public WorldOptions generateStructures(boolean generateStructures) {
		this.generateStructures = generateStructures;
		return this;
	}
	
	public WorldOptions unloadOptions(WorldUnloadOption unloadOption) {
		this.unloadOption = unloadOption;
		return this;
	}

	public WorldOptions saveOnUnload(boolean saveOnUnload) {
		this.saveOnUnload = saveOnUnload;
		return this;
	}
	
	public WorldOptions lockWeather(boolean lockWeather) {
		this.lockWeather = lockWeather;
		return this;
	}
	
	public void setLockWeather(boolean lockWeather) {
		this.lockWeather = lockWeather;
	}
	
	public String getName() {
		return name;
	}

	public boolean hasSeed() {
		return hasSeed;
	}

	public long getSeed() {
		return seed;
	}

	public Environment getEnvironment() {
		return environment;
	}
	
	public boolean isGenerateStructures() {
		return generateStructures;
	}
	
	public WorldUnloadOption getUnloadOption() {
		return unloadOption;
	}
	
	public boolean isSaveOnUnload() {
		return saveOnUnload;
	}
	
	public boolean isLockWeather() {
		return lockWeather;
	}
}