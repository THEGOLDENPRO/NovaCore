package net.zeeraa.novacore.spigot.particles;

import java.util.Collection;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.zeeraa.novacore.spigot.abstraction.particle.NovaDustOptions;
import net.zeeraa.novacore.spigot.abstraction.particle.NovaParticleEffect;
import net.zeeraa.novacore.spigot.abstraction.particle.NovaParticleProvider;
import xyz.xenondevs.particle.ParticleEffect;

public class DefaultNovaParticleProvider extends NovaParticleProvider {
	@Override
	public void showColoredRedstoneParticle(Location location, NovaDustOptions options) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor());
	}

	@Override
	public void showColoredRedstoneParticle(Location location, NovaDustOptions options, Player receiver) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor(), receiver);
	}

	@Override
	public void showColoredRedstoneParticle(Location location, NovaDustOptions options, Collection<Player> receivers) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor(), receivers);
	}

	@Override
	public void showColoredRedstoneParticle(Location location, NovaDustOptions options, Predicate<Player> predicate) {
		ParticleEffect.REDSTONE.display(location, options.getAwtColor(), predicate);
	}

	@Override
	public void showParticle(Location location, NovaParticleEffect effect) {
		mapNovaParticleType(effect).display(location);
	}

	@Override
	public void showParticle(Location location, NovaParticleEffect effect, Player receiver) {
		mapNovaParticleType(effect).display(location, receiver);
	}

	@Override
	public void showParticle(Location location, NovaParticleEffect effect, Collection<Player> receivers) {
		mapNovaParticleType(effect).display(location, receivers);
	}

	@Override
	public void showParticle(Location location, NovaParticleEffect effect, Predicate<Player> predicate) {
		mapNovaParticleType(effect).display(location, predicate);
	}

	private ParticleEffect mapNovaParticleType(NovaParticleEffect effect) {
		switch (effect) {
		case CRIT_MAGIC:
			return ParticleEffect.CRIT_MAGIC;

		case FIREWORKS_SPARK:
			return ParticleEffect.FIREWORKS_SPARK;

		case HEART:
			return ParticleEffect.HEART;

		case REDSTONE:
			return ParticleEffect.REDSTONE;

		case SMOKE_LARGE:
			return ParticleEffect.SMOKE_LARGE;
		}
		return null;
	}
}