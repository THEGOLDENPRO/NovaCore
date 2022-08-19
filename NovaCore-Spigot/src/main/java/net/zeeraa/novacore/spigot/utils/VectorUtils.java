package net.zeeraa.novacore.spigot.utils;

import org.bukkit.util.Vector;
import org.json.JSONObject;

/**
 * Utilities for {@link Vector}s
 * 
 * @author Zeeraa
 */
public class VectorUtils {
	/**
	 * Convert a {@link Vector} to a {@link JSONObject}.
	 * <p>
	 * The output json should look like this
	 * <code>{"x:" 0.0, "y:" 0.0, "z:" 0.0}</code>
	 * 
	 * @param vector The {@link Vector} to convert
	 * @return A {@link JSONObject} with the x, y and z values of the vector
	 */
	public static JSONObject toJSONObject(Vector vector) {
		JSONObject json = new JSONObject();

		json.put("x", vector.getX());
		json.put("y", vector.getY());
		json.put("z", vector.getZ());

		return json;
	}

	/**
	 * Convert a {@link JSONObject} to a {@link Vector}
	 * <p>
	 * The input json should look like this
	 * <code>{"x:" 0.0, "y:" 0.0, "z:" 0.0}</code>
	 * 
	 * @param json The {@link JSONObject} with the x, y and z values of the vector
	 * @return The {@link Vector}
	 */
	public static Vector fromJSONObject(JSONObject json) {
		double x = json.getDouble("x");
		double y = json.getDouble("y");
		double z = json.getDouble("z");

		return new Vector(x, y, z);
	}

	/**
	 * @return A vector with all values set to 0
	 */
	public static Vector getEmptyVector() {
		return new Vector(0D, 0D, 0D);
	}
}