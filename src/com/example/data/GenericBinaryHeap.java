package com.example.data;

public class GenericBinaryHeap<T extends Comparable<? super T>> {

	enum HeapType {
		MIN,
		MAX
	}

	GenericBinaryHeap(HeapType heapType) {
		this.heapType = heapType;
		this.root = null;
	}

	class Element {
		T data;
		Element leftChild;
		Element rightChild;
	}

	private HeapType heapType;
	private Element root;

	public static <T extends Comparable<? super T>> GenericBinaryHeap<T>
	    meld(GenericBinaryHeap<T> firstHeap, GenericBinaryHeap<T> secondHeap) {
		return null;
	}

	public GenericBinaryHeap<T> meld(GenericBinaryHeap<T> otherHeap) {
		return null;
	}

	public static <T extends Comparable<? super T>> GenericBinaryHeap<T>
	    genericBinaryHeapFactory(final T[] arrayOfElements, HeapType heapType)
	    		throws NullPointerException {
		if (arrayOfElements == null) throw new NullPointerException();

		int numberOfLevels = (int) Math.floor(Math.log10(arrayOfElements.length) / Math.log10(2));
		for (int i = numberOfLevels - 1; i >= 0; i--)
			for (int j = (int) Math.pow(2, i) - 1;
				j < (int) Math.pow(2, i + 1) - 1;
				j++) heapifyFromIndex(j, arrayOfElements, heapType);
		for (int i = 0; i < numberOfLevels; i++)
			for (int j = (int) Math.pow(2, i) - 1;
				j < (int) Math.pow(2, i + 1) - 1;
				j++) {
				
			}
		return null;
	}

	/**
	 * Recursively heapify the sub-tree rooted at pseudo
	 * node with data value equal to array's index "j".
	 */
	private static <T extends Comparable<? super T>> void
	    heapifyFromIndex(int j, final T[] arrayOfElements, HeapType heapType) {
		T temp;

		// Return if the sub-tree's root is the array's
		// last element or falls off the array's boundary
		if (j >= arrayOfElements.length - 1 ||
			2*j > arrayOfElements.length - 1) return;

		// Swap the sub-tree's root & its left child if necessary
		// when the right child falls off the array's boundary.
		if (2*j == arrayOfElements.length - 1) {
			if  ((heapType == HeapType.MIN &&
				arrayOfElements[j].compareTo(arrayOfElements[2 * j]) > 0) ||
				(heapType == HeapType.MAX &&
				arrayOfElements[j].compareTo(arrayOfElements[2 * j]) < 0)) {
				temp = arrayOfElements[j];
				arrayOfElements[j] = arrayOfElements[2 * j];
				arrayOfElements[2 * j] = temp;
			}
			return;
		}

		// Heapify the topmost tree rooted at array's index "j" when the
		// root, its left & right children all fall into the array boundary.
		if (2*j <= arrayOfElements.length - 2) {
			if ((heapType == HeapType.MIN &&
				arrayOfElements[2*j].compareTo(arrayOfElements[j]) <= 0 &&
				arrayOfElements[2*j].compareTo(arrayOfElements[2*j + 1]) <= 0) ||
				(heapType == HeapType.MAX &&
				arrayOfElements[2*j].compareTo(arrayOfElements[j]) >= 0 &&
				arrayOfElements[2*j].compareTo(arrayOfElements[2*j + 1]) >= 0)) {
				temp = arrayOfElements[j];
				arrayOfElements[j] = arrayOfElements[2*j];
				arrayOfElements[2*j] = temp;
				// Continue with sub-tree rooted at left child
				heapifyFromIndex(2*j, arrayOfElements, heapType);
				return;
			}

			if ((heapType == HeapType.MIN &&
				arrayOfElements[2*j + 1].compareTo(arrayOfElements[j]) <= 0 &&
				arrayOfElements[2*j + 1].compareTo(arrayOfElements[2*j]) <= 0) ||
				(heapType == HeapType.MAX &&
				arrayOfElements[2*j + 1].compareTo(arrayOfElements[j]) >= 0 &&
				arrayOfElements[2*j + 1].compareTo(arrayOfElements[2*j]) >= 0)) {
				temp = arrayOfElements[j];
				arrayOfElements[j] = arrayOfElements[2*j + 1];
				arrayOfElements[2*j + 1] = temp;
				// Continue with sub-tree rooted at right child
				heapifyFromIndex(2*j + 1, arrayOfElements, heapType);
				return;
			}
		}
	}
}
