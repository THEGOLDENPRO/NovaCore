package net.zeeraa.novacore.spigot.module.modules.lootdrop.particles;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.darkblade12.particledemo.particle.ColoredParticle;
import com.darkblade12.particledemo.particle.ParticleEffect;

/**
 * Effect from
 * https://github.com/iSach/UltraCosmetics/blob/master/core/src/main/java/be/isach/ultracosmetics/cosmetics/particleeffects/ParticleEffectGreenSparks.java
 * 
 * @author Zeeraa
 */
public class LootdropParticleEffect {
	private Location location;

	private int r;
	private int g;
	private int b;

	private ColoredParticle particle;

	boolean up;
	float height;
	int step;

	public LootdropParticleEffect(Location location) {

		this(location, ColoredParticle.REDSTONE, 255, 255, 255);
	}

	public LootdropParticleEffect(Location location, ColoredParticle particle, int r, int g, int b) {
		this.location = location;

		this.r = r;
		this.g = g;
		this.b = b;

		this.particle = particle;

		this.up = false;
		this.height = 0;
		this.step = 0;
	}

	public void update() {
		if (up) {
			if (height < 2)
				height += 0.05;
			else
				up = false;
		} else {
			if (height > 0)
				height -= 0.05;
			else
				up = true;
		}
		double inc = (2 * Math.PI) / 100;
		double angle = step * inc;
		Vector v = new Vector();
		v.setX(Math.cos(angle) * 1.1);
		v.setZ(Math.sin(angle) * 1.1);
		ParticleEffect.FIREWORKS_SPARK.display(new Vector(0, 0, 0), 0, location.clone().add(v).add(0, height, 0), 50);
		step += 4;
	}

	public Location getLocation() {
		return location;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public ColoredParticle getParticle() {
		return particle;
	}
}