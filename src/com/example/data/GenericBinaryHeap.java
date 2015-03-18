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
	    genericBinaryHeapFactory(T[] arrayOfElements)
	    		throws NullPointerException {
		if (arrayOfElements == null) throw new NullPointerException();
		int numberOfLevels = (int) Math.floor(Math.log(arrayOfElements.length));
		for (int i = numberOfLevels - 1; i >= 0; i--)
			for (int j = (int) Math.pow(2, i) - 1;
				j < (int) Math.pow(2, i + 1) - 1;
				j++) heapifyFromIndex(j, arrayOfElements);
		return null; // TODO
	}

	private static <T extends Comparable<? super T>> void
	    heapifyFromIndex(int j, final T[] arrayOfElements) {
		if (j >= arrayOfElements.length - 1 || 2*j > arrayOfElements.length - 1)
			return;

		T temp;
		if (2*j <= arrayOfElements.length - 1 &&
			2*j + 1 > arrayOfElements.length - 1) {
			if (arrayOfElements[j].compareTo(arrayOfElements[2*j]) > 0) {
				temp = arrayOfElements[j];
				arrayOfElements[j] = arrayOfElements[2*j];
				arrayOfElements[2*j] = temp;
			}
			return;
		}

		if (2*j <= arrayOfElements.length - 1 &&
			2*j + 1 <= arrayOfElements.length - 1) {
			if (arrayOfElements[2*j].compareTo(arrayOfElements[j]) <= 0 &&
				arrayOfElements[2*j].compareTo(arrayOfElements[2*j + 1]) <= 0) {
				temp = arrayOfElements[j];
				arrayOfElements[j] = arrayOfElements[2*j];
				arrayOfElements[2*j] = temp;
				heapifyFromIndex(2*j, arrayOfElements);
			} else if (arrayOfElements[2*j + 1].compareTo(arrayOfElements[j]) <= 0 &&
				arrayOfElements[2*j + 1].compareTo(arrayOfElements[2*j]) <= 0) {
				temp = arrayOfElements[j];
				arrayOfElements[j] = arrayOfElements[2*j + 1];
				arrayOfElements[2*j + 1] = temp;
				heapifyFromIndex(2*j + 1, arrayOfElements);
			}
		}			
	}
}
