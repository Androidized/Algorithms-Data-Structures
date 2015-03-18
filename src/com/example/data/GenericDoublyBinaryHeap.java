package com.example.data;

import java.util.EmptyStackException;
import java.util.Stack;

class GenericDoublyBinaryHeap<T extends Comparable<? super T>>{
	enum Type {
		MAX, MIN
	}

	public Type heapType;
	public Element root;

	GenericDoublyBinaryHeap(Type heapType) {
		this.heapType = heapType;
	}

	class Element {
		T data;
		Element parent;
		Element leftChild;
		Element rightChild;

		public Element(T data, Element parent, Element leftChild, Element rightChild) {
			this.data = data;
			this.parent = parent;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		public Element(T data, Element parent) {
			this(data, parent, null, null);
		}
		
		public Element(T data) {
			this(data, null, null, null);
		}
	}

	class DeepestRightmostElement {
		Element deepestRightmostElement;
		Element parentOfNextAvailablePosition;

		public DeepestRightmostElement(Element deepestRightmostElement,
				                       Element parentOfNextAvailablePosition) {
			this.deepestRightmostElement = deepestRightmostElement;
			this.parentOfNextAvailablePosition = parentOfNextAvailablePosition;
		}
	}

	public void insert(T data) {
		if (this.root == null) this.root = new Element(data, null, null, null);

		Element parentOfNextAvailablePosition = getDeepestRightmostElement().parentOfNextAvailablePosition;
		Element insertedElement = new Element(data, parentOfNextAvailablePosition);
		if (parentOfNextAvailablePosition.leftChild == null) {
			parentOfNextAvailablePosition.leftChild = insertedElement;
		} else if (parentOfNextAvailablePosition.rightChild == null) {
			parentOfNextAvailablePosition.rightChild = insertedElement;
		}

		if (this.heapType == Type.MAX) {
			while (insertedElement.parent != null &&
				   insertedElement.data.compareTo(insertedElement.parent.data) > 0) {
				swapElements(insertedElement, insertedElement.parent);
				insertedElement = insertedElement.parent;
			}
		} else {
			while (insertedElement.parent != null &&
				   insertedElement.data.compareTo(insertedElement.parent.data) < 0) {
				swapElements(insertedElement, insertedElement.parent);
				insertedElement = insertedElement.parent;
			}
		}
	}
	
	private void swapElements(final Element insertedElement, final Element parent) {
		T data = insertedElement.data;
		insertedElement.data = parent.data;
		parent.data = data;
	}

	public DeepestRightmostElement getDeepestRightmostElement() {
		if (this.root == null) return null;

        // Current element & its parent while traversing the heap.
		Element element = this.root;
		Element parent = null;

		// Deepest rightmost element & its parent.
		Element deepestRightmostElement = null;
		Element parentOfNextAvailablePosition = null;

		// Current & maximum (deepest) levels when traversing
		// the heap when the search is complete, respectively.
		int currentLevel = 0;
		int maxLevel = 0;

		// Level of next available position.
		int nextAvailablePositionLevel = -1;

		boolean continueToRight = false;
		boolean pushedElementToStack = false;
		Stack<Element> stack = new Stack<Element>();

		do {
			pushedElementToStack = false;
			while (element.leftChild != null) {
				pushedElementToStack = true;
				currentLevel++;
				stack.push(element);
				element = element.leftChild;
			}

			System.out.println("Hit Tree's Bottom at Element: " +
			    element.data.toString() + ", Level: " + currentLevel);

			Element elementToPop = null;
			if (maxLevel <= currentLevel) {
				parent = stack.peek();
				maxLevel = currentLevel;
				if (parent.rightChild != null) {
					elementToPop = stack.pop();
					deepestRightmostElement = elementToPop.rightChild;
					System.out.println("Current Deepest Rightmost Element: " +
							deepestRightmostElement.data.toString() + ", Level: " + currentLevel);
				} else {
					elementToPop = stack.pop();
					deepestRightmostElement = elementToPop.leftChild;
					parentOfNextAvailablePosition = parent;
					nextAvailablePositionLevel = currentLevel;
					System.out.println("Current Deepest Rightmost Element: " +
							deepestRightmostElement.data.toString() + ", Level: " + currentLevel);
				}
			} else {
				if (maxLevel == currentLevel + 1 &&
					nextAvailablePositionLevel == -1) {
					parentOfNextAvailablePosition = element;
					nextAvailablePositionLevel = currentLevel + 1;
					System.out.println("Parent of Next Available Position: " +
					    parentOfNextAvailablePosition.data.toString());
				}
				try {
					if (pushedElementToStack) {
						elementToPop = stack.pop();
					}
				} catch (EmptyStackException e) {}
			}

			if (stack.peek() != null &&
				!stack.peek().leftChild.equals(elementToPop) &&
				!stack.peek().rightChild.equals(elementToPop)) {
				currentLevel -= 3;
			} else {
				currentLevel -= 2;
			}

			if (stack.peek() != null && stack.peek().rightChild != null) {
				continueToRight = true;
				element = stack.pop().rightChild;
				currentLevel++;
			} else continueToRight = false;
		} while (continueToRight || !stack.isEmpty());

		return new DeepestRightmostElement(deepestRightmostElement, parentOfNextAvailablePosition);
	}

