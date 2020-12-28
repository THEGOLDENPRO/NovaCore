package net.zeeraa.novacore.spigot;

import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONException;

import net.zeeraa.novacore.commons.NovaCommons;
import net.zeeraa.novacore.commons.ServerType;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.log.LogLevel;
import net.zeeraa.novacore.commons.utils.JSONFileType;
import net.zeeraa.novacore.commons.utils.JSONFileUtils;
import net.zeeraa.novacore.spigot.abstraction.ActionBar;
import net.zeeraa.novacore.spigot.abstraction.CommandRegistrator;
import net.zeeraa.novacore.spigot.abstraction.NovaCoreAbstraction;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependantLoader;
import net.zeeraa.novacore.spigot.abstraction.VersionIndependantUtils;
import net.zeeraa.novacore.spigot.abstraction.commons.AbstractBukkitConsoleSender;
import net.zeeraa.novacore.spigot.abstraction.commons.AbstractBukkitPlayerMessageSender;
import net.zeeraa.novacore.spigot.abstraction.commons.BukkitAsyncManager;
import net.zeeraa.novacore.spigot.command.CommandRegistry;
import net.zeeraa.novacore.spigot.command.commands.novacore.NovaCoreCommand;
import net.zeeraa.novacore.spigot.customcrafting.CustomCraftingManager;
import net.zeeraa.novacore.spigot.language.LanguageReader;
import net.zeeraa.novacore.spigot.loottable.LootTableManager;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.LootTableLoaderV1;
import net.zeeraa.novacore.spigot.loottable.loottables.V1.LootTableLoaderV1Legacy;
import net.zeeraa.novacore.spigot.module.ModuleManager;
import net.zeeraa.novacore.spigot.module.event.ModuleDisabledEvent;
import net.zeeraa.novacore.spigot.module.event.ModuleEnableEvent;
import net.zeeraa.novacore.spigot.module.modules.chestloot.ChestLootManager;
import net.zeeraa.novacore.spigot.module.modules.compass.CompassTracker;
import net.zeeraa.novacore.spigot.module.modules.customitems.CustomItemManager;
import net.zeeraa.novacore.spigot.module.modules.game.GameManager;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodule.MapModuleManager;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.chestloot.ChestLoot;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.handcraftingtable.HandCraftingTable;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.lootdrop.LootDropMapModule;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.mapprotection.MapProtection;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.settime.SetTime;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.startmessage.StartMessage;
import net.zeeraa.novacore.spigot.module.modules.game.map.mapmodules.worldborder.WorldborderMapModule;
import net.zeeraa.novacore.spigot.module.modules.gamelobby.GameLobby;
import net.zeeraa.novacore.spigot.module.modules.gui.GUIManager;
import net.zeeraa.novacore.spigot.module.modules.jumppad.JumpPadManager;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.LootDropManager;
import net.zeeraa.novacore.spigot.module.modules.multiverse.MultiverseManager;
import net.zeeraa.novacore.spigot.module.modules.scoreboard.NetherBoardScoreboard;
import net.zeeraa.novacore.spigot.permission.PermissionRegistrator;
import net.zeeraa.novacore.spigot.platformindependent.SpigotPlatformIndependentBungeecordAPI;
import net.zeeraa.novacore.spigot.tasks.abstraction.BukkitSimpleTaskCreator;
import net.zeeraa.novacore.spigot.teams.TeamManager;

public class NovaCore extends JavaPlugin implements Listener {
	private static NovaCore instance;

	private CommandRegistrator bukkitCommandRegistrator;

	private ActionBar actionBar;

	private LootTableManager lootTableManager;

	private File jumpPadFile;

	private File logSeverityConfigFile;
	private FileConfiguration logSeverityConfig;

	private TeamManager teamManager;

	private VersionIndependantUtils versionIndependentUtils;

	private CustomCraftingManager customCraftingManager;

	private boolean hologramsSupport;

	private boolean loadingDone;

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

