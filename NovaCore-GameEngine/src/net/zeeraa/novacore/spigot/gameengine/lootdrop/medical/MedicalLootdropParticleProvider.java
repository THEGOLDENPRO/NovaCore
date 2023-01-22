package net.zeeraa.novacore.spigot.gameengine.lootdrop.medical;

import org.bukkit.Location;

import net.zeeraa.novacore.spigot.NovaCore;
import net.zeeraa.novacore.spigot.abstraction.particle.NovaParticleEffect;
import net.zeeraa.novacore.spigot.module.modules.lootdrop.particles.LootdropParticleEffectProvider;

public class MedicalLootdropParticleProvider implements LootdropParticleEffectProvider {
	@Override
	public void display(Location location) {
		NovaCore.getInstance().getNovaParticleProvider().showParticle(location, NovaParticleEffect.HEART);
	}
}