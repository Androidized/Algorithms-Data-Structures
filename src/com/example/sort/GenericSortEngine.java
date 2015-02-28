package com.example.sort;

public class GenericSortEngine<T extends Comparable<? super T>> {
	public enum SortType {
		ASCENDING,
		DESCENDING
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
}
