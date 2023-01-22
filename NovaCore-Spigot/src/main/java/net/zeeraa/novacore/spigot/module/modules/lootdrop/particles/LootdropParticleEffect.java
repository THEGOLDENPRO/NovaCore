package net.zeeraa.novacore.spigot.module.modules.lootdrop.particles;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Effect from
 * https://github.com/iSach/UltraCosmetics/blob/master/core/src/main/java/be/isach/ultracosmetics/cosmetics/particleeffects/ParticleEffectGreenSparks.java
 * 
 * @author Zeeraa
 */
public class LootdropParticleEffect {
	private final Location location;
	private final LootdropParticleEffectProvider particleProvider;
	
	private boolean up;
	private float height;
	private int step;


	public LootdropParticleEffect(Location location, LootdropParticleEffectProvider particleProvider) {
		this.location = location;
		this.particleProvider = particleProvider;
		
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

		particleProvider.display(location.clone().add(v).add(0, height, 0));
		
		step += 4;
	}

	public Location getLocation() {
		return location;
	}
}