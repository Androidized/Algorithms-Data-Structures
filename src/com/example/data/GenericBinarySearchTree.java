package com.example.data;

import java.util.LinkedList;
import java.util.Iterator;

public class GenericBinarySearchTree<T extends Comparable<? super T>> {
	Node root;

	GenericBinarySearchTree() {
		
	}

	class Node {
		T data;
		Node leftChild;
		Node rightChild;

		Node(T data) {
			this.data = data;
			this.leftChild =null;
			this.rightChild = null;
		}

		Node (T data, Node leftChild, Node rightChild) {
			this.data = data;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}
	}

	public boolean isRoot(T data) {
		if (this.root.data.compareTo(data) == 0) return true;
		else return false;
	}

	private void traverseAndUpdate(final Node node, final LinkedList<Node> list) {
		Node traversalNode = node;
		while (traversalNode != null) {
			list.add(traversalNode);
			traversalNode = traversalNode.leftChild;
		}
	}
	
	public Iterator<T> iterator() {
		final LinkedList<Node> list = new LinkedList<Node>();
		traverseAndUpdate(GenericBinarySearchTree.this.root, list);
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public T next() {
				Node node = list.peekLast();
				list.removeLast();
				traverseAndUpdate(node.rightChild, list);
				return node.data;
			}

			@Override
			public void remove() {
				
			}

		};
	}

	public boolean insert(T data) {
		boolean result;

		if (this.root == null) {
			this.root = new Node(data);
			return true;
		}

		Node currentNode = this.root;
		while (true) {
			if (currentNode.data.compareTo(data) > 0) {
				if (currentNode.leftChild == null) {
					Node node = new Node(data);
					currentNode.leftChild = node;
					result = true;
					break;
				} else {
					currentNode = currentNode.leftChild;
				}
			} else if (currentNode.data.compareTo(data) < 0) {
				if (currentNode.rightChild == null) {
					Node node = new Node(data);
					currentNode.rightChild = node;
					result = true;
					break;
				} else {
					currentNode = currentNode.rightChild;
				}
			} else {
				result = false;
				break;
			}
		}

		return result;
	}

	public boolean search(T data) {
		boolean result;
		Node currentNode = this.root;
		while (true) {
			if (currentNode.data.compareTo(data) > 0) {
				if (currentNode.leftChild == null) {
					result = false;
					break;
				} else {
					currentNode = currentNode.leftChild;
				}
			} else if (currentNode.data.compareTo(data) < 0) {
				if (currentNode.rightChild == null) {
					result = false;
					break;
				} else {
					currentNode = currentNode.rightChild;
				}
			} else {
				result = true;
				break;
			}
		}

		return result;
	}

	public Node getNode(T data) {
		Node currentNode = this.root;
		while (true) {
			if (currentNode.data.compareTo(data) > 0) {
				if (currentNode.leftChild != null) currentNode = currentNode.leftChild;
				else return null;
			} else if (currentNode.data.compareTo(data) < 0) {
				if (currentNode.rightChild != null) currentNode = currentNode.rightChild;
				else return null;
			} else {
				return currentNode;
			}
		}
	}

	public Node getParent(T data) {
		Node parent = null;
		Node child = this.root;
		while (true) {
			if (child.data.compareTo(data) > 0) {
				if (child.leftChild != null) {
					parent = child;
					child = child.leftChild;
				} else return null;
			} else if (child.data.compareTo(data) < 0) {
				if (child.rightChild != null) {
					parent = child;
					child = child.rightChild;
				} else return null;
			} else {
				return parent;
			}
		}
	}

	public Node getNextInOrderTraverse(T data) throws IllegalArgumentException {
		Node node = getNode(data);
		Node nextInOrder = node.rightChild;
		if (nextInOrder == null) {
			throw new IllegalArgumentException("Input should have both children!");
		}
		while (nextInOrder.leftChild != null) {
			nextInOrder = nextInOrder.leftChild; 
		}
		return nextInOrder;
	}

	public Node getNextInReverseOrderTraverse(T data) throws IllegalArgumentException {
		Node node = getNode(data);
		Node nextInReverseOrder = node.leftChild;
		if (nextInReverseOrder == null) {
			throw new IllegalArgumentException("Input should have both children!");
		}
		while (nextInReverseOrder.rightChild != null) {
			nextInReverseOrder = nextInReverseOrder.rightChild;
		}
		return nextInReverseOrder;
	}

	public Node delete(T data) {
		Node nodeToDelete = getNode(data);
		Node nodeToDeleteParent = getParent(data);

		if (nodeToDelete == null) return null;

		if (nodeToDelete.leftChild == null && nodeToDelete.rightChild == null) {
			if (nodeToDeleteParent.leftChild.equals(nodeToDelete)) {
				nodeToDeleteParent.leftChild = null;
			}
			if (nodeToDeleteParent.rightChild.equals(nodeToDelete)) {
				nodeToDeleteParent.rightChild = null;
			}
			return nodeToDelete;
		}

		if (nodeToDelete.leftChild == null && nodeToDelete.rightChild != null) {
			if (nodeToDeleteParent.leftChild.equals(nodeToDelete)) {
				nodeToDeleteParent.leftChild = nodeToDelete.rightChild;
			}
			if (nodeToDeleteParent.rightChild.equals(nodeToDelete)) {
				nodeToDeleteParent.rightChild = nodeToDelete.rightChild;
			}
			return nodeToDelete;
		}

		if (nodeToDelete.leftChild != null && nodeToDelete.rightChild == null) {
			if (nodeToDeleteParent.leftChild.equals(nodeToDelete)) {
				nodeToDeleteParent.leftChild = nodeToDelete.leftChild;
			}
			if (nodeToDeleteParent.rightChild.equals(nodeToDelete)) {
				nodeToDeleteParent.rightChild = nodeToDelete.leftChild;
			}
			return nodeToDelete;
		}

		if (nodeToDelete.leftChild != null && nodeToDelete.rightChild != null) {
			Node replacement;
			if (getNextInOrderTraverse(data) != null) {
				replacement = getNextInOrderTraverse(data);
				swapNodes(nodeToDelete, replacement);
				return nodeToDelete;
			}
			if (getNextInReverseOrderTraverse(data) != null) {
				replacement = getNextInReverseOrderTraverse(data);
				swapNodes(nodeToDelete, replacement);
				return nodeToDelete;
			}
		}

		return null;
	}

	private void swapNodes(Node nodeToDelete, Node replacement) {
		Node parentOfReplacement = getParent(replacement.data);
		Node nodeToDeleteParent = getParent(nodeToDelete.data);

		if (!parentOfReplacement.equals(nodeToDelete)) {
			if (parentOfReplacement.leftChild.equals(replacement)) {
				parentOfReplacement.leftChild = null;
			}

			if (parentOfReplacement.rightChild.equals(replacement)) {
				parentOfReplacement.rightChild = null;
			}
		}

		if (!nodeToDelete.leftChild.equals(replacement))
			replacement.leftChild = nodeToDelete.leftChild;

		if (!nodeToDelete.rightChild.equals(replacement))
			replacement.rightChild = nodeToDelete.rightChild;

		if (nodeToDeleteParent.leftChild != null && nodeToDeleteParent.leftChild.equals(nodeToDelete)) {
			nodeToDeleteParent.leftChild = replacement;
		}

		if (nodeToDeleteParent.rightChild != null && nodeToDeleteParent.rightChild.equals(nodeToDelete)) {
			nodeToDeleteParent.rightChild = replacement;
		}
	}

	public void balance() {
		
	}

	public int findLongestLeaf() {
		return 0;
	}

	public LinkedList<Node> getLinkedListForDepth(int level) {
		if (level <= 0 || this.root == null) return null;

		LinkedList<Node> first = new LinkedList<Node>();
		LinkedList<Node> second = new LinkedList<Node>();

		first.add(this.root);
		if (level == 1) return first;

		for (int i = 2; i <= level; i++) {
			for (Node node : first) {
				if (node.leftChild != null) second.add(node.leftChild);
				if (node.rightChild != null) second.add(node.rightChild);
			}
			first = new LinkedList<Node>(second);
			second.clear();
		}

		return first;
	}

	public T getLowestData() {
		if (this.root == null) return null;

		Node current = this.root;
		while (current.leftChild != null) {
			current = current.leftChild;
		}
		return current.data;
	}

	public Node getNodeWithLowestData() {
		if (this.root == null) return null;

		Node current = this.root;
		while (current.leftChild != null) {
			current = current.leftChild;
		}
		return current;
	}

	public static <T extends Comparable<? super T>> boolean
	    isPostOrderTraversal(final T[] traversal) {
		return isPostOrderTraversal(traversal, 0, traversal.length - 1);
	}

	public static <T extends Comparable<? super T>> boolean
	    isPostOrderTraversal(final T[] traversal, int low, int high) {
		if (low == high || low == high - 1) return true;

		T root = traversal[high];
		int lowIndex = low;
		int highIndex = high - 1;
		int i = highIndex;
		while (i >= lowIndex && traversal[i].compareTo(root) > 0) i--;
		if (i == lowIndex - 1) return isPostOrderTraversal(traversal, lowIndex, highIndex); 
		int j = lowIndex;
		while (j <= highIndex && traversal[j].compareTo(root) < 0) j++;
		if (j == highIndex + 1) return isPostOrderTraversal(traversal, lowIndex, highIndex);
		if (i != j - 1) return false;
		else return isPostOrderTraversal(traversal, lowIndex, j - 1) &&
				    isPostOrderTraversal(traversal, i + 1, highIndex);
	}

	public static <T extends Comparable<? super T>> boolean
	    isPreOrderTraversal(final T[] traversal) {
		return isPreOrderTraversal(traversal, 0, traversal.length - 1);
	}

	public static <T extends Comparable<? super T>> boolean
		isPreOrderTraversal(final T[] traversal, int low, int high) {
		if (low == high || low == high - 1) return true;
	
		T root = traversal[low];
		int lowIndex = low + 1;
		int highIndex = high;
		int i = highIndex;
		while (i >= lowIndex && traversal[i].compareTo(root) > 0) i--;
		if (i == lowIndex - 1) return isPreOrderTraversal(traversal, lowIndex, highIndex); 
		int j = lowIndex;
		while (j <= highIndex && traversal[j].compareTo(root) < 0) j++;
		if (j == highIndex + 1) return isPreOrderTraversal(traversal, lowIndex, highIndex);
		if (i != j - 1) return false;
		else return isPreOrderTraversal(traversal, lowIndex, j - 1) &&
				    isPreOrderTraversal(traversal, i + 1, highIndex);
    }
}
