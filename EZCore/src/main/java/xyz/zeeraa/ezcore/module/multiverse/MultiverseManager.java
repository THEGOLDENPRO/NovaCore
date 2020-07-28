package xyz.zeeraa.ezcore.module.multiverse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.EZModule;

public class MultiverseManager extends EZModule implements Listener {
	private HashMap<String, MultiverseWorld> worlds;

	private static MultiverseManager instance;

	public static MultiverseManager getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		instance = this;
		this.worlds = new HashMap<>();
	}

	@Override
	public void onDisable() {
		unloadAll();
	}

	@Override
	public String getName() {
		return "MultiverseManager";
	}

	public HashMap<String, MultiverseWorld> getWorlds() {
		return worlds;
	}

	public MultiverseWorld createWorld(WorldOptions options) {
		WorldCreator worldCreator = new WorldCreator(options.getName());

		if (options.hasSeed()) {
			worldCreator.seed(options.getSeed());
		}

		worldCreator.environment(worldCreator.environment());

		worldCreator.generateStructures(options.isGenerateStructures());

		World world = worldCreator.createWorld();

		MultiverseWorld multiverseWorld = new MultiverseWorld(options.getName(), world, options.getUnloadOption(), options.isSaveOnUnload(), options.isLockWeather());

		worlds.put(options.getName(), multiverseWorld);

		return multiverseWorld;
	}

	public MultiverseWorld createFromFile(File worldFile, WorldUnloadOption unloadOption) throws IOException {
		if (!worldFile.exists()) {
			throw new FileNotFoundException("Could not find world file: " + worldFile.getPath());
		}

		String worldName = worldFile.getName();

		File worldContainer = Bukkit.getServer().getWorldContainer();

		File targetFile = Paths.get(worldContainer.getAbsolutePath() + "/" + worldName).toFile();
		if (targetFile.exists()) {
			EZLogger.info("Multiverse", "Replacing old world " + worldName);
			targetFile.delete();
			FileUtils.deleteDirectory(targetFile);
		}

		EZLogger.info("Multiverse", "Adding " + worldName + " to " + worldContainer.getAbsolutePath());
		FileUtils.copyDirectory(worldFile, targetFile);

		World world = Bukkit.getServer().createWorld(new WorldCreator(worldName));

		MultiverseWorld multiverseWorld = new MultiverseWorld(worldName, world, unloadOption, true, false);

		worlds.put(multiverseWorld.getName(), multiverseWorld);

		return multiverseWorld;
	}

	public MultiverseWorld cloneWorld(World world, String name, WorldUnloadOption unloadOption) {
		WorldCreator worldCreator = new WorldCreator(name);

		worldCreator.copy(world);

		World world2 = worldCreator.createWorld();

		MultiverseWorld multiverseWorld = new MultiverseWorld(name, world2, unloadOption, true, false);

		worlds.put(name, multiverseWorld);

		return multiverseWorld;
	}

	public MultiverseWorld getWorld(String name) {
		return worlds.get(name);
	}

	public MultiverseWorld getWorld(World world) {
		return worlds.get(world.getName());
	}

	public boolean hasWorld(World world) {
		return hasWorld(world.getName());
	}

	public boolean hasWorld(String name) {
		return worlds.containsKey(name);
	}

	public boolean unload(String name) {
		if (worlds.containsKey(name)) {
			return unload(worlds.get(name));
		}

		return false;
	}

	public boolean unload(MultiverseWorld multiverseWorld) {
		String worldName = multiverseWorld.getWorld().getName();
		EZLogger.info("Multiverse", "Unloading world " + worldName);

		for(Player player : multiverseWorld.getWorld().getPlayers()) {
			player.kickPlayer("Unloading world"); //TODO: move player to other world instead of kicking them
		}
		
		Bukkit.getServer().unloadWorld(multiverseWorld.getWorld(), multiverseWorld.isSaveOnUnload());
		worlds.remove(multiverseWorld.getName());
		
		if (multiverseWorld.getUnloadOption() == WorldUnloadOption.DELETE) {
			EZLogger.info("Multiverse", "Deleting unloaded world " + worldName);
			try {
				FileUtils.deleteDirectory(new File(Bukkit.getServer().getWorldContainer().getPath(), worldName));
			} catch (IOException e) {
				EZLogger.error("Multiverse", "IOException while trying to delete world " + worldName);
				e.printStackTrace();
			}
		}
		return true;
	}

	public void unloadAll() {
		while(worlds.size() > 0) {
			String key = worlds.keySet().iterator().next();
			unload(worlds.get(key));
		}
	}

	@EventHandler
	public void onWatherChage(WeatherChangeEvent e) {
		if (hasWorld(e.getWorld())) {
			if (getWorld(e.getWorld()).isLockWeather()) {
				EZLogger.debug("Multiverse", "Prevented weather change in world " + e.getWorld().getName());
				e.setCancelled(true);
			}
		}
	}
}