	public void delete(T data) {
		if (this.root == null) return;

		Element elementToDelete = getElement(data);
		if (elementToDelete == null) return;

		if (elementToDelete.leftChild == null && elementToDelete.rightChild == null) {
			if (elementToDelete.parent.leftChild.equals(elementToDelete)) {
				elementToDelete.parent.leftChild = null;
				return;
			} else {
				elementToDelete.parent.rightChild = null;
				return;
			}
		} else if (elementToDelete.leftChild == null && elementToDelete.rightChild != null) {
			if (elementToDelete.parent.leftChild.equals(elementToDelete)) {
				elementToDelete.parent.leftChild = elementToDelete.rightChild;
				return;
			}
			if (elementToDelete.parent.rightChild.equals(elementToDelete)) {
				elementToDelete.parent.rightChild = elementToDelete.rightChild;
				return;
			}
		} else if (elementToDelete.leftChild != null && elementToDelete.rightChild == null) {
			if (elementToDelete.parent.leftChild.equals(elementToDelete)) {
				elementToDelete.parent.leftChild = elementToDelete.leftChild;
				return;
			}
			if (elementToDelete.parent.rightChild.equals(elementToDelete)) {
				elementToDelete.parent.rightChild = elementToDelete.leftChild;
				return;
			}
		} else {
			DeepestRightmostElement deepestRightmostElement = getDeepestRightmostElement();
			elementToDelete.data = deepestRightmostElement.deepestRightmostElement.data;
			Element parent = deepestRightmostElement.deepestRightmostElement.parent;
			if (parent.leftChild.equals(deepestRightmostElement)) {
				parent.leftChild = null;
			}
			if (parent.rightChild.equals(deepestRightmostElement)) {
				parent.rightChild = null;
			}
			heapifyFromElement(elementToDelete);
		}
	}

	private void heapifyFromElement(Element element) {
		while (element.leftChild != null || element.rightChild != null) {
			if (this.heapType == Type.MAX) {
				if (element.leftChild != null && element.rightChild != null &&
						element.data.compareTo(element.leftChild.data) < 0
							&& element.leftChild.data
								.compareTo(element.rightChild.data) < 0) {
					swapElements(element, element.rightChild);
				} else if (element.leftChild != null && element.rightChild != null &&
						  	element.data.compareTo(element.rightChild.data) < 0
								&& element.rightChild.data
									.compareTo(element.leftChild.data) < 0) {
					swapElements(element, element.leftChild);
				} else if (element.leftChild != null && 
						element.data.compareTo(element.leftChild.data) < 0) {
					swapElements(element, element.leftChild);
				} else if (element.rightChild != null &&
						element.data.compareTo(element.rightChild.data) < 0) {
					swapElements(element, element.rightChild);
				} else
					return;
			} else {
				if (element.leftChild != null && element.rightChild != null &&
						element.data.compareTo(element.leftChild.data) > 0
							&& element.leftChild.data
								.compareTo(element.rightChild.data) > 0) {
					swapElements(element, element.rightChild);
				} else if (element.leftChild != null && element.rightChild != null &&
						element.data.compareTo(element.rightChild.data) > 0
							&& element.rightChild.data
								.compareTo(element.leftChild.data) > 0) {
					swapElements(element, element.leftChild);
				} else if (element.leftChild != null &&
						element.data.compareTo(element.leftChild.data) > 0) {
					swapElements(element, element.leftChild);
				} else if (element.rightChild != null && 
						element.data.compareTo(element.rightChild.data) > 0) {
					swapElements(element, element.rightChild);
				} else
					return;
			}
		}
	}

	private Element getElement(T data) {
		return null;
	}

	public void heapify(Element root, Type type) {
		if (root.leftChild.leftChild != null ||
			root.leftChild.rightChild != null) {
			heapify(root.leftChild, type);
		}

		if (root.rightChild.leftChild != null ||
			root.rightChild.rightChild != null) {
			heapify(root.rightChild, type);
		}

		if ((root.data.compareTo(root.leftChild.data) > 0 &&
		     root.data.compareTo(root.rightChild.data) > 0 &&
		     type == Type.MAX) ||
		    (root.data.compareTo(root.leftChild.data) < 0 &&
		     root.data.compareTo(root.rightChild.data) < 0 &&
		     type == Type.MIN)) {
  			return;
	    }

		if ((root.rightChild.data.compareTo(root.data) > 0 &&
			 root.rightChild.data.compareTo(root.leftChild.data) > 0 &&
			 type == Type.MAX) ||
			(root.rightChild.data.compareTo(root.data) < 0 &&
			 root.rightChild.data.compareTo(root.leftChild.data) < 0 &&
			 type == Type.MIN)) {
      		T data = root.data;
      		root.data = root.rightChild.data;
      		root.rightChild.data = data;
		} else if ((root.leftChild.data.compareTo(root.data) > 0 &&
			 root.leftChild.data.compareTo(root.rightChild.data) > 0 &&
			 type == Type.MAX) ||
			(root.leftChild.data.compareTo(root.data) < 0 &&
			 root.leftChild.data.compareTo(root.rightChild.data) < 0 &&
			 type == Type.MIN)) {
	      	T data = root.data;
	      	root.data = root.leftChild.data;
	      	root.leftChild.data = data;
		}

		if (root.leftChild.leftChild != null ||
			root.leftChild.rightChild != null) {
			heapify(root.leftChild, type);
		}

		if (root.rightChild.leftChild != null ||
			root.rightChild.rightChild != null) {
			heapify(root.rightChild, type);
		}

		return;
	}
}