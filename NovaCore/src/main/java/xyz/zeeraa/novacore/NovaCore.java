package xyz.zeeraa.novacore;

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

import xyz.zeeraa.novacore.abstraction.ActionBar;
import xyz.zeeraa.novacore.abstraction.CommandRegistrator;
import xyz.zeeraa.novacore.abstraction.VersionIndependantLoader;
import xyz.zeeraa.novacore.abstraction.VersionIndependantUtils;
import xyz.zeeraa.novacore.command.CommandRegistry;
import xyz.zeeraa.novacore.command.commands.novacore.NovaCoreCommand;
import xyz.zeeraa.novacore.log.Log;
import xyz.zeeraa.novacore.log.LogLevel;
import xyz.zeeraa.novacore.loottable.LootTableManager;
import xyz.zeeraa.novacore.loottable.loottables.V1.LootTableLoaderV1;
import xyz.zeeraa.novacore.module.ModuleManager;
import xyz.zeeraa.novacore.module.modules.chestloot.ChestLootManager;
import xyz.zeeraa.novacore.module.modules.compass.CompassTracker;
import xyz.zeeraa.novacore.module.modules.game.GameManager;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodule.MapModuleManager;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodules.chestloot.ChestLoot;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodules.handcraftingtable.HandCraftingTable;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodules.lootdrop.LootDropMapModule;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodules.mapprotection.MapProtection;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodules.settime.SetTime;
import xyz.zeeraa.novacore.module.modules.game.map.mapmodules.worldborder.WorldborderMapModule;
import xyz.zeeraa.novacore.module.modules.gamelobby.GameLobby;
import xyz.zeeraa.novacore.module.modules.gui.GUIManager;
import xyz.zeeraa.novacore.module.modules.lootdrop.LootDropManager;
import xyz.zeeraa.novacore.module.modules.multiverse.MultiverseManager;
import xyz.zeeraa.novacore.module.modules.scoreboard.NetherBoardScoreboard;
import xyz.zeeraa.novacore.teams.TeamManager;

public class NovaCore extends JavaPlugin implements Listener {
	private static NovaCore instance;

	private CommandRegistrator bukkitCommandRegistrator;

	private ActionBar actionBar;

	private LootTableManager lootTableManager;

	private File logSeverityConfigFile;
	private FileConfiguration logSeverityConfig;

	private TeamManager teamManager;

	private VersionIndependantUtils versionIndependentUtils;

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

	public VersionIndependantUtils getVersionIndependentUtils() {
		return versionIndependentUtils;
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

		String packageName = this.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);

		Log.info("Server version: " + version);

		try {
			Class<?> clazz = Class.forName("xyz.zeeraa.novacore.version." + version + ".VersionIndependantLoader");
			if (VersionIndependantLoader.class.isAssignableFrom(clazz)) {
				VersionIndependantLoader setIndependantLoader = (VersionIndependantLoader) clazz.getConstructor().newInstance();

				bukkitCommandRegistrator = setIndependantLoader.getCommandRegistrator();
				if (bukkitCommandRegistrator == null) {
					Log.warn("CommandRegistrator is not supported for this version");
				}

				actionBar = setIndependantLoader.getActionBar();
				if (actionBar == null) {
					Log.warn("ActionBar is not supported for this version");
				}
				
				versionIndependentUtils = setIndependantLoader.getVersionIndependentUtils();
				if (versionIndependentUtils == null) {
					Log.warn("VersionIndependentUtils is not supported for this version");
				}
			} else {
				throw new InvalidClassException("xyz.zeeraa.novacore.version." + version + ".VersionIndependantLoader is not assignable from " + VersionIndependantLoader.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.fatal("Could not find support for this CraftBukkit version.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		lootTableManager = new LootTableManager();

		lootTableManager.addLoader(new LootTableLoaderV1());

		Log.info("Loading loot tables from: " + lootTableFolder.getPath());
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