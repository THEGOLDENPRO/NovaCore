package net.zeeraa.novacore.commons.utils;

/**
 * Represents yaw, pitch and roll
 * 
 * @author Zeeraa
 */
public class Rotation {
	private float yaw;
	private float pitch;
	private float roll;

	public Rotation(float yaw, float pitch) {
		this(yaw, pitch, 0F);
	}

	public Rotation(float yaw, float pitch, float roll) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}
}