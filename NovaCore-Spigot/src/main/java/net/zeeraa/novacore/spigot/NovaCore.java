package net.zeeraa.novacore.spigot;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.zeeraa.novacore.commons.NovaCommons;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.log.LogLevel;
import net.zeeraa.novacore.spigot.abstraction.ActionBar;
import net.zeeraa.novacore.spigot.abstraction.CommandRegistrator;
import net.zeeraa.novacore.spigot.abstraction.NovaCoreAbstraction;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependantLoader;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils;
import net.zeeraa.novacore.spigot.abstraction.commons.AbstractBukkitConsoleSender;
import net.zeeraa.novacore.spigot.abstraction.commons.AbstractBukkitPlayerMessageSender;
import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.command.commands.novacore.NovaCoreCommand;
import net.zeeraa.novacore.spigot.customcrafting.CustomCraftingManager;
import net.zeeraa.novacore.spigot.loottable.LootTableManager;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.LootTableLoaderV1;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.modules.chestloot.ChestLootManager;
import net.zeeraa.novacore.spigot.module.modules.compass.CompassTracker;
import net.zeeraa.novacore.spigot.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodule.MapModuleManager;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.chestloot.ChestLoot;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.handcraftingtable.HandCraftingTable;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.lootdrop.LootDropMapModule;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.mapprotection.MapProtection;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.settime.SetTime;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.worldborder.WorldborderMapModule;
import net.zeeraa.novacore.spigot.module.modules.gamelobby.GameLobby;
import net.zeeraa.novacore.spigot.module.modules.gui.GUIManager;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropManager;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.module.modules.scoreboard.NetherBoardScoreboard;
import net.zeeraa.novacore.spigot.tasks.abstraction.BukkitSimpleTaskCreator;
import net.zeeraa.novacore.spigot.teams.TeamManager;

public class NovaCore extends JavaPlugin implements Listener {
	private static NovaCore instance;

	private CommandRegistrator bukkitCommandRegistrator;

	private ActionBar actionBar;

	private LootTableManager lootTableManager;

	private File logSeverityConfigFile;
	private FileConfiguration logSeverityConfig;

	private TeamManager teamManager;

	private VersionIndependantUtils versionIndependentUtils;

	private CustomCraftingManager customCraftingManager;
	
	private boolean hologramsSupport;
	
	/**
	 * Get instance of the {@link NovaCore} plugin
	 * 
	 * @return {@link NovaCore} instance
	 */
	public static NovaCore getInstance() {
		return instance;
	}

	public CommandRegistrator getCommandRegistrator() {
		return bukkitCommandRegistrator;
	}

	public ActionBar getActionBar() {
		return actionBar;
	}

	public LootTableManager getLootTableManager() {
		return lootTableManager;
	}

	public TeamManager getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(TeamManager teamManager) {
		this.teamManager = teamManager;
	}

	public boolean hasTeamManager() {
		return teamManager != null;
	}

	public CustomCraftingManager getCustomCraftingManager() {
		return customCraftingManager;
	}

	public VersionIndependantUtils getVersionIndependentUtils() {
		return versionIndependentUtils;
	}
	
	public boolean hasHologramsSupport() {
		return hologramsSupport;
	}

