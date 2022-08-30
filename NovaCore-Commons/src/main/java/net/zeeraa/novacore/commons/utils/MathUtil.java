package net.zeeraa.novacore.commons.utils;

public class MathUtil {
	public static float lerp(float a, float b, float f) {
		return a + f * (b - a);
	}
	
	public static double lerp(double a, double b, double f) {
		return a + f * (b - a);
	}
}