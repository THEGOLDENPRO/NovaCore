package net.zeeraa.novacore.spigot.mapdisplay;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

import net.coobird.thumbnailator.Thumbnails;
import net.zeeraa.novacore.commons.log.Log;
import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.mapdisplay.renderer.DisplayRenderer;
import world.WorldUtils;

public class MapDisplay {
	private World world;

	private Dimension dimensions;
	private Dimension resolution;

	private UUID[][] itemFrameUuids;
	private ItemFrame[][] itemFrames;

	private DisplayRenderer[][] renderers;

	public void setImage(BufferedImage image) throws Exception {
		if (image == null) {
			for (int i = 0; i < itemFrames.length; i++) {
				for (int j = 0; j < itemFrames[i].length; j++) {
					if (renderers[i][j] == null) {
						continue;
					}
					renderers[i][j].setImage(null);
				}
			}
			return;
		}

		Dimension imageDimension = getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), getResolution());
		BufferedImage resized = resizeImage(image, imageDimension.width, imageDimension.height);

		BufferedImage realImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_RGB);

		drawImageOnImage(resized, realImage, 0, 0);

		for (int y = 0; y < itemFrames.length; y++) {
			for (int x = 0; x < itemFrames[y].length; x++) {
				if (renderers[y][x] == null) {
					continue;
				}
				Image part = cropImage(realImage, new Rectangle(x * 128, y * 128, 128, 128));

				renderers[y][x].setImage(part);
			}
		}

	}

	/**
	 * Create a MapDisplay
	 * 
	 * @param world          The {@link World} that tme display is located in
	 * @param itemFrameUuids Array of item frames
	 */
	public MapDisplay(World world, UUID[][] itemFrameUuids) {
		this.world = world;
		this.itemFrameUuids = itemFrameUuids;

		dimensions = new Dimension(itemFrameUuids[0].length, itemFrameUuids.length);
		resolution = new Dimension((int) dimensions.getWidth() * 128, (int) dimensions.getHeight() * 128);

		itemFrames = new ItemFrame[itemFrameUuids.length][itemFrameUuids[0].length];

		for (int i = 0; i < itemFrameUuids.length; i++) {
			for (int j = 0; j < itemFrameUuids[i].length; j++) {
				ItemFrame itemFrame = (ItemFrame) WorldUtils.getEntityByUUID(world, itemFrameUuids[i][j]);

				itemFrames[i][j] = itemFrame;
			}
		}

		renderers = new DisplayRenderer[itemFrameUuids.length][itemFrameUuids[0].length];

		Log.debug("MapDisplay", "Map diplay initiated with a resolution of " + resolution.getWidth() + "x" + resolution.getHeight() + ". grid size is " + dimensions.getWidth() + "x" + dimensions.getHeight());
	}

	public UUID[][] getItemFrameUuids() {
		return itemFrameUuids;
	}

	public ItemFrame[][] getItemFrames() {
		return itemFrames;
	}

	public World getWorld() {
		return world;
	}

	public Dimension getDimensions() {
		return dimensions;
	}

	public Dimension getResolution() {
		return resolution;
	}

	public ItemFrame getItemFrame(int x, int y) {
		return itemFrames[x][y];
	}

	public void setupMaps() {
		for (int i = 0; i < itemFrames.length; i++) {
			for (int j = 0; j < itemFrames[i].length; j++) {
				ItemFrame frame = itemFrames[i][j];

				ItemStack item = new ItemStack(Material.MAP);

				MapView view = Bukkit.createMap(world);

				view.setScale(Scale.FARTHEST);

				for (MapRenderer renderer : view.getRenderers()) {
					view.removeRenderer(renderer);
				}

				DisplayRenderer renderer = new DisplayRenderer();

				view.addRenderer(renderer);

				renderers[i][j] = renderer;

				NovaCore.getInstance().getVersionIndependentUtils().attachMapView(item, view);

				frame.setItem(item);
			}
		}
	}

	public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Thumbnails.of(originalImage).size(targetWidth, targetHeight).outputFormat("JPEG").outputQuality(1).toOutputStream(outputStream);
		byte[] data = outputStream.toByteArray();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
		return ImageIO.read(inputStream);
	}

	public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
		BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
		return dest;
	}

	/*public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
		int original_width = imgSize.width;
		int original_height = imgSize.height;
		int bound_width = boundary.width;
		int bound_height = boundary.height;
		int new_width = original_width;
		int new_height = original_height;

		// first check if we need to scale width
		if (original_width > bound_width) {
			// scale width to fit
			new_width = bound_width;
			// scale height to maintain aspect ratio
			new_height = (new_width * original_height) / original_width;
		}

		// then check if we need to scale even with the new height
		if (new_height > bound_height) {
			// scale height to fit instead
			new_height = bound_height;
			// scale width to maintain aspect ratio
			new_width = (new_height * original_width) / original_height;
		}

		return new Dimension(new_width, new_height);
	}*/
	
	public static Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {

	    double widthRatio = boundary.getWidth() / imageSize.getWidth();
	    double heightRatio = boundary.getHeight() / imageSize.getHeight();
	    double ratio = Math.min(widthRatio, heightRatio);

	    return new Dimension((int) (imageSize.width  * ratio),
	                         (int) (imageSize.height * ratio));
	}

	public static void drawImageOnImage(BufferedImage smaller, BufferedImage larger, int x, int y) {
		larger.getGraphics().drawImage(smaller, x, y, null);
	}
}