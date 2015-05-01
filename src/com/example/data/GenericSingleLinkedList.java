package com.example.data;

import java.util.ArrayList;
import java.util.List;

import com.example.data.exception.EmptyDataStructureException;
import com.example.data.exception.EmptyQueueException;

public class GenericSingleLinkedList<T extends Comparable<? super T>> {
	private Node root;

	GenericSingleLinkedList() {

	}

	class Node {
		T data;
		Node next;
		Node minBeforeThis;

		Node(T data) {
			this.data = data;
			this.next = null;
		}

		Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	class Block {
		Node node;
		int size;

		Block(Node node, int size) {
			this.node = node;
			this.size = size;
		}
	}

	@SuppressWarnings("unchecked")
	public T[] serialize() {
		if (this.root == null) return null;

		List<T> arrayOfElements = new ArrayList<T>();
		Node currentNode = this.root;
		while (currentNode != null) {
			arrayOfElements.add(currentNode.data);
			currentNode = currentNode.next;
		}

		T[] serializedLinkedList = (T[]) new Object[arrayOfElements.size()];
		for (int i = 0; i < arrayOfElements.size(); i++) {
			serializedLinkedList[i] = arrayOfElements.get(i);
		}

		return serializedLinkedList;
	}

	public T getMin() {
		if (this.root == null) return null;
		return this.root.minBeforeThis.data;
	}

	public Node getMinNode() {
		if (this.root == null) return null;
		return this.root.minBeforeThis;
	}

	public boolean isEmpty() {
		return this.root == null;
	}

	public GenericSingleLinkedList<T> add(T data) {
		Node node = new Node(data, this.root);
		if (this.root == null) {
			node.minBeforeThis = node;
		} else {
			node.minBeforeThis = data.compareTo(this.root.minBeforeThis.data) < 0
					? node : this.root.minBeforeThis;
		}
		this.root = node;
		return this;
	}

	public T deleteNodeWithData(T data) {
		if (data == null || this.root == null) return null;

		Node currentNode = this.root;
		Node nodeToDelete;
		Node nodeToReach;
		boolean withBlock;

		while (true) {
			if (currentNode.minBeforeThis.data.compareTo(data) > 0) {
				// Nodes lining up from the head of the data structure
				// to the node pointed by the root.minBeforeThis should
				// have data values greater than the data value to be
				// deleted from the list. In this case, skip to the node
				// pointed by root.minBeforeThis.next and continue.
				currentNode = currentNode.minBeforeThis.next;
			} else if (currentNode.minBeforeThis.data.compareTo(data) < 0) {
				nodeToReach = currentNode.minBeforeThis;
				while (!currentNode.equals(nodeToReach)) {
					if (currentNode.data.compareTo(data) == 0) {
						withBlock= true;
						nodeToDelete = currentNode;
						break;
					}
					currentNode = currentNode.next;
				}
			} else {
				withBlock = false;
				nodeToDelete = currentNode;
				break;
			}
		}

		if (withBlock) {
			nodeToDelete.data = nodeToDelete.next.data;
			nodeToDelete.next = nodeToDelete.next.next;
			nodeToDelete.minBeforeThis = nodeToDelete.next.minBeforeThis;
		} else {
			
		}

		return nodeToDelete.data;
	}

	public T deleteFromHead() throws EmptyDataStructureException {
		if (this.root == null)
			throw new EmptyDataStructureException();

		T headData = this.root.data;
		this.root = this.root.next;
		return headData;
	}

	public Node deleteFromTail() throws EmptyDataStructureException {
		Node toBeDeletedNode = null;
		if (this.root == null) throw new EmptyDataStructureException();
		if (this.root.next == null) {
			toBeDeletedNode = this.root;
			this.root = null;
			return toBeDeletedNode;
		}

		Node currentNode = this.root;
		while (currentNode.next != null && currentNode.next.next != null) {
			currentNode = currentNode.next;
		}
		toBeDeletedNode = currentNode.next;
		currentNode.next = null;
		return toBeDeletedNode;
	}

	public Node peek() {
		return this.root;
	}

	public void reverse() {
		if (this.root == null ||
			(this.root != null && this.root.next == null))
			return;

		Node previous = null;
		Node current = this.root;
		Node next = this.root.next;
		while (current != null) {
			current.next = previous;
			previous = current;
			current = next;
			if (next != null) next = next.next;
			else this.root = previous;
		}
	}

	public GenericSingleLinkedList<T> deleteDuplicates() {
		if (this.root == null || this.root.next == null) return this;

		boolean retainPrimaryPointer = false;
		Node primaryPointer = this.root;
		Node secondaryPointer = this.root.next;
		while (primaryPointer != null) {
			retainPrimaryPointer = false;
			secondaryPointer = primaryPointer.next;
			while (secondaryPointer != null) {
				if (primaryPointer.data.compareTo(secondaryPointer.data) == 0) {
					if (secondaryPointer.next == null) {
						primaryPointer.data = primaryPointer.next.data;
						primaryPointer.next = primaryPointer.next.next;
						retainPrimaryPointer = true;
					} else {
						secondaryPointer.data = secondaryPointer.next.data;
						secondaryPointer.next = secondaryPointer.next.next;
					}
				}
				secondaryPointer = secondaryPointer.next;
			}
			if (!retainPrimaryPointer) primaryPointer = primaryPointer.next;
		}

		return this;
	}

	public void sort() {
		if (this.root == null || this.root.next == null) return;

		GenericQueue<Block> queue = new GenericQueue<Block>();
		Node pointer = this.root;
		while (pointer != null)
			queue.write(new Block(pointer, 1));
		queue.write(new Block(null, 0)); // Delimiter

		Block blockOne = null;
		Block blockTwo = null;
		while (true) {
			try {
				blockOne = queue.read();
				if (blockOne.size == 0) {
					queue.write(blockOne);
					continue;
				}
			} catch (EmptyQueueException e) {}
			try {
				blockTwo = queue.read();
				if (blockTwo.size == 0) {
					if (blockOne.node == this.root) break;
					queue.write(blockOne);
					queue.write(blockTwo);
					continue;
				}
			} catch (EmptyQueueException e) {}
			queue.write(mergeSort(blockOne, blockTwo));
		}
	}

	private Block mergeSort(Block blockOne, Block blockTwo) {
		if (blockTwo == null) return blockOne;

		Node listOneRoot = blockOne.node;
		Node listTwoRoot = blockTwo.node;
		int sizeOne = blockOne.size;
		int sizeTwo = blockTwo.size;
		Node firstPointer = listOneRoot;
		Node secondPointer = listTwoRoot;
		int firstListCounter = 0;
		int secondListCounter = 0;

		while (firstListCounter < sizeOne && secondListCounter < sizeTwo) {
			if (firstPointer.data.compareTo(secondPointer.data) >= 0) {
				Node node = new Node(firstPointer.data, firstPointer.next);
				firstPointer.next = node;
				firstPointer.data = secondPointer.data;
				firstPointer = firstPointer.next;
				secondPointer.data = secondPointer.next.data;
				secondPointer.next = secondPointer.next.next;
				secondListCounter++;
			} else {
				firstPointer = firstPointer.next;
				firstListCounter++;
			}
		}

		return new Block(listOneRoot, blockOne.size + blockTwo.size);
	}
}