	public static VersionIndependantUtils versionIndependantUtils() {
		return NovaCore.getInstance().getVersionIndependentUtils();
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
		NovaCommons.setAbstractAsyncManager(new BukkitAsyncManager(this));
		NovaCommons.setServerType(ServerType.SPIGOT);

		Log.setConsoleLogLevel(LogLevel.INFO);

		jumpPadFile = new File(this.getDataFolder().getPath() + File.separator + "jump_pads.json");

		File lootTableFolder = new File(this.getDataFolder().getPath() + File.separator + "LootTables");
		logSeverityConfigFile = new File(this.getDataFolder(), "log_severity.yml");

		try {
			FileUtils.forceMkdir(this.getDataFolder());
			FileUtils.forceMkdir(lootTableFolder);

			if (!jumpPadFile.exists()) {
				JSONFileUtils.createEmpty(jumpPadFile, JSONFileType.JSONArray);
			}

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

		// Register permissions for log levels
		for (LogLevel ll : LogLevel.values()) {
			PermissionRegistrator.registerPermission("novacore.loglevel.auto." + ll.name().toLowerCase(), "Sets the players log level to " + ll.name().toLowerCase() + " whan they join", PermissionDefault.FALSE);
		}

		Log.info("Loading language files...");
		try {
			LanguageReader.readFromJar(this.getClass(), "/lang/en-us.json");
		} catch (Exception e) {
			e.printStackTrace();
		}

		lootTableManager = new LootTableManager();

		lootTableManager.addLoader(new LootTableLoaderV1());
		lootTableManager.addLoader(new LootTableLoaderV1Legacy());

		Log.info("Loading loot tables from: " + lootTableFolder.getPath());
		lootTableManager.loadAll(lootTableFolder);

		customCraftingManager = new CustomCraftingManager();

		// Register plugin channels
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		// Platform independent Bungeecord API
		NovaCommons.setPlatformIndependentBungeecordAPI(new SpigotPlatformIndependentBungeecordAPI());

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
		ModuleManager.loadModule(JumpPadManager.class);

		// Load and enable
		ModuleManager.loadModule(CustomItemManager.class, true);

		MapModuleManager.addMapModule("novacore.chestloot", ChestLoot.class);
		MapModuleManager.addMapModule("novacore.lootdrop", LootDropMapModule.class);
		MapModuleManager.addMapModule("novacore.mapprotection", MapProtection.class);
		MapModuleManager.addMapModule("novacore.handcraftingtable", HandCraftingTable.class);
		MapModuleManager.addMapModule("novacore.worldborder", WorldborderMapModule.class);
		MapModuleManager.addMapModule("novacore.settime", SetTime.class);
		MapModuleManager.addMapModule("novacore.startmessage", StartMessage.class);

		CommandRegistry.registerCommand(new NovaCoreCommand());

		new BukkitRunnable() {
			@Override
			public void run() {
				loadingDone = true;
			}
		}.runTaskLater(this, 1L);
	}

	@Override
	public void onDisable() {
		// Cancel scheduler tasks
		Bukkit.getScheduler().cancelTasks(this);

		if (ModuleManager.moduleExists(JumpPadManager.class)) {
			try {
				JumpPadManager.getInstance().saveJumpPads(jumpPadFile, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Disable modules
		ModuleManager.disableAll();

		// Unregister listeners
		HandlerList.unregisterAll((Plugin) this);

		// Unregister plugin channels
		Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);
	}

	private final void loadNovaCoreJumpPads() {
		try {
			JumpPadManager.getInstance().loadJumpPads(jumpPadFile, this);
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onModuleEnable(ModuleEnableEvent e) {
		if (e.isModule(JumpPadManager.class)) {
			if (loadingDone) {
				Log.info("NovaCore", "Loading jump pads");
				loadNovaCoreJumpPads();
			} else {
				new BukkitRunnable() {
					@Override
					public void run() {
						Log.info("NovaCore", "Loading jump pads (Delayed)");
						loadNovaCoreJumpPads();
					}
				}.runTaskLater(this, 1L);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onModuleDisable(ModuleDisabledEvent e) {
		if (e.isModule(JumpPadManager.class)) {
			try {
				if (JumpPadManager.getInstance().hasBeenEnabled()) {
					JumpPadManager.getInstance().saveJumpPads(jumpPadFile, this);
				}
			} catch (JSONException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		if (Log.getSubscribedPlayers().containsKey(player.getUniqueId())) {
			// Player has set their log level, do not modify
			return;
		}

		int permissionErrorCheck = 0;

		String perms = "";

		// Register permissions for log levels
		for (LogLevel ll : LogLevel.values()) {
			if (player.hasPermission("novacore.loglevel.auto." + ll.name().toLowerCase())) {
				perms += "novacore.loglevel.auto." + ll.name().toLowerCase() + " ";

				Log.debug("NovaCore", "Set " + player.getName() + "s log level to " + ll.name() + " because they have the novacore.loglevel.auto." + ll.name().toLowerCase() + " permission");
				Log.getSubscribedPlayers().put(player.getUniqueId(), ll);
				permissionErrorCheck++;
			}
		}

		if (permissionErrorCheck > 1) {
			Log.warn("NovaCore", player.getName() + " has multiple log level set permissions. Please remove permissions until they only have one of the following: " + perms);
		}
	}
}