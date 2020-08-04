package xyz.zeeraa.novacore.utils;

public class TextUtils {
	/**
	 * Get the ordinal name of a number<br>
	 * Code from <a href=
	 * "https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal-name">https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal-name</a>
	 * 
	 * @param i Number
	 * @return Ordinal name for the number
	 */
	public static String ordinal(int i) {
		String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		switch (i % 100) {
		case 11:
		case 12:
		case 13:
			return i + "th";
		default:
			return i + sufixes[i % 10];

		}
	}
}