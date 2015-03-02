package com.example.sort;

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
	 * Shift array's block of content, specified
	 * by lower & upper bounds towards the head.
	 */
	private void shiftContentBlockTowardsHead(T[] array, int lowerBound, int upperBound)
			throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
		if (lowerBound > upperBound) throw new IllegalArgumentException();
		if (lowerBound < 0 || upperBound > array.length - 1)
			throw new ArrayIndexOutOfBoundsException();
		for (int i = lowerBound; i <= upperBound; i++)
			array[i - lowerBound] = array[i];
	}
}
