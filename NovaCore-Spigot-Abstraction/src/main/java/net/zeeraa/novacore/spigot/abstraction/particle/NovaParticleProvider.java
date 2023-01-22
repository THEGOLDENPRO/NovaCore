package net.zeeraa.novacore.spigot.abstraction.particle;

import java.util.Collection;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class NovaParticleProvider {
	public abstract void showRedstoneParticle(Location location, NovaDustOptions options);

	public abstract void showRedstoneParticle(Location location, NovaDustOptions options, Player receiver);

	public abstract void showRedstoneParticle(Location location, NovaDustOptions options, Collection<Player> receivers);

	public abstract void showRedstoneParticle(Location location, NovaDustOptions options, Predicate<Player> predicate);
}