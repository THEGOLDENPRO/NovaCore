package net.zeeraa.novacore.commons.utils;

import java.util.Random;

public class RandomProvidedNumber {
	public static final int randomProvidedInt(Random random, int... numbers) {
		return numbers[random.nextInt(numbers.length)];
	}

	public static final int randomProvidedInt(int... numbers) {
		return RandomProvidedNumber.randomProvidedInt(new Random(), numbers);
	}
	
	public static final double randomProvidedDouble(Random random, double... numbers) {
		return numbers[random.nextInt(numbers.length)];
	}

	public static final double randomProvidedDouble(double... numbers) {
		return RandomProvidedNumber.randomProvidedDouble(new Random(), numbers);
	}
	
	public static final float randomProvidedFloat(Random random, float... numbers) {
		return numbers[random.nextInt(numbers.length)];
	}

	public static final float randomProvidedFloat(float... numbers) {
		return RandomProvidedNumber.randomProvidedFloat(new Random(), numbers);
	}

	public static final long randomProvidedLong(Random random, long... numbers) {
		return numbers[random.nextInt(numbers.length)];
	}

	public static final long randomProvidedLong(long... numbers) {
		return RandomProvidedNumber.randomProvidedLong(new Random(), numbers);
	}


}