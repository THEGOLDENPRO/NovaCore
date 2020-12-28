package net.zeeraa.novacore.spigot.utils;

import java.util.Random;

import org.bukkit.util.Vector;

import net.zeeraa.novacore.commons.utils.RandomGenerator;

public class VectorArea {
	private Vector position1;
	private Vector position2;

	public VectorArea(Vector position1, Vector position2) {
		this.position1 = new Vector((position1.getX() < position2.getX() ? position1.getX() : position2.getX()), (position1.getY() < position2.getY() ? position1.getY() : position2.getY()), (position1.getZ() < position2.getZ() ? position1.getZ() : position2.getZ()));
		this.position1 = new Vector((position1.getX() > position2.getX() ? position1.getX() : position2.getX()), (position1.getY() > position2.getY() ? position1.getY() : position2.getY()), (position1.getZ() > position2.getZ() ? position1.getZ() : position2.getZ()));
	}

	public VectorArea(int x1, int y1, int z1, int x2, int y2, int z2) {
		this(new Vector(x1, y1, z1), new Vector(x2, y2, z2));
	}

	public VectorArea(double x1, double y1, double z1, double x2, double y2, double z2) {
		this(new Vector(x1, y1, z1), new Vector(x2, y2, z2));
	}

	public VectorArea(float x1, float y1, float z1, float x2, float y2, float z2) {
		this(new Vector(x1, y1, z1), new Vector(x2, y2, z2));
	}

	/**
	 * Get the position with the lowest x, y and z values
	 * 
	 * @return The {@link Vector} with the lowest values
	 */
	public Vector getPosition1() {
		return position1;
	}

	/**
	 * Get the position with the highest x, y and z values
	 * 
	 * @return The {@link Vector} with the highest values
	 */
	public Vector getPosition2() {
		return position2;
	}

	public Vector getRandomVectorWithin() {
		return this.getRandomVectorWithin(new Random());
	}

	public Vector getRandomVectorWithin(Random random) {
		double x = RandomGenerator.generateDouble(position1.getX(), position2.getX(), random);
		double y = RandomGenerator.generateDouble(position1.getY(), position2.getY(), random);
		double z = RandomGenerator.generateDouble(position1.getZ(), position2.getZ(), random);

		return new Vector(x, y, z);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " lower: " + position1.toString() + " upper: " + position2.toString();
	}
}