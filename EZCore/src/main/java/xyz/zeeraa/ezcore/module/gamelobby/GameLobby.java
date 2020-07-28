package xyz.zeeraa.ezcore.module.gamelobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.log.EZLogger;
import xyz.zeeraa.ezcore.module.EZModule;
import xyz.zeeraa.ezcore.module.game.GameManager;
import xyz.zeeraa.ezcore.module.gamelobby.events.PlayerJoinGameLobbyEvent;
import xyz.zeeraa.ezcore.module.gamelobby.map.GameLobbyMap;
import xyz.zeeraa.ezcore.module.gamelobby.map.GameLobbyMapData;
import xyz.zeeraa.ezcore.module.gamelobby.map.GameLobbyReader;
import xyz.zeeraa.ezcore.module.gamelobby.map.readers.DefaultLobbyMapReader;
import xyz.zeeraa.ezcore.module.gamelobby.mapselector.LobbyMapSelector;
import xyz.zeeraa.ezcore.module.gamelobby.mapselector.selectors.RandomLobbyMapSelector;
import xyz.zeeraa.ezcore.module.multiverse.MultiverseManager;

/**
 * This module creates a simple lobby where players wait for more players before
 * starting a game. This requires the {@link GameManager} and
 * {@link MultiverseManager} module and a game needs to be added
 * 
 * @author Zeeraa
 */
public class GameLobby extends EZModule implements Listener {
	private static GameLobby instance;

	private GameLobbyMap activeMap;

	private GameLobbyReader mapReader;
	private LobbyMapSelector mapSelector;

	private int taskId;

	private List<UUID> waitingPlayers;

	public static GameLobby getInstance() {
		return instance;
	}

	@Override
	public void onLoad() {
		GameLobby.instance = this;

		this.activeMap = null;

		this.mapReader = new DefaultLobbyMapReader();
		this.mapSelector = new RandomLobbyMapSelector();

		this.waitingPlayers = new ArrayList<UUID>();

		this.taskId = -1;
	}

	public GameLobby() {
		this.addDependency(MultiverseManager.class);
		this.addDependency(GameManager.class);

		this.activeMap = null;
	}

	@Override
	public String getName() {
		return "GameLobby";
	}

	@Override
	public void onEnable() {
		GameLobbyMapData map = mapSelector.getMapToUse();

		if (map == null) {
			EZLogger.fatal("The lobby map selector returned no map");
			this.disable();
			return;
		}

		try {
			this.activeMap = map.load();
		} catch (IOException e) {
			e.printStackTrace();
			EZLogger.fatal("Failed to load the map to use for game lobby");
			this.disable();
			return;
		}

		this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(EZCore.getInstance(), new Runnable() {
			@Override
			public void run() {
				if (hasActiveMap()) {
					for (Player player : getActiveMap().getWorld().getPlayers()) {
						if (player.getLocation().getY() < 0) {
							player.setFallDistance(0);
							player.teleport(activeMap.getSpawnLocation());
						}
					}
				}
			}
		}, 5L, 5L);
	}

	@Override
	public void onDisable() {
		if (taskId != -1) {
			Bukkit.getScheduler().cancelTask(taskId);
			taskId = -1;
		}
	}

	public List<UUID> getWaitingPlayers() {
		return waitingPlayers;
	}

	public GameLobbyReader getMapReader() {
		return mapReader;
	}

	public void setMapReader(GameLobbyReader mapReader) {
		this.mapReader = mapReader;
	}

	public GameLobbyMap getActiveMap() {
		return activeMap;
	}

	public boolean hasActiveMap() {
		return activeMap != null;
	}

	public LobbyMapSelector getMapSelector() {
		return mapSelector;
	}

	public void setMapSelector(LobbyMapSelector mapSelector) {
		this.mapSelector = mapSelector;
	}

	public boolean startGame() {
		try {
			for (UUID uuid : waitingPlayers) {
				Player player = Bukkit.getServer().getPlayer(uuid);

				if (player != null) {
					if (player.isOnline()) {
						GameManager.getInstance().getActiveGame().addPlayer(player);
					}
				}
			}

			GameManager.getInstance().start();

			return true;
		} catch (Exception e) {
			EZLogger.fatal("An exception occured while trying to start the game");
			Bukkit.getServer().broadcastMessage(ChatColor.RED + "An uncorrectable error occurred while trying to start the game");
			e.printStackTrace();
		}

		return false;
	}

	// ----- Listeners -----

	// -- Join and quit --
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (hasActiveMap()) {
			if (GameManager.getInstance().hasGame()) {
				if (!GameManager.getInstance().getActiveGame().hasStarted()) {
					if (!waitingPlayers.contains(e.getPlayer().getUniqueId())) {
						waitingPlayers.add(e.getPlayer().getUniqueId());
						e.getPlayer().teleport(activeMap.getSpawnLocation());

						e.getPlayer().getInventory().clear();

						e.getPlayer().setGameMode(GameMode.ADVENTURE);
						e.getPlayer().setMaxHealth(20);
						e.getPlayer().setHealth(20);
						e.getPlayer().setFoodLevel(20);

						Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinGameLobbyEvent(e.getPlayer()));
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (waitingPlayers.contains(p.getUniqueId())) {
			EZLogger.debug("Removing player " + e.getPlayer().getName() + " from the waiting players list");
			waitingPlayers.remove(p.getUniqueId());
		}
	}

	// -- World interactions --

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent e) {
		if (hasActiveMap()) {
			if (e.getBlock().getWorld() == activeMap.getWorld()) {
				if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (hasActiveMap()) {
			if (e.getBlock().getWorld() == activeMap.getWorld()) {
				if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (hasActiveMap()) {
			if (e.getDamager().getWorld() == activeMap.getWorld()) {
				if (e.getDamager() instanceof Player) {
					if (((Player) e.getDamager()).getGameMode() != GameMode.CREATIVE) {
						return;
					}
				}

				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent e) {
		if (hasActiveMap()) {
			if (e.getEntity() instanceof Player) {
				if (e.getEntity().getWorld() == activeMap.getWorld()) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (hasActiveMap()) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getPlayer().getWorld() == activeMap.getWorld()) {
					if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
		if (hasActiveMap()) {
			if (e.getPlayer().getWorld() == activeMap.getWorld()) {
				if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					e.setCancelled(true);
				}
			}
		}
	}

	// -- World --

	@EventHandler(priority = EventPriority.NORMAL)
	public void onWeatherChange(WeatherChangeEvent e) {
		if (hasActiveMap()) {
			if (e.getWorld() == activeMap.getWorld()) {
				if (e.toWeatherState()) {
					e.setCancelled(true);
				}
			}
		}
	}
}