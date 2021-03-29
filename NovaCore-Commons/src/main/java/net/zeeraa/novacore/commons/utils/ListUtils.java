package net.zeeraa.novacore.commons.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	/**
	 * Create a new {@link ArrayList} and add the content of the provided
	 * {@link List} to it
	 * 
	 * @param <T>  The data type
	 * @param list The {@link List} to be cloned
	 * @return {@link ArrayList} with all the elements of the provided list
	 */
	public static <T> List<T> cloneList(List<T> list) {
		List<T> result = new ArrayList<T>();

		for (T t : list) {
			result.add(t);
		}

		return result;
	}
}