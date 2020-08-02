package xyz.zeeraa.ezcore;

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

import xyz.zeeraa.ezcore.abstraction.ActionBar;
import xyz.zeeraa.ezcore.abstraction.CommandRegistrator;
import xyz.zeeraa.ezcore.abstraction.NMSHandler;
import xyz.zeeraa.ezcore.abstraction.VersionIndependentUtils;
import xyz.zeeraa.ezcore.command.CommandRegistry;
import xyz.zeeraa.ezcore.command.commands.ezcore.EZCoreCommand;
import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.log.LogLevel;
import xyz.zeeraa.ezcore.loottable.LootTableManager;
import xyz.zeeraa.ezcore.loottable.loottables.V1.LootTableLoaderV1;
import xyz.zeeraa.ezcore.module.ModuleManager;
import xyz.zeeraa.ezcore.module.modules.chestloot.ChestLootManager;
import xyz.zeeraa.ezcore.module.modules.compass.CompassTracker;
import xyz.zeeraa.ezcore.module.modules.game.GameManager;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodule.MapModuleManager;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.chestloot.ChestLoot;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.handcraftingtable.HandCraftingTable;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.lootdrop.LootDropMapModule;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.mapprotection.MapProtection;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.settime.SetTime;
import xyz.zeeraa.ezcore.module.modules.game.map.mapmodules.worldborder.WorldborderMapModule;
import xyz.zeeraa.ezcore.module.modules.gamelobby.GameLobby;
import xyz.zeeraa.ezcore.module.modules.gui.GUIManager;
import xyz.zeeraa.ezcore.module.modules.lootdrop.LootDropManager;
import xyz.zeeraa.ezcore.module.modules.multiverse.MultiverseManager;
import xyz.zeeraa.ezcore.module.modules.scoreboard.EZScoreboard;
import xyz.zeeraa.ezcore.teams.TeamManager;

public class EZCore extends JavaPlugin implements Listener {
	private static EZCore instance;

	private CommandRegistrator bukkitCommandRegistrator;

	private ActionBar actionBar;

	private LootTableManager lootTableManager;

	private File logSeverityConfigFile;
	private FileConfiguration logSeverityConfig;

	private TeamManager teamManager;

	private VersionIndependentUtils versionIndependentUtils;

	/**
	 * Get instance of the {@link EZCore} plugin
	 * 
	 * @return {@link EZCore} instance
	 */
	public static EZCore getInstance() {
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

	public VersionIndependentUtils getVersionIndependentUtils() {
		return versionIndependentUtils;
	}
	
	public void setLogLevel(LogLevel logLevel) {
		try {
			EZLogger.info("Setting console log level to " + logLevel.name());
			EZLogger.setConsoleLogLevel(logLevel);
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

		EZLogger.setConsoleLogLevel(LogLevel.INFO);

		File lootTableFolder = new File(this.getDataFolder().getPath() + File.separator + "LootTables");
		logSeverityConfigFile = new File(this.getDataFolder(), "log_severity.yml");

		try {
			FileUtils.forceMkdir(this.getDataFolder());
			FileUtils.forceMkdir(lootTableFolder);

			if (!logSeverityConfigFile.exists()) {
				EZLogger.info("Creating log_severity.yml");
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
				EZLogger.info("Setting console log level to " + logLevel.name());
				EZLogger.setConsoleLogLevel(logLevel);
			} catch (Exception e) {
				EZLogger.warn("The value " + logLevelName + " is not a valid LogLevel. Resetting it to " + LogLevel.INFO.name());
				logSeverityConfig.set("severity", LogLevel.INFO.name());
				logSeverityConfig.save(logSeverityConfigFile);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			EZLogger.fatal("Failed to setup data directory");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		String packageName = this.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);

		EZLogger.info("Server version: " + version);

		try {
			Class<?> clazz = Class.forName("xyz.zeeraa.ezcore.version." + version + ".NMSHandler");
			if (NMSHandler.class.isAssignableFrom(clazz)) {
				NMSHandler nmsHandler = (NMSHandler) clazz.getConstructor().newInstance();

				bukkitCommandRegistrator = nmsHandler.getCommandRegistrator();
				if (bukkitCommandRegistrator == null) {
					EZLogger.warn("CommandRegistrator is not supported for this version");
				}

				actionBar = nmsHandler.getActionBar();
				if (actionBar == null) {
					EZLogger.warn("ActionBar is not supported for this version");
				}
				
				versionIndependentUtils = nmsHandler.getVersionIndependentUtils();
				if (versionIndependentUtils == null) {
					EZLogger.warn("VersionIndependentUtils is not supported for this version");
				}
			} else {
				throw new InvalidClassException("xyz.zeeraa.ezcore.version." + version + ".NMSHandler is not assignable from " + NMSHandler.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			EZLogger.fatal("Could not find support for this CraftBukkit version.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		lootTableManager = new LootTableManager();

		lootTableManager.addLoader(new LootTableLoaderV1());

		EZLogger.info("Loading loot tables from: " + lootTableFolder.getPath());
		lootTableManager.loadAll(lootTableFolder);

		// Register plugin channels
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		// Register events
		Bukkit.getPluginManager().registerEvents(this, this);

		// Load modules
		ModuleManager.loadModule(GUIManager.class);
		ModuleManager.loadModule(LootDropManager.class);
		ModuleManager.loadModule(ChestLootManager.class);
		ModuleManager.loadModule(MultiverseManager.class);
		ModuleManager.loadModule(CompassTracker.class);
		ModuleManager.loadModule(EZScoreboard.class);
		ModuleManager.loadModule(GameManager.class);
		ModuleManager.loadModule(GameLobby.class);

		MapModuleManager.addMapModule("ezcore.chestloot", ChestLoot.class);
		MapModuleManager.addMapModule("ezcore.lootdrop", LootDropMapModule.class);
		MapModuleManager.addMapModule("ezcore.mapprotection", MapProtection.class);
		MapModuleManager.addMapModule("ezcore.handcraftingtable", HandCraftingTable.class);
		MapModuleManager.addMapModule("ezcore.worldborder", WorldborderMapModule.class);
		MapModuleManager.addMapModule("ezcore.settime", SetTime.class);

		CommandRegistry.registerCommand(new EZCoreCommand());
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