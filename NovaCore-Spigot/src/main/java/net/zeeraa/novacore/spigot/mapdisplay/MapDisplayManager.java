package net.zeeraa.novacore.spigot.mapdisplay;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.commons.utils.JSONFileUtils;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.utils.LocationUtils;

public class MapDisplayManager extends NovaModule implements Listener {
	public static final HashMap<BlockFace, Vector> SCAN_DIRECTIONS = new HashMap<>();
	static {
		SCAN_DIRECTIONS.put(BlockFace.SOUTH, new Vector(1, 0, 0));
		SCAN_DIRECTIONS.put(BlockFace.EAST, new Vector(0, 0, -1));
		SCAN_DIRECTIONS.put(BlockFace.NORTH, new Vector(-1, 0, 0));
		SCAN_DIRECTIONS.put(BlockFace.WEST, new Vector(0, 0, 1));
	}

	private static MapDisplayManager instance;

	private boolean worldDataLoadingEnabled;
	private boolean worldDataSavingDisabled;

	public static MapDisplayManager getInstance() {
		return instance;
	}

	public void setWorldDataLoadingEnabled(boolean worldDataLoadingEnabled) {
		this.worldDataLoadingEnabled = worldDataLoadingEnabled;
	}

	public boolean isWorldDataLoadingEnabled() {
		return worldDataLoadingEnabled;
	}
	
	public void setWorldDataSavingDisabled(boolean worldDataSavingDisabled) {
		this.worldDataSavingDisabled = worldDataSavingDisabled;
	}
	
	public boolean isWorldDataSavingDisabled() {
		return worldDataSavingDisabled;
	}

	private List<MapDisplay> mapDisplays;

	@Override
	public void onLoad() {
		MapDisplayManager.instance = this;
		worldDataLoadingEnabled = true;
		worldDataSavingDisabled = false;
		mapDisplays = new ArrayList<>();
	}

	@Override
	public void onEnable() throws Exception {
		if (worldDataLoadingEnabled) {
			new BukkitRunnable() {
				@Override
				public void run() {
					for (World world : Bukkit.getServer().getWorlds()) {
						readAllFromWorld(world);
					}
				}
			}.runTaskLater(NovaCore.getInstance(), 1L);
		}
	}

	@Override
	public void onDisable() throws Exception {
	}

	public List<MapDisplay> getMapDisplays() {
		return mapDisplays;
	}

	public MapDisplay getMapDisplay(String name) {
		for (MapDisplay display : mapDisplays) {
			if (display.getName().equalsIgnoreCase(name)) {
				return display;
			}
		}
		return null;
	}

	public boolean hasMapDisplay(String name) {
		return getMapDisplay(name) != null;
	}

