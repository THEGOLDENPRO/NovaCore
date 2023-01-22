package net.zeeraa.novacore.spigot.module.modules.lootdrop;

import org.bukkit.Location;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.particle.NovaParticleEffect;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.particles.LootdropParticleEffectProvider;

public class DefaultFireworkSparkLootdropEffectProvider implements LootdropParticleEffectProvider {
	@Override
	public void display(Location location) {
		NovaCore.getInstance().getNovaParticleProvider().showParticle(location, NovaParticleEffect.FIREWORKS_SPARK);
	}
}