package net.zeeraa.novacore.spigot.mapdisplay;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
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
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import net.zeeraa.novacore.commons.NovaCommons;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.module.NovaModule;
import net.zeeraa.novacore.spigot.utils.LocationUtils;
import net.zeeraa.novacore.spigot.utils.XYLocation;

public class MapDisplayManager extends NovaModule implements Listener {
	public MapDisplayManager() {
		super("NovaCore.MapDisplayManager");
	}

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
			if (NovaCommons.isExtendedDebugging()) {
				Log.debug(getName(), "Scheduling load next tick");
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					if (NovaCommons.isExtendedDebugging()) {
						Log.debug(getName(), "Initial worlds to load: " + Bukkit.getServer().getWorlds());
					}

					for (World world : Bukkit.getServer().getWorlds()) {
						if (NovaCommons.isExtendedDebugging()) {
							Log.debug(getName(), "Loading initial world data for world " + world.getName());
						}
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
		return mapDisplays.stream().filter(display -> display.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
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

		MapDisplay display = new MapDisplay(frame.getWorld(), frameUuids, persistent, name, new ArrayList<>());

		mapDisplays.add(display);

		display.setupMaps();

		if (persistent && !worldDataSavingDisabled) {
			try {
				File dataFile = getDataFile(frame.getLocation().getWorld(), name);
				dataFile.getParentFile().mkdirs();

				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				// Name
				out.writeUTF(name);

				// Size
				out.writeInt(frameUuids.length);
				out.writeInt(frameUuids[0].length);

				// UUIDs
				for (int i = 0; i < frameUuids.length; i++) {
					for (int j = 0; j < frameUuids[i].length; j++) {
						out.writeUTF(frameUuids[i][j].toString());
					}
				}

				List<XYLocation> chunks = new ArrayList<>();
				List<ItemFrame> frameEntityList = display.getAllItemFrames();
				frameEntityList.forEach(frameToSave -> {
					XYLocation xyl = new XYLocation(frameToSave.getLocation().getChunk().getX(), frameToSave.getLocation().getChunk().getZ());

					if (!chunks.contains(xyl)) {
						chunks.add(xyl);
					}
				});
				// Amount of chunks
				out.writeInt(chunks.size());

				// Chunks
				chunks.forEach(chunk -> {
					out.writeInt(chunk.getX());
					out.writeInt(chunk.getY());
				});

				Files.write(out.toByteArray(), dataFile);
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

		File[] files = dataFolder.listFiles();

		int count = 0;
		for (File file : files) {
			if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("dat")) {
				count++;
			}
		}
		Log.debug("MapDisplayManager", world.getName() + " has " + count + " map display dat files");

		for (File file : files) {
			if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("dat")) {
				String name = "[UNKNOWN]";
				try {
					byte[] bytes = FileUtils.readFileToByteArray(file);

					ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

					// Name
					name = in.readUTF();

					// Size
					int x = in.readInt();
					int y = in.readInt();

					UUID[][] uuids = new UUID[x][y];

					// UUIDs
					for (int i = 0; i < x; i++) {
						for (int j = 0; j < y; j++) {
							uuids[i][j] = UUID.fromString(in.readUTF());
						}
					}

					// Amount of chunks
					int chunkCount = in.readInt();

					// Chunks
					List<XYLocation> chunksToLoad = new ArrayList<>();

					for (int i = 0; i < chunkCount; i++) {
						chunksToLoad.add(new XYLocation(in.readInt(), in.readInt()));
					}

					Log.trace("MapDisplayManager", "Data from file " + file.getAbsolutePath() + ": name:" + name + ". size: " + x + " " + y + ". chunk count: " + chunksToLoad.size());
					// Init display
					MapDisplay display = new MapDisplay(world, uuids, true, name, chunksToLoad);

					mapDisplays.add(display);

					display.setupMaps();
					display.tryLoadFromCache();
				} catch (IOException | MapDisplayNameAlreadyExistsException | MissingItemFrameException e) {
					if (e instanceof MapDisplayNameAlreadyExistsException) {
						Log.error(getName(), "Failed to load map display named " + name + " in world " + world + ". " + e.getClass().getName() + " " + e.getMessage());
						continue;
					}

					if (e instanceof MissingItemFrameException) {
						Log.error(getName(), "Failed to load map display named " + name + " in world " + world + ". " + e.getClass().getName() + " " + e.getMessage());
						// file.delete();
						continue;
					}

					Log.error(getName(), "Failed to load map display from file " + file.getAbsolutePath() + " in world " + world + ". " + e.getClass().getName() + " " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	public File getDataFolder(World world) {
		return new File(world.getWorldFolder().getPath() + File.separator + "novacore" + File.separator + "mapdisplays");
	}

	public File getDataFile(World world, String name) {
		return new File(getDataFolder(world).getPath() + File.separator + name + ".dat");
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
			mapDisplays.forEach(display -> {
				if (display.isEntityPartOfDisplay(e.getEntity())) {
					e.setCancelled(true);
				}
			});
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			mapDisplays.forEach(display -> {
				if (display.isEntityPartOfDisplay(e.getRightClicked())) {
					e.setCancelled(true);
				}
			});
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity().getType() == EntityType.ITEM_FRAME) {
			mapDisplays.forEach(display -> {
				if (display.isEntityPartOfDisplay(e.getEntity())) {
					e.setCancelled(true);
				}
			});
		}
	}
}