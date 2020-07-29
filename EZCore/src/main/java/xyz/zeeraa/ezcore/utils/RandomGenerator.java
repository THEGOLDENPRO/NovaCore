package xyz.zeeraa.ezcore.utils;

import java.util.Random;

public class RandomGenerator {
	public static int generate(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}