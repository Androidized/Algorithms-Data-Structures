package com.example.data;

import com.example.data.exception.EmptyQueueException;

public class GenericBinaryHeap<T extends Comparable<? super T>> {

	enum HeapType {
		MIN,
		MAX
	}

	GenericBinaryHeap(HeapType heapType) {
		this.heapType = heapType;
		this.root = null;
		this.size = 0;
		this.internalArray = null;
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
	private int size;
	private T[] internalArray;

	public HeapType getType() {
		return this.heapType;
	}

	public int getNullPathLength() {
		return getNullPathLength(this.root);
	}

	private int getNullPathLength(final Element heapRoot) {
		if (heapRoot == null) return 0;
		return 1 + Math.min(getNullPathLength(heapRoot.leftChild),
				            getNullPathLength(heapRoot.rightChild));
	}

	/**
	 * Merge the current generic binary heap
	 * with the one specified as the argument. 
	 */
	public void meld(GenericBinaryHeap<T> other) {
		if (other == null) return;
		meld(this.root, other.root);
	}

	private Element meld(Element thisRoot, Element otherRoot) {

		// Edge cases
		if (otherRoot == null) return thisRoot;
		if (thisRoot == null) return otherRoot;

		// Ensure the first heap is the one with
		// root data value less than the second
		if (thisRoot.data.compareTo(otherRoot.data) > 0) {
			Element tempRoot = thisRoot;
			thisRoot = otherRoot;
			otherRoot = tempRoot;
		}

		// Meld the first binary heap's right sub-
		// tree with the entire second binary heap
		thisRoot.rightChild = meld(thisRoot.rightChild, otherRoot);

		// Ensure the resultant heap is more
		// loaded towards on its left sub-tree
		// by swapping the right & left sub-trees
		if (getNullPathLength(thisRoot.rightChild) >
		    getNullPathLength(thisRoot.leftChild)) {
			Element tempChild = thisRoot.rightChild;
			thisRoot.rightChild = thisRoot.leftChild;
			thisRoot.leftChild = tempChild;
		}

		return thisRoot;
	}

	/**
	 * Factory method returning a generic binary heap given an
	 * array of raw data elements and the specified heap type.  
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> GenericBinaryHeap<T>
	    genericBinaryHeapFactory(final T[] arrayOfElements, HeapType heapType)
	    		throws IllegalArgumentException {
		if (arrayOfElements == null ||    // Array cannot be null
			arrayOfElements[0] != null || // Array's first element must be null
			arrayOfElements.length < 2)   // Array cannot be empty if not null
			throw new IllegalArgumentException();

		int numberOfLevels = logBaseTwo(arrayOfElements.length);

		for (int i = numberOfLevels - 1; i >= 0; i--) {
			for (int j = (int) Math.pow(2, i);
				j < (int) Math.pow(2, i + 1);
				j++) heapifyFromIndex(j, arrayOfElements, heapType);
		}

		GenericBinaryHeap<T> genericBinaryHeap = new GenericBinaryHeap<T>(heapType);
		GenericQueue<GenericBinaryHeap<T>.Element> breathFirstSearchQueue =
				new GenericQueue<GenericBinaryHeap<T>.Element>();
		genericBinaryHeap.size = arrayOfElements.length - 1;
		genericBinaryHeap.internalArray = (T[]) new Object[arrayOfElements.length];
		for (int j = 0; j < arrayOfElements.length; j++) {
			genericBinaryHeap.internalArray[j] = arrayOfElements[j];
		}
		// By convention, the array's first element is
		// null so the actual data begins at index "1"
		int i = 1;
		GenericBinaryHeap<T>.Element element;
		genericBinaryHeap.root = genericBinaryHeap.new Element(arrayOfElements[i], null, null);
		breathFirstSearchQueue.write(genericBinaryHeap.root);
		while (!breathFirstSearchQueue.isEmpty()) {
			try {
				element = breathFirstSearchQueue.read();
				if (i < arrayOfElements.length - 1 &&
					arrayOfElements[++i] != null) {
					element.leftChild = genericBinaryHeap.new Element(arrayOfElements[i], null, null);
					breathFirstSearchQueue.write(element.leftChild);
				}
				if (i < arrayOfElements.length - 1 &&
					arrayOfElements[++i] != null) {
					element.rightChild = genericBinaryHeap.new Element(arrayOfElements[i], null, null);
					breathFirstSearchQueue.write(element.rightChild);
				}
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		}

		return genericBinaryHeap;
	}

	private static int logBaseTwo(int value) {
		return (int) Math.floor(Math.log10(value) / Math.log10(2));
	}

	/**
	 * Recursively heapify the heap rooted at node
	 * with data value equal to array's index "j".
	 */
	private static <T extends Comparable<? super T>> void
	    heapifyFromIndex(int j, final T[] arrayOfElements, HeapType heapType) {
		T temp;
		T tempRootData;

		// Return if the node is the array's last element or
		// or both of its children fall off the array's boundary
		if (j >= arrayOfElements.length - 1 ||
			2*j > arrayOfElements.length - 1) return;

		// Swap the heap's root and its left child if necessary
		// when the right child falls off the array's boundary
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

		// Heapify the heap rooted at the array's index "j" when the
		// root and its children all fall into the array boundary.
		if (2*j <= arrayOfElements.length - 2) {
			if (heapType == HeapType.MIN) {
				temp = arrayOfElements[2*j].compareTo(arrayOfElements[2*j + 1]) < 0
						? arrayOfElements[2*j] : arrayOfElements[2*j + 1];
			} else {
				temp = arrayOfElements[2*j].compareTo(arrayOfElements[2*j + 1]) > 0
						? arrayOfElements[2*j] : arrayOfElements[2*j + 1];
			}

			// Swap the root's data with
			// the candidate child.
			tempRootData = arrayOfElements[j];
			arrayOfElements[j] = temp;
			temp = tempRootData;

			// Continue with the relevant heap
			// rooted at the candidate child.
			if (temp.equals(arrayOfElements[2*j]))
				heapifyFromIndex(2*j, arrayOfElements, heapType);
			if (temp.equals(arrayOfElements[2*j + 1]))
				heapifyFromIndex(2*j + 1, arrayOfElements, heapType);
		}
	}

	/**
	 * Return the minimum (or the maximum)
	 * element depending on the heap type.
	 */
	public T findMinMax() {
		return this.root.data;
	}

	/**
	 * Delete the minimum (or the maximum)
	 * element depending on the heap type. 
	 */
	public T deleteMinMax() {
		T temp;
		T theMinMax = this.root.data;
		this.root.data = this.internalArray[this.size--];
		heapifyFromIndex(1, this.internalArray, this.heapType); // O(logN)
		Element currentElement = this.root;
		Element elementToHeapify = null;
		do {
			if (this.heapType == HeapType.MIN) {
				if (currentElement.leftChild != null &&
					currentElement.rightChild != null) {
					elementToHeapify = currentElement.leftChild.data.compareTo(
							currentElement.rightChild.data) < 0 ?
									currentElement.leftChild :
									currentElement.rightChild; 
				} else if (currentElement.leftChild != null &&
						   currentElement.rightChild == null) {
					elementToHeapify = currentElement.leftChild;
				} else elementToHeapify = null;
			}
			if (this.heapType == HeapType.MAX) {
				if (currentElement.leftChild != null &&
					currentElement.rightChild != null) {
					elementToHeapify = currentElement.leftChild.data.compareTo(
							currentElement.rightChild.data) > 0 ?
									currentElement.leftChild :
									currentElement.rightChild; 
				} else if (currentElement.leftChild != null &&
						   currentElement.rightChild == null) {
					elementToHeapify = currentElement.leftChild;
				} else elementToHeapify = null;
			}
			if (elementToHeapify != null) {
				temp = currentElement.data;
				currentElement.data = elementToHeapify.data;
				elementToHeapify.data = temp;
			}
			currentElement = elementToHeapify;
		} while (currentElement != null); // O(logN)
		return theMinMax;
	}
}
