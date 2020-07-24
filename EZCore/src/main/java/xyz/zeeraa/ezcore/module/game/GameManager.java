package xyz.zeeraa.ezcore.module.game;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import xyz.zeeraa.ezcore.EZCore;
import xyz.zeeraa.ezcore.callbacks.Callback;
import xyz.zeeraa.ezcore.module.EZModule;
import xyz.zeeraa.ezcore.module.game.map.GameMapData;
import xyz.zeeraa.ezcore.module.game.map.MapReader;
import xyz.zeeraa.ezcore.module.game.map.readers.DefaultMapReader;
import xyz.zeeraa.ezcore.module.game.mapselector.MapSelector;
import xyz.zeeraa.ezcore.module.game.mapselector.selectors.RandomMapSelector;
import xyz.zeeraa.ezcore.module.multiverse.MultiverseManager;

/**
 * Module used to manage games and maps
 * 
 * @author Zeeraa
 */
public class GameManager extends EZModule implements Listener {
	private static GameManager instance;
	private Game activeGame;

	private HashMap<UUID, EliminationTask> eliminationTasks;

	private MapSelector mapSelector;
	private MapReader mapReader;

	/**
	 * Get instance of {@link GameManager}
	 * 
	 * @return Instance
	 */
	public static GameManager getInstance() {
		return instance;
	}

	public GameManager() {
		instance = this;

		this.mapSelector = new RandomMapSelector();
		this.mapReader = new DefaultMapReader();

		this.addDependency(MultiverseManager.class);

		this.activeGame = null;
		this.eliminationTasks = new HashMap<UUID, EliminationTask>();
	}

	/**
	 * Get the map selector. The default map selector is {@link RandomMapSelector}.
	 * This is only used for games based on {@link MapGame}
	 * 
	 * @return The {@link MapSelector} to use
	 */
	public MapSelector getMapSelector() {
		return mapSelector;
	}

	/**
	 * Set the map selector to use. The default map selector is
	 * {@link RandomMapSelector}. This is only used for games based on
	 * {@link MapGame}
	 * 
	 * @param mapSelector The {@link MapSelector} to use
	 */
	public void setMapSelector(MapSelector mapSelector) {
		this.mapSelector = mapSelector;
	}

	/**
	 * Get the map reader. The default map reader is {@link DefaultMapReader}. This
	 * is only used for games based on {@link MapGame}
	 * 
	 * @return The {@link DefaultMapReader} to use
	 */
	public MapReader getMapReader() {
		return mapReader;
	}

	/**
	 * Try to load all JSON files from a directory as maps and add them to the
	 * active {@link MapSelector} defined by {@link GameManager#getMapSelector()}
	 * 
	 * @param directory      Directory to scan
	 * @param worldDirectory The directory containing the worlds
	 */
	public void addMaps(File directory, File worldDirectory) {
		mapReader.loadAll(directory, worldDirectory);
	}

	/**
	 * Read {@link GameMapData} from a {@link File} list and add it to the active
	 * {@link MapSelector} defined by {@link GameManager#getMapSelector()}
	 * 
	 * @param file           The {@link File} to read
	 * @param worldDirectory The directory containing the worlds
	 * @return <code>true</code> on success
	 */
	public boolean addMap(File mapFile, File worldDirectory) {
		return mapReader.readMap(mapFile, worldDirectory) != null;
	}

	@Override
	public String getName() {
		return "GameManager";
	}

	/**
	 * Get the {@link Game} that has been loaded
	 * 
	 * @return Instance of the active {@link Game}
	 */
	public Game getActiveGame() {
		return activeGame;
	}

	/**
	 * Check if a game is loaded
	 * 
	 * @return <code>true</code> if a game has been loaded
	 */
	public boolean hasGame() {
		return this.activeGame != null;
	}

	/**
	 * Load a {@link Game} and register listeners
	 * 
	 * @param game {@link Game} to be loaded
	 * @return <code>true</code> on success been added
	 */
	public boolean loadGame(Game game) {
		if (this.hasGame()) {
			return false;
		}

		game.onLoad();
		if (game instanceof Listener) {
			Bukkit.getPluginManager().registerEvents((Listener) game, EZCore.getInstance());
		}

		return true;
	}

	@Override
	public void onDisable() {
		for (UUID uuid : eliminationTasks.keySet()) {
			EliminationTask eliminationTask = eliminationTasks.get(uuid);
			if (eliminationTask != null) {
				eliminationTask.cancel();
			}
		}

		if (activeGame != null) {
			if (activeGame instanceof Listener) {
				HandlerList.unregisterAll((Listener) activeGame);
			}

			activeGame.onUnload();
		}
	}

	/**
	 * Start the game. If the game is a {@link MapGame} a map will also be selected
	 * by the {@link MapSelector} and will be loaded
	 * 
	 * @throws IOException          if the game is a {@link MapGame} and the map
	 *                              load function throws an {@link IOException}
	 * @throws NoMapsAddedException if the game is a {@link MapGame} and no maps has
	 * 
	 */
	public void start() throws IOException {
		if (activeGame instanceof MapGame) {
			if (mapSelector.getMaps().size() == 0) {
				throw new NoMapsAddedException("No maps has been loaded");
			}

			GameMapData map = mapSelector.getMapToUse();

			((MapGame) activeGame).loadMap(map);
		}

		activeGame.startGame();
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (hasGame()) {
			Player p = e.getPlayer();

			switch (activeGame.getPlayerQuitEliminationAction()) {
			case NONE:
				break;

			case INSTANT:
				activeGame.eliminatePlayer(p, null, PlayerEliminationReason.QUIT);
				break;

			case DELAYED:
				EliminationTask eliminationTask = new EliminationTask(p.getUniqueId(), p.getName(), activeGame.getPlayerEliminationDelay(), new Callback() {
					@Override
					public void execute() {
						getActiveGame().eliminatePlayer(p, null, PlayerEliminationReason.DID_NOT_RECONNECT);
					}
				});
				eliminationTasks.put(e.getPlayer().getUniqueId(), eliminationTask);
				break;

			default:
				break;
			}
		}
	}
}