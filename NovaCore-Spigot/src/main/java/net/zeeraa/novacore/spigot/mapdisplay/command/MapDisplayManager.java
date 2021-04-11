package net.zeeraa.novacore.spigot.mapdisplay.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.mapdisplay.MapDisplay;
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
	
	public static MapDisplayManager getInstance() {
		return instance;
	}
	
	private List<MapDisplay> mapDisplays;
	
	@Override
	public void onLoad() {
		MapDisplayManager.instance = this;
		mapDisplays = new ArrayList<>();
	}
	
	@Override
	public void onEnable() throws Exception {
	}
	
	@Override
	public void onDisable() throws Exception {
	}
	
	public List<MapDisplay> getMapDisplays() {
		return mapDisplays;
	}
	

	public MapDisplay createMapDisplay(ItemFrame frame) {
		//Log.info(frame.getFacing().name());
		
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
				
				Log.trace("Scanning at X: " + col.getBlockX() + " Y: " + col.getBlockY() + " Z: " + col.getBlockZ());

				col.add(scanDirection);
				if (frameToStore == null) {
					if (width == -1) {
						width = colFrames.size();
						Log.debug("MapDisplayScanner", "Seems like the displays width is " + width);
					}
					break;
				} else {
					colFrames.add(frameToStore.getUniqueId());
				}
			}

			frames.add(colFrames);

			row.add(0, -1, 0);
		}
		
		for(List<UUID> col : frames) {
			String text = "";
			for(UUID uuid : col) {
				text += uuid.toString() + " ";
			}
			
			Log.info(text);
		}
		
		UUID[][] frameUuids = new UUID[frames.size()][width];
		
		for(int i = 0; i < frames.size(); i++) {
			List<UUID> col = frames.get(i);
			
			for(int j = 0; j < col.size(); j++) {
				frameUuids[i][j] = col.get(j);
			}
		}
		
		MapDisplay display = new MapDisplay(frame.getWorld(), frameUuids);
		
		mapDisplays.add(display);
		
		display.setupMaps();
		
		return display;
	}

	public ItemFrame getItemFrameAtLocation(Location location) {
		ItemFrame frame = null;
		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, 1, 1, 1);

		for (Entity entity : entities) {
			if (entity instanceof ItemFrame) {
				if (LocationUtils.isSameBlock(location, entity.getLocation())) {
					frame = (ItemFrame) entity;
					Log.debug("MapDisplayCommand", "Found ItemFrame " + frame.getUniqueId());
				}
			}
		}

		return frame;
	}

	@Override
	public String getName() {
		return "MapDisplayManager";
	}
}