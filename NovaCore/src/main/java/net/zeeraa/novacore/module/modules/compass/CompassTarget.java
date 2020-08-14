package net.zeeraa.novacore.module.modules.compass;

import org.bukkit.Location;

public class CompassTarget {
	private Location targetLocation;
	private String trackingMessage;
	
	public CompassTarget(Location targetLocation, String trackingMessage) {
		this.targetLocation = targetLocation;
		this.trackingMessage = trackingMessage;
	}
	
	public Location getTargetLocation() {
		return targetLocation;
	}
	
	public String getTrackingMessage() {
		return trackingMessage;
	}
}