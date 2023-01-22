package net.zeeraa.novacore.spigot.abstraction.particle;

import org.bukkit.Color;

/**
 * NovaCore version of
 * https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.DustOptions.html
 * 
 * @author Zeeraa
 */
public class NovaDustOptions {
	private Color color;
	private java.awt.Color awtColor;
	private float size;

	public NovaDustOptions(java.awt.Color color, float size) {
		this.color = Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
		this.awtColor = color;
		this.size = size;
	}

	public NovaDustOptions(Color color, float size) {
		this.color = color;
		this.awtColor = new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
		this.size = size;
	}

	public java.awt.Color getAwtColor() {
		return awtColor;
	}
	
	public Color getColor() {
		return color;
	}

	public float getSize() {
		return size;
	}
}