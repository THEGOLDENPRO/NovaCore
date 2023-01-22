package net.zeeraa.novacore.spigot.abstraction.particle;

import java.util.Collection;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class NovaParticleProvider {
	public void showRedstoneParticle(Location location, java.awt.Color color) {
		this.showRedstoneParticle(location, new NovaDustOptions(color, 1));
	}

	public void showRedstoneParticle(Location location, java.awt.Color color, Player receiver) {
		this.showRedstoneParticle(location, new NovaDustOptions(color, 1), receiver);
	}

	public void showRedstoneParticle(Location location, java.awt.Color color, Collection<Player> receivers) {
		this.showRedstoneParticle(location, new NovaDustOptions(color, 1), receivers);
	}

	public void showRedstoneParticle(Location location, java.awt.Color color, Predicate<Player> predicate) {
		this.showRedstoneParticle(location, new NovaDustOptions(color, 1), predicate);
	}

	public abstract void showRedstoneParticle(Location location, NovaDustOptions options);

	public abstract void showRedstoneParticle(Location location, NovaDustOptions options, Player receiver);

	public abstract void showRedstoneParticle(Location location, NovaDustOptions options, Collection<Player> receivers);

	public abstract void showRedstoneParticle(Location location, NovaDustOptions options, Predicate<Player> predicate);
}