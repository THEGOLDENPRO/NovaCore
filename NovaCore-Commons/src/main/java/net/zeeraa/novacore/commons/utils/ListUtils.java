package net.zeeraa.novacore.commons.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

		result.addAll(list);

		return result;
	}

	/**
	 * Create a list with the provided elements in it
	 * 
	 * @param <T>      The data type
	 * @param elements The elements to add
	 * @return {@link List} aith the provided elements
	 */
	public static <T> List<T> createList(@SuppressWarnings("unchecked") T... elements) {
		List<T> result = new ArrayList<>();

		Collections.addAll(result, elements);

		return result;
	}

	public static <T> List<T> shuffleWithRandom(List<T> list, Random random) {
		List<T> elements = new ArrayList<>();

		while (list.size() > 0) {
			elements.add(list.remove(0));
		}

		list.clear();

		while (elements.size() > 0) {
			list.add(elements.remove(random.nextInt(elements.size())));
		}

		return list;
	}

	public static <T> List<T> removeDuplicates(Collection<T> list) {
		List<T> newList = new ArrayList<>();
		list.forEach(t -> {
			if (!newList.contains(t)) {
				newList.add(t);
			}
		});
		return newList;
	}

	public static <T> List<T> removeDuplicates(Collection<T> list, BiPredicate<T, T> predicate) {
		if (predicate == null) {
			return removeDuplicates(list);
		}
		List<T> newList = new ArrayList<>(list);
		List<T> loopList = new ArrayList<>(list);
		while (!loopList.isEmpty()) {
			List<T> placeholderList = new ArrayList<>(loopList);
			placeholderList.remove(loopList.get(0));

			for (T val1 : placeholderList) {
				if (predicate.test(loopList.get(0), val1)) {
				newList.remove(val1);
				}

			}
			loopList.remove(0);
		}
		return newList;
	}

}