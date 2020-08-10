package xyz.zeeraa.novacore.utils;

/**
 * Functions to generate text
 * 
 * @author Zeeraa
 */
public class TextUtils {
	/**
	 * Heart in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2764
	 */
	public static final String ICON_HEARTH = Character.toString((char) 2764);

	/**
	 * The Radioactive Sign in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2622
	 */
	public static final String ICON_RADIOACTIVE = Character.toString((char) 9762);

	/**
	 * Quarter Note in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2622
	 */
	public static final String ICON_NOTE = Character.toString((char) 2669);

	/**
	 * The Hammer and Pick in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2692
	 */
	public static final String ICON_TOOLS = Character.toString((char) 9762);

	/**
	 * Crossed Swords in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2694
	 */
	public static final String ICON_SWORDS = Character.toString((char) 9762);

	/**
	 * Warning Sign in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+26A0
	 */
	public static final String ICON_WARNING = Character.toString((char) 9888);

	/**
	 * Black star in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2605
	 */
	public static final String ICON_BLACK_STAR = Character.toString((char) 2605);

	/**
	 * White star in unicode
	 * <p>
	 * https://www.compart.com/en/unicode/U+2606
	 */
	public static final String ICON_WHITE_STAR = Character.toString((char) 2606);

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