package com.example.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GenericBinaryTree<T extends Comparable<? super T>> {
	public enum RecursionDirection {
		LEFT,
		RIGHT
	}

	public class Element {
		public T data;
		public Element leftChild;
		public Element rightChild;

		public Element(T data, Element leftChild, Element rightChild) {
			this.data = data;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		public Element(T data) {
			this.data = data;
			this.leftChild = null;
			this.rightChild = null;
		}
	}

	public GenericBinaryTree() {
		this.size = 0;
		this.root = null;
		this.elementWithMaxData = null;
		this.elementWithMinData = null;
	}

	private int size;
	public Element root;
	private Element elementWithMinData;
	private Element elementWithMaxData;

	public Element getElementWithMinData() {
		return this.elementWithMinData;
	}

	public Element getElementWithMaxData() {
		return this.elementWithMaxData;
	}

	public T delete(T data) {
		// TODO: If deletion succeeds, decrement size.
		// this.size--;

		return null;
	}

	public void insert(T data) {
		Element element = new Element(data);
		if (this.elementWithMaxData.data.compareTo(data) < 0)
			this.elementWithMaxData = element;
		if (this.elementWithMinData.data.compareTo(data) > 0)
			this.elementWithMinData = element;

		// TODO: Add the new element to the tree.
		// If insertion succeeds, increment size.
		// this.size++;

		return;
	}

	public int getNumberOfLeaves(Element localRoot) {
		if (localRoot.leftChild == null && localRoot.rightChild == null)
			return 1;
		int numberOfLeaves = 0;
		if (localRoot.leftChild != null)
			numberOfLeaves += getNumberOfLeaves(localRoot.leftChild);
		if (localRoot.rightChild != null)
			numberOfLeaves += getNumberOfLeaves(localRoot.rightChild);
		return numberOfLeaves;
	}

	public int getTotalNumberOfInternalElements(Element localRoot) {
		if (localRoot == null ||
		   (localRoot.leftChild == null && localRoot.rightChild == null))
			return 0;
		else return 1 +
			   getTotalNumberOfInternalElements(localRoot.leftChild) +
			   getTotalNumberOfInternalElements(localRoot.rightChild);
	}

	public List<T> inOrderTraversal() {
		List<T> inOrderTraversal = new ArrayList<T>();
		inOrderTraversal(this.root, inOrderTraversal);
		return inOrderTraversal;
	}

	private void inOrderTraversal(Element root, final List<T> inOrderTraversal) {
		if (root == null) return;
		if (root.leftChild != null) inOrderTraversal(root.leftChild, inOrderTraversal);
		inOrderTraversal.add(root.data);
		if (root.rightChild != null) inOrderTraversal(root.rightChild, inOrderTraversal);
	}

	public List<T> preOrderTraversal() {
		List<T> preOrderTraversal = new ArrayList<T>();
		preOrderTraversal(this.root, preOrderTraversal);
		return preOrderTraversal;
	}

	private void preOrderTraversal(Element root, final List<T> preOrderTraversal) {
		if (root == null) return;
		preOrderTraversal.add(root.data);
		if (root.leftChild != null) preOrderTraversal(root.leftChild, preOrderTraversal);
		if (root.rightChild != null) preOrderTraversal(root.rightChild, preOrderTraversal);
	}

	public List<T> postOrderTraversal() {
		List<T> postOrderTraversal = new ArrayList<T>();
		postOrderTraversal(this.root, postOrderTraversal);
		return postOrderTraversal;
	}

	private void postOrderTraversal(Element root, final List<T> postOrderTraversal) {
		if (root == null) return;
		if (root.leftChild != null) postOrderTraversal(root.leftChild, postOrderTraversal);
		if (root.rightChild != null) postOrderTraversal(root.rightChild, postOrderTraversal);
		postOrderTraversal.add(root.data);
	}

	public void convertBinaryTreeToLinkedListInOrderTraversal() {
		Element previous = convertBinaryTreeToLinkedList(this.root.leftChild, RecursionDirection.LEFT);
		Element next = convertBinaryTreeToLinkedList(this.root.rightChild, RecursionDirection.RIGHT);

		this.root.leftChild = previous;
		if (previous != null) previous.rightChild = this.root;
		this.root.rightChild = next;
		if (next != null) next.leftChild = this.root;
	}

	private Element convertBinaryTreeToLinkedList(Element root,
			RecursionDirection recursionDirection) {
		if (root == null) return null;
		if (root.leftChild == null && root.rightChild == null)	return root;

		Element previous = convertBinaryTreeToLinkedList(root.leftChild,
				RecursionDirection.LEFT);
		Element next = convertBinaryTreeToLinkedList(root.rightChild,
				RecursionDirection.RIGHT);

		if (recursionDirection == RecursionDirection.LEFT) {
			if (next == null) {
				root.leftChild = previous;
				if (previous != null) previous.rightChild = root;
				return root;
			} else {
				root.leftChild = previous;
				if (previous != null) previous.rightChild = root;
				root.rightChild = next;
				next.leftChild = root;
				return next;
			}
		} else {
			if (previous == null) {
				root.rightChild = next;
				if (next != null) next.leftChild = root;
				return root;
			} else {
				root.leftChild = previous;
				previous.rightChild = root;
				root.rightChild = next;
				if (next != null) next.leftChild = root;
				return previous;
			}
		}
	}

	public void findPathSumToValue(int value) {
		if (this.root == null) return;
		findPathSumToValue(value, (Number) this.root.data, this.root.leftChild);
		findPathSumToValue(value, (Number) this.root.data, this.root.rightChild);
	}

	private void findPathSumToValue(int value, Number sum, Element child) {
		sum = sum.intValue() + ((Number) child.data).intValue();
		if (sum.intValue() == value) {
			System.out.println("Sum of values from root to node " +
		        child.data.toString() + " equls to " + value);
		} else if (sum.intValue() < value) {
			if (child.leftChild != null)
				findPathSumToValue(value, sum, child.leftChild);
			if (child.rightChild != null)
				findPathSumToValue(value, sum, child.rightChild);
		}
	}

	@SuppressWarnings("unchecked")
	public T[] serialize() {
		if (this.root == null) return null;

		T[] serializedTree = (T[]) new Object();
		final List<T> serializedTreeAsList = new ArrayList<T>();
		serializeTree(this.root, serializedTreeAsList);
		for (int i = 0; i < serializedTreeAsList.size(); i++)
			serializedTree[i] = serializedTreeAsList.get(i);
		return serializedTree;
	}

	private void serializeTree(final Element rootOfTree, final List<T> serializedTreeAsList) {
		if (rootOfTree != null) {
			serializedTreeAsList.add(rootOfTree.data);
			if (rootOfTree.leftChild != null) serializeTree(rootOfTree.leftChild, serializedTreeAsList);
			if (rootOfTree.rightChild != null) serializeTree(rootOfTree.rightChild, serializedTreeAsList);
			serializedTreeAsList.add(null); // Add a signature to indicate moving one level up in the tree.
		} else return;
	}

	public GenericBinaryTree<T> deserialize(final T[] serializedTree) {
		if (serializedTree == null || serializedTree.length == 0) return null;

		Element child = null;
		Element parent = null;
		GenericBinaryTree<T> genericBinaryTree = new GenericBinaryTree<T>();
		final Stack<Element> preOrderTraversalStack = new Stack<Element>();
		for (int i = 0; i < serializedTree.length; i++) {
			if (serializedTree[i] != null) {
				preOrderTraversalStack.push(new Element(serializedTree[i]));
			} else {
				child = preOrderTraversalStack.pop();
				if (preOrderTraversalStack.peek() != null) {
					parent = preOrderTraversalStack.peek();
					if (parent.leftChild == null) parent.leftChild = child;
					else if (parent.rightChild == null) parent.rightChild = child;
				} else {
					genericBinaryTree.root = child;
				}
			}
		}

		return genericBinaryTree;
	}
}
