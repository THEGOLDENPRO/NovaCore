package net.zeeraa.novacore.spigot.utils;

import org.json.JSONObject;

public class XYLocation {
	private int x;
	private int y;

	public XYLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public XYLocation(JSONObject json) {
		this(json.getInt("x"), json.getInt("y"));
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();

		json.put("x", x);
		json.put("y", y);

		return json;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static XYLocation fromJSON(JSONObject json) {
		return new XYLocation(json);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof XYLocation) {
			XYLocation location2 = (XYLocation) obj;

			return this.x == location2.x && this.y == location2.y;
		}

		return false;
	}

	@Override
	public String toString() {
		return "X: " + y + " Y: " + y;
	}
}