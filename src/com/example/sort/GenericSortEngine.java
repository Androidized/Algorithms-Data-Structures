package com.example.sort;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class GenericSortEngine<T extends Comparable<? super T>> {
	public enum SortType {
		ASCENDING,
		DESCENDING
	}

	class ArrayIterator {
		T[] array;
		int pointer;

		ArrayIterator(T[] array) throws NullPointerException {
			if (array == null) throw new NullPointerException();
			this.array = array;
			this.pointer = 0;
		}

		ArrayIterator(T[] array, int pointer)
				throws NullPointerException, ArrayIndexOutOfBoundsException {
			if (array == null)
				throw new NullPointerException();
			if (pointer < 0 || pointer > array.length - 1)
				throw new ArrayIndexOutOfBoundsException();
			this.array = array;
			this.pointer = pointer;
		}

		public T setPointer(int pointer) throws ArrayIndexOutOfBoundsException {
			if (pointer < 0 || pointer > this.array.length - 1) {
				throw new ArrayIndexOutOfBoundsException();
			} else {
				this.pointer = pointer;
				return this.array[pointer];
			}
		}

		public int getPointer() {
			return this.pointer;
		}

		public void setValue(T value) {
			this.array[this.pointer] = value;
		}

		public T getValue() {
			return this.array[this.pointer];
		}

		public boolean forward() {
			if (this.pointer == this.array.length - 1) return false;
			this.pointer++;
			return true;
		}

		public boolean backward() {
			if (this.pointer == 0) return false;
		    this.pointer--;
		    return true;
		}
	}

	public void insertionSort(T[] array, SortType sortType) {
		T temp = null;
		for (int i = 1; i < array.length; i++) {
			switch(sortType) {
				case ASCENDING:
					if (array[i].compareTo(array[i - 1]) < 0) {
						int j = i - 2;
						while (j != -1 && array[i].compareTo(array[j]) < 0) j--;
						for (int k = j + 1; k < i; k++) {
							temp = array[i];
							array[i] = array[k];
							array[k] = temp;
						}
					}
					break;
				case DESCENDING:
					if (array[i].compareTo(array[i - 1]) > 0) {
						int j = i - 2;
						while (j != -1 && array[i].compareTo(array[j]) > 0) j--;
						for (int k = j + 1; k < i; k++) {
							temp = array[i];
							array[i] = array[k];
							array[k] = temp;
						}
					}
					break;
			}
		}
	}

	public void quickSort(T[] array, SortType sortType) {
		switch (sortType) {
			case ASCENDING:
				ascendingQuickSort(array, 0, array.length - 1);
				break;
			case DESCENDING:
				descendingQuickSort(array, 0, array.length - 1);
				break;
		}
	}

	private void descendingQuickSort(T[] array, int lowerBound, int upperBound) {
		if (lowerBound == upperBound) return;
		int divider = partition(array, lowerBound, upperBound);
		if (divider > 0 && lowerBound <= divider - 1)
			descendingQuickSort(array, lowerBound, divider - 1);
		if (divider < array.length && divider + 1 <= upperBound)
			descendingQuickSort(array, divider + 1, upperBound);
	}

	private void ascendingQuickSort(T[] array, int lowerBound, int upperBound) {
		if (lowerBound == upperBound) return;
		int divider = partition(array, lowerBound, upperBound);
		if (divider > 0 && lowerBound <= divider - 1)
			ascendingQuickSort(array, lowerBound, divider - 1);
		if (divider < array.length && divider + 1 <= upperBound)
			ascendingQuickSort(array, divider + 1, upperBound);
	}

	private int partition(T[] array, int lowerBound, int upperBound) {
		T auxiliary;
		int i = lowerBound + 1;
		int j = upperBound;
		System.out.println("Partitioning: lowerBound = " + lowerBound + " and upperBound = " + upperBound);
		while (i <= j) {
			while (i <= j && array[i].compareTo(array[lowerBound]) <= 0) i++;
			while (i <= j && array[j].compareTo(array[lowerBound]) >= 0) j--;
			if (i < j) {
				System.out.println("Swapping i = " + i + " and j = " + j);
				auxiliary = array[i];
				array[i] = array[j];
				array[j] = auxiliary;
				for (int k = 0; k < array.length; k++) {
					System.out.print(array[k].toString() + "[" + k + "] ");
				}
				System.out.println("");
			}
		}
		i--;
		auxiliary = array[lowerBound];
		array[lowerBound] = array[i];
		array[i] = auxiliary;
		System.out.println("Returning " + i);
		return i;
	}

	/**
	 * Given two sorted arrays, contents of the arrays are sorted
	 * and distributed between the two, where the lower portion is
	 * placed in the first one while the second portion is placed
	 * in the second one.
	 */
	public void sortAndDistribute(T[] firstArray, T[] secondArray) {
		ArrayIterator firstArrayIterator = new ArrayIterator(firstArray);
		ArrayIterator secondArrayIterator = new ArrayIterator(secondArray);

		T temp;
		boolean unfinished = true;
		boolean ignoreSubarray = true;
		while (unfinished) {
			if (secondArrayIterator.getPointer() != 0) {
				if (secondArray[0].compareTo(secondArrayIterator.getValue()) < 0) {
					temp = firstArrayIterator.getValue();
					firstArrayIterator.setValue(secondArray[0]);
					unfinished = firstArrayIterator.forward();
					if (secondArrayIterator.getPointer() > 1) {
						shiftContentBlockTowardsHead(secondArray, 1,
								secondArrayIterator.getPointer() - 1);
						ignoreSubarray = false;
					} else {
						ignoreSubarray = true;
					}
					secondArray[secondArrayIterator.getPointer() - 1] = temp;
				} else {
					temp = secondArrayIterator.getValue();
					secondArrayIterator.setValue(firstArrayIterator.getValue());
					firstArrayIterator.setValue(temp);
					unfinished = firstArrayIterator.forward();
					unfinished &= secondArrayIterator.forward();
				}
			} else if (secondArrayIterator.getPointer() == 0 || ignoreSubarray) {
				if (firstArrayIterator.getValue().compareTo(secondArrayIterator.getValue()) <= 0) {
					unfinished = firstArrayIterator.forward();
				} else {
					temp = firstArrayIterator.getValue();
					firstArrayIterator.setValue(secondArrayIterator.getValue());
					secondArrayIterator.setValue(temp);
					unfinished = firstArrayIterator.forward();
					unfinished &= secondArrayIterator.forward();
				}
			}
		}
	}

	/**
	 * Shift an array's block of content, specified
	 * by lower and upper bounds, towards the head.
	 */
	private void shiftContentBlockTowardsHead(T[] array, int lowerBound, int upperBound)
			throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		if (lowerBound > upperBound) throw new IllegalArgumentException();
		if (lowerBound < 0 || upperBound > array.length - 1)
			throw new ArrayIndexOutOfBoundsException();
		for (int i = lowerBound; i <= upperBound; i++)
			array[i - lowerBound] = array[i];
	}

	/**
	 * Find and arrange anagrams given an array of strings.
	 */
	public void arrangeAnagrams(String[] array) {
		
	}

	/**
	 * Calculate a given anagram's hash code. 
	 */
	public static long anagramHashCode(StringBuilder string) {
		char ch;
		long hashCode = 1;
		long primeCode = 13;
		Map<Character, Integer> hash = new HashMap<Character, Integer>();
		for (int i = 0; i < string.length(); i++) {
			ch = string.charAt(i);
			if (hash.containsKey(ch)) {
				hash.put(ch, hash.get(ch) + 1);
			} else hash.put(ch, 1);
		}

		for (Entry<Character, Integer> entry : hash.entrySet()) {
			primeCode = findNextPrimeNumber(primeCode + 1);
			hashCode += Character.getNumericValue(entry.getKey()) * entry.getValue() * primeCode;
		}

		return hashCode;
	}
	
	/**
	 * Given an integer, find the next prime number. 
	 */
	public static long findNextPrimeNumber(long number) {
		if (isPrime(number)) return number;
		long prime = number + 1;
		for (long i = number; i < Math.pow(number, i); i++) {
			if (isPrime(i)) {
				prime = i;
				break;
			}
		}
		return prime;
	}

	/**
	 * Determine if a given number is prime.
	 */
	private static boolean isPrime(long number) {
		for (int i = 2; i <= (int) Math.sqrt(number); i++)
			if (number % i == 0) return false; 
		return true;
	}

	/** 
	 * Find pairs of elements, which sum up to a specified value, in a given sorted array. 
	 */
	public static void findComplements(int sum, int[] array) {
		Map<Integer, Integer> hash = new HashMap<Integer, Integer>();
		for (int i = 0; i < array.length; i++) {
			hash.put(array[i], sum - array[i]);
			if (hash.containsKey(sum - array[i]))
				System.out.println("Complementary Elements: (" + array[i] + ", " + hash.get(array[i]) + ")");
		}
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive, where
	 * the difference between min and max can be at most Integer.MAX_VALUE - 1.
	 *
	 * @param min Minimum value.
	 * @param max Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randomInt(int min, int max) throws IllegalArgumentException {
		if (min >= max) throw new IllegalArgumentException();

	    // nextInt() is normally exclusive of the top value.
	    // Therefore, add '1' to make it inclusive.
		return new Random().nextInt(max - min + 1) + min;
	}

	public void segmentize(int[] array) {
		Map<Integer, Map.Entry<Integer, Integer>> hash = new HashMap<Integer, Map.Entry<Integer, Integer>>();
		int sum = 0;
		int negativePointer = array[0] > 0 ? 0 : -1;
		int positivePointer = array[0] <= 0 ? -1 : 0;
		for (int i = 0; i < array.length; i++) {
			if (i < array.length - 1) {
				if (array[i] > 0 && array[i + 1] <= 0) {
					sum += array[i];
					hash.put(sum, new AbstractMap.SimpleEntry<Integer, Integer>(positivePointer, i));
					negativePointer = i + 1;
					positivePointer = -1;
					sum = 0;
				} else if (array[i] <= 0 && array[i + 1] > 0) {
					sum += array[i];
					hash.put(sum, new AbstractMap.SimpleEntry<Integer, Integer>(negativePointer, i));
					positivePointer = i + 1;
					negativePointer = -1;
					sum = 0;
				} else {
					sum += array[i];
				}
			} else {
				sum += array[i];
				if (positivePointer != -1)
					hash.put(sum, new AbstractMap.SimpleEntry<Integer, Integer>(positivePointer, i));
				if (negativePointer != -1)
					hash.put(sum, new AbstractMap.SimpleEntry<Integer, Integer>(negativePointer, i));
			}
		}
		for (Integer integer : hash.keySet())
			System.out.println("Segment Total Value: " + integer +
					", begins: " + hash.get(integer).getKey() +
					", ends: " + hash.get(integer).getValue());
	}
}
