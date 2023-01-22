package net.zeeraa.novacore.spigot.particles;

import java.util.Collection;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.abstraction.particle.NovaDustOptions;
import net.zeeraa.novacore.spigot.abstraction.particle.NovaParticleProvider;
import xyz.xenondevs.particle.ParticleEffect;

public class DefaultNovaParticleProvider extends NovaParticleProvider {
	@Override
	public void showRedstoneParticle(Location location, NovaDustOptions options) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor());
	}

	@Override
	public void showRedstoneParticle(Location location, NovaDustOptions options, Player receiver) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor(), receiver);
	}

	@Override
	public void showRedstoneParticle(Location location, NovaDustOptions options, Collection<Player> receivers) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor(), receivers);
	}

	@Override
	public void showRedstoneParticle(Location location, NovaDustOptions options, Predicate<Player> predicate) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor(), predicate);
	}
}