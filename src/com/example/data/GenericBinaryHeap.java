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

		Element(T data, Element leftChild, Element rightChild) {
			this.data = data;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		T data;
		Element leftChild;
		Element rightChild;
	}

	private HeapType heapType;
	private Element root;

	public static <T extends Comparable<? super T>> int
	    getNullPathLength(final GenericBinaryHeap<T>.Element heap) {
		if (heap == null) return 0;
		if (heap.leftChild == null || heap.rightChild == null) return 1;
		return 1 + Math.min(getNullPathLength(heap.leftChild),
				getNullPathLength(heap.rightChild));
	}

	/**
	 * Merge two generic binary heaps. 
	 */
	public static <T extends Comparable<? super T>> GenericBinaryHeap<T>.Element
	    meld(GenericBinaryHeap<T>.Element firstHeapRoot,
	    		GenericBinaryHeap<T>.Element secondHeapRoot) {

		// Edge cases
		if (firstHeapRoot == null) return secondHeapRoot;
		if (secondHeapRoot == null) return firstHeapRoot;

		// Ensure the first heap is the one with
		// root data value less than the second
		if (firstHeapRoot.data.compareTo(secondHeapRoot.data) > 0) {
			GenericBinaryHeap<T>.Element tempReference = firstHeapRoot;
			firstHeapRoot = secondHeapRoot;
			secondHeapRoot = tempReference;
		}

		// Meld the first binary heap's right sub-
		// tree with the entire second binary heap
		firstHeapRoot.rightChild = meld(firstHeapRoot.rightChild, secondHeapRoot);
		return null;
	}

	/**
	 * Merge the current generic binary heap
	 * with the one specified as the argument. 
	 */
	public GenericBinaryHeap<T> meld(GenericBinaryHeap<T> otherHeap) {
		return null;
	}

	/**
	 * Factory method returning a generic binary heap given an
	 * array of raw data elements and the specified heap type.  
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> GenericBinaryHeap<T>
	    genericBinaryHeapFactory(final T[] arrayOfElements, HeapType heapType)
	    		throws NullPointerException {
		if (arrayOfElements == null) throw new NullPointerException();

		int numberOfLevels = (int) Math.floor(Math.log10(arrayOfElements.length) / Math.log10(2));
		for (int i = numberOfLevels - 1; i >= 0; i--)
			for (int j = (int) Math.pow(2, i) - 1;
				j < (int) Math.pow(2, i + 1) - 1;
				j++) heapifyFromIndex(j, arrayOfElements, heapType);

		GenericBinaryHeap<T> genericBinaryHeap = new GenericBinaryHeap<T>(heapType);
		GenericBinaryHeap<T>.Element[] arrayOfElementizedData =
				(GenericBinaryHeap<T>.Element[]) new Object[arrayOfElements.length];

		for (int j = (int) Math.pow(2, numberOfLevels) - 1; j < arrayOfElements.length; j++)
			arrayOfElementizedData[j] = genericBinaryHeap
					.new Element(arrayOfElements[j], null, null);

		for (int i = numberOfLevels - 1; i >= 0; i--)
			for (int j = (int) Math.pow(2, i) - 1;
				j < (int) Math.pow(2, i + 1) - 1;
				j++) {
				if (2*j <= arrayOfElements.length - 2) {
					arrayOfElementizedData[j] = genericBinaryHeap
							.new Element(arrayOfElements[j],
									arrayOfElementizedData[2*j],
									arrayOfElementizedData[2*j + 1]);
				} else if (2*j == arrayOfElements.length - 1) {
					arrayOfElementizedData[j] = genericBinaryHeap
							.new Element(arrayOfElements[j],
									arrayOfElementizedData[2*j], null);
				} else {
					arrayOfElementizedData[j] = genericBinaryHeap
							.new Element(arrayOfElements[j], null, null);
				}
			}
		return genericBinaryHeap;
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
