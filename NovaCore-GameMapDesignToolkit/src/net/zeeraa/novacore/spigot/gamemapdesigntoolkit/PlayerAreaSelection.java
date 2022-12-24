package net.zeeraa.novacore.spigot.gamemapdesigntoolkit;

import org.bukkit.Location;

public class PlayerAreaSelection {
	private Location position1;
	private Location position2;

	public PlayerAreaSelection(Location position1, Location position2) {
		this.position1 = position1;
		this.position2 = position2;
	}

	public Location getPosition1() {
		return position1;
	}

	public void setPosition1(Location position1) {
		this.position1 = position1;
	}

	public Location getPosition2() {
		return position2;
	}

	public void setPosition2(Location position2) {
		this.position2 = position2;
	}
}