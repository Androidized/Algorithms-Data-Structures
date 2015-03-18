package com.example.data;

import com.example.data.exception.EmptyDataStructureException;
import com.example.data.exception.EmptyQueueException;

public class GenericSingleLinkedList<T extends Comparable<? super T>> {
	private Node root;
	private Node nodeWithMinData;

	GenericSingleLinkedList() {

	}

	class Node {
		T data;
		Node next;
		Node minBeneathThis;

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

	public void printContent() {
		Node currentNode = this.root;
		while (currentNode != null) {
			if (currentNode.data != null)
				System.out.println(currentNode.data.toString());
			else
				System.out.println("NULL");

			currentNode = currentNode.next;
		}
	}

	public Node getNodeWithMinData() {
		return this.nodeWithMinData;
	}

	public boolean isEmpty() {
		return this.root == null;
	}

	public GenericSingleLinkedList<T> appendToTail(T data) {
		if (this.root == null) {
			this.root = new Node(data);
		} else {
			Node node = this.root;
			while (node.next != null) {
				node = node.next;
			}
			node.next = new Node(data);
		}
		return this;
	}

	public GenericSingleLinkedList<T> appendToHead(T data) {
		Node newNode = new Node(data);
		newNode.next = this.root;
        if (this.root == null) {
			this.nodeWithMinData = newNode;
		}
        this.root = newNode;
        newNode.minBeneathThis = this.nodeWithMinData;
        if (this.nodeWithMinData.data.compareTo(newNode.data) > 0) {
        	this.nodeWithMinData = newNode;
        }

		return this;
	}

	public Node deleteNodeWithData(T data) {
		Node toBeDeletedNode = null;
		Node currentNode = this.root;

		if (currentNode.data.equals(data)) {
			toBeDeletedNode = this.root;
			this.root = this.root.next;
			return toBeDeletedNode;
		}

		while (currentNode.next != null) {
			if (currentNode.next.data.equals(data)) {
				toBeDeletedNode = currentNode.next;
				currentNode.next = currentNode.next.next;
				break;
			}
		}
		return toBeDeletedNode;
	}

	public Node deleteFromHead() throws EmptyDataStructureException {
		if (this.root == null) throw new EmptyDataStructureException();
		if (this.nodeWithMinData == this.root) {
			this.nodeWithMinData = this.root.minBeneathThis;
		}
		Node toBeDeletedNode = this.root;
		this.root = this.root.next;
		if (this.root == null) {
			this.nodeWithMinData = null;
		}
		return toBeDeletedNode;
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