	public MapDisplay createMapDisplay(ItemFrame frame, boolean persistent, String name) {
		if (hasMapDisplay(name)) {
			throw new MapDisplayNameAlreadyExistsException("A map display with the name " + name + " already exits");
		}

		List<List<UUID>> frames = new ArrayList<List<UUID>>();

		int width = -1;

		Vector scanDirection = new Vector(1, 0, 0);

		if (SCAN_DIRECTIONS.containsKey(frame.getFacing())) {
			scanDirection = SCAN_DIRECTIONS.get(frame.getFacing());
		}

		Location row = frame.getLocation();

		while (true) {
			if (getItemFrameAtLocation(row) == null) {
				break;
			}

			Location col = row.clone();
			List<UUID> colFrames = new ArrayList<UUID>();

			int sx = 0;
			while (true) {
				sx++;

				if (width != -1 && sx > width) {
					break;
				}

				ItemFrame frameToStore = getItemFrameAtLocation(col);

				// Log.trace("Scanning at X: " + col.getBlockX() + " Y: " + col.getBlockY() + "
				// Z: " + col.getBlockZ());

				col.add(scanDirection);
				if (frameToStore == null) {
					if (width == -1) {
						width = colFrames.size();
						Log.trace("MapDisplayScanner", "Displays width is " + width);
					}
					break;
				} else {
					colFrames.add(frameToStore.getUniqueId());
				}
			}

			frames.add(colFrames);

			row.add(0, -1, 0);
		}

		UUID[][] frameUuids = new UUID[frames.size()][width];

		for (int i = 0; i < frames.size(); i++) {
			List<UUID> col = frames.get(i);

			for (int j = 0; j < col.size(); j++) {
				frameUuids[i][j] = col.get(j);
			}
		}

		MapDisplay display = new MapDisplay(frame.getWorld(), frameUuids, persistent, name);

		mapDisplays.add(display);

		display.setupMaps();

		if (persistent && !worldDataSavingDisabled) {
			try {
				File dataFile = getDataFile(frame.getLocation().getWorld(), name);
				dataFile.getParentFile().mkdirs();

				JSONObject json = new JSONObject();

				json.put("name", name);

				JSONArray rowJson = new JSONArray();

				for (int i = 0; i < frameUuids.length; i++) {
					JSONArray col = new JSONArray();
					for (int j = 0; j < frameUuids[i].length; j++) {
						col.put(frameUuids[i][j].toString());
					}
					rowJson.put(col);
				}

				json.put("row", rowJson);

				JSONFileUtils.saveJson(dataFile, json);
			} catch (Exception e) {
				Log.error("Failed to save persistent data for MapDisplay " + name + ". " + e.getClass().getName() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

		return display;
	}

	public void readAllFromWorld(World world) {
		File dataFolder = getDataFolder(world);

		if (!dataFolder.exists()) {
			Log.debug(getName(), "No data folder found for world " + world.getName());
			return;
		} else {
			Log.debug(getName(), "Found data folder for world " + world.getName());
		}

		for (File file : dataFolder.listFiles()) {
			if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
				String name = "";
				try {
					JSONObject json = JSONFileUtils.readJSONObjectFromFile(file);

					name = json.getString("name");

					Log.trace(getName(), "Found display named " + name + " in world " + world.getName());

					JSONArray row = json.getJSONArray("row");

					UUID[][] uuids = new UUID[row.length()][row.getJSONArray(0).length()];

					for (int i = 0; i < row.length(); i++) {
						JSONArray col = row.getJSONArray(i);
						for (int j = 0; j < col.length(); j++) {
							uuids[i][j] = UUID.fromString(col.getString(j));
						}
					}

					MapDisplay display = new MapDisplay(world, uuids, true, name);

					mapDisplays.add(display);

					display.setupMaps();
					display.tryLoadFromCache();
				} catch (JSONException | IOException | MapDisplayNameAlreadyExistsException | MissingItemFrameException e) {
					if (e instanceof MapDisplayNameAlreadyExistsException) {
						Log.error(getName(), "Failed to load map display named " + name + " in world " + world + ". " + e.getClass().getName() + " " + e.getMessage());
						continue;
					}

					if (e instanceof MissingItemFrameException) {
						Log.error(getName(), "Failed to load map display named " + name + " in world " + world + ". " + e.getClass().getName() + " " + e.getMessage());
						file.delete();
						continue;
					}
					e.printStackTrace();
				}
			}
		}
	}

	public File getDataFolder(World world) {
		return new File(world.getWorldFolder().getPath() + File.separator + "novacore" + File.separator + "mapdisplays");
	}

	public File getDataFile(World world, String name) {
		return new File(getDataFolder(world).getPath() + File.separator + name + ".json");
	}

	public ItemFrame getItemFrameAtLocation(Location location) {
		ItemFrame frame = null;
		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 1, 1, 1);

		for (Entity entity : entities) {
			if (entity instanceof ItemFrame) {
				if (LocationUtils.isSameBlock(location, entity.getLocation())) {
					frame = (ItemFrame) entity;
					// Log.debug("MapDisplayCommand", "Found ItemFrame " + frame.getUniqueId());
				}
			}
		}

		return frame;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onWorldLoad(WorldLoadEvent e) {
		if (worldDataLoadingEnabled) {
			new BukkitRunnable() {
				@Override
				public void run() {
					readAllFromWorld(e.getWorld());
				}
			}.runTaskLater(NovaCore.getInstance(), 1L);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onHangingBreak(HangingBreakEvent e) {
		if (e.getEntity().getType() == EntityType.ITEM_FRAME) {
			for (MapDisplay display : mapDisplays) {
				if (display.isEntityPartOfDisplay(e.getEntity())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			for (MapDisplay display : mapDisplays) {
				if (display.isEntityPartOfDisplay(e.getRightClicked())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity().getType() == EntityType.ITEM_FRAME) {
			for (MapDisplay display : mapDisplays) {
				if (display.isEntityPartOfDisplay(e.getEntity())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "MapDisplayManager";
	}
}