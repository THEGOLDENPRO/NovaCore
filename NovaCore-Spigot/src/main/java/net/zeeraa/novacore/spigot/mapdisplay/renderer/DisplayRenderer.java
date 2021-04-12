package net.zeeraa.novacore.spigot.mapdisplay.renderer;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

import net.zeeraa.novacore.spigot.NovaCore;

public class DisplayRenderer extends MapRenderer {
	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public void removePlayerFromCache(Player player) {
		hasRendered.remove(player.getUniqueId());	
	}
	
	public void clearPlayerCache() {
		hasRendered.clear();
	}
	
	private List<UUID> hasRendered; 
	
	public DisplayRenderer() {
		hasRendered = new ArrayList<UUID>();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MapView map, MapCanvas canvas, Player player) {
		if(hasRendered.contains(player.getUniqueId())) {
			return;
		}
		
		hasRendered.add(player.getUniqueId());
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if (image == null) {
					for (int x = 0; x < 128; x++) {
						for (int y = 0; y < 128; y++) {
							canvas.setPixel(x, y, MapPalette.WHITE);
						}
					}
				} else {
					canvas.drawImage(0, 0, image);
				}				
			}
		}.runTaskAsynchronously(NovaCore.getInstance());
	}
}