	public void setLogLevel(LogLevel logLevel) {
		try {
			Log.info("Setting console log level to " + logLevel.name());
			Log.setConsoleLogLevel(logLevel);
			logSeverityConfig.set("severity", logLevel.name());
			logSeverityConfig.save(logSeverityConfigFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEnable() {
		instance = this;
		this.teamManager = null;

		NovaCommons.setAbstractConsoleSender(new AbstractBukkitConsoleSender());
		NovaCommons.setAbstractPlayerMessageSender(new AbstractBukkitPlayerMessageSender());
		NovaCommons.setAbstractSimpleTaskCreator(new BukkitSimpleTaskCreator());

		Log.setConsoleLogLevel(LogLevel.INFO);

		File lootTableFolder = new File(this.getDataFolder().getPath() + File.separator + "LootTables");
		logSeverityConfigFile = new File(this.getDataFolder(), "log_severity.yml");

		try {
			FileUtils.forceMkdir(this.getDataFolder());
			FileUtils.forceMkdir(lootTableFolder);

			if (!logSeverityConfigFile.exists()) {
				Log.info("Creating log_severity.yml");
				FileUtils.touch(logSeverityConfigFile);
			}
			logSeverityConfig = YamlConfiguration.loadConfiguration(logSeverityConfigFile);

			if (!logSeverityConfig.contains("severity")) {
				logSeverityConfig.set("severity", LogLevel.INFO.name());
				logSeverityConfig.save(logSeverityConfigFile);
			}

			String logLevelName = logSeverityConfig.getString("severity");

			try {
				LogLevel logLevel = LogLevel.valueOf(logLevelName);
				Log.info("Setting console log level to " + logLevel.name());
				Log.setConsoleLogLevel(logLevel);
			} catch (Exception e) {
				Log.warn("The value " + logLevelName + " is not a valid LogLevel. Resetting it to " + LogLevel.INFO.name());
				logSeverityConfig.set("severity", LogLevel.INFO.name());
				logSeverityConfig.save(logSeverityConfigFile);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.fatal("Failed to setup data directory");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		String version = NovaCoreAbstraction.getNMSVersion();

		Log.info("Server version: " + version);

		try {
			Class<?> clazz = Class.forName("net.zeeraa.novacore.spigot.version." + version + ".VersionIndependantLoader");
			if (VersionIndependantLoader.class.isAssignableFrom(clazz)) {
				VersionIndependantLoader versionIndependantLoader = (VersionIndependantLoader) clazz.getConstructor().newInstance();

				bukkitCommandRegistrator = versionIndependantLoader.getCommandRegistrator();
				if (bukkitCommandRegistrator == null) {
					Log.warn("CommandRegistrator is not supported for this version");
				}

				actionBar = versionIndependantLoader.getActionBar();
				if (actionBar == null) {
					Log.warn("ActionBar is not supported for this version");
				}

				versionIndependentUtils = versionIndependantLoader.getVersionIndependentUtils();
				if (versionIndependentUtils == null) {
					Log.warn("VersionIndependentUtils is not supported for this version");
				}

				Bukkit.getServer().getPluginManager().registerEvents(versionIndependantLoader.getListeners(), this);
			} else {
				throw new InvalidClassException(clazz.getName() + " is not assignable from " + VersionIndependantLoader.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.fatal("Could not find support for this CraftBukkit version.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		if (Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") != null) {
			this.hologramsSupport = true;
			Log.info("Hologram support enabled");
		} else {
			this.hologramsSupport = false;
			Log.warn("Hologram support disabled due to HolographicDisplays not being installed");
		}

		lootTableManager = new LootTableManager();

		lootTableManager.addLoader(new LootTableLoaderV1());

		Log.info("Loading loot tables from: " + lootTableFolder.getPath());
		lootTableManager.loadAll(lootTableFolder);

		customCraftingManager = new CustomCraftingManager();

		// Register plugin channels
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		// Register events
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(customCraftingManager, this);

		// Load modules
		ModuleManager.loadModule(GUIManager.class);
		ModuleManager.loadModule(LootDropManager.class);
		ModuleManager.loadModule(ChestLootManager.class);
		ModuleManager.loadModule(MultiverseManager.class);
		ModuleManager.loadModule(CompassTracker.class);
		ModuleManager.loadModule(NetherBoardScoreboard.class);
		ModuleManager.loadModule(GameManager.class);
		ModuleManager.loadModule(GameLobby.class);

		MapModuleManager.addMapModule("novacore.chestloot", ChestLoot.class);
		MapModuleManager.addMapModule("novacore.lootdrop", LootDropMapModule.class);
		MapModuleManager.addMapModule("novacore.mapprotection", MapProtection.class);
		MapModuleManager.addMapModule("novacore.handcraftingtable", HandCraftingTable.class);
		MapModuleManager.addMapModule("novacore.worldborder", WorldborderMapModule.class);
		MapModuleManager.addMapModule("novacore.settime", SetTime.class);

		CommandRegistry.registerCommand(new NovaCoreCommand());
	}

	@Override
	public void onDisable() {
		// Cancel scheduler tasks
		Bukkit.getScheduler().cancelTasks(this);

		// Disable modules
		ModuleManager.disableAll();

		// Unregister listeners
		HandlerList.unregisterAll((Plugin) this);

		// Unregister plugin channels
		Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
	}
}