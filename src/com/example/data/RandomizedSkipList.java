package com.example.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class RandomizedSkipList<T extends Comparable<? super T>> {
	private Node header;
	private Node sentinel;
	int listLevel;

	public RandomizedSkipList() {
		this.header = new Node(null, -1);
		this.sentinel = new Node(null, -1);
		this.listLevel = 0;
	}

	class Node {
		T data;
		int level;
		Map<Integer, Node> rightNeighbors;

		Node(T data, int level) {
			this.data = data;
			this.level = level;
			this.rightNeighbors = new HashMap<Integer, Node>();
		}
	}

	public NodeEntry<Boolean, Node> find(T data) {
		Node nodeToCompare = null;

		// Return false if there is nothing in the list.
		if (this.listLevel == 0) return new NodeEntry<Boolean, Node>(false, null);

		// Begin with the header.
		Node currentNode = this.header;
		int currentLevel = this.listLevel;

		while (currentLevel > 0) {
			nodeToCompare = currentNode.rightNeighbors.get(currentLevel);
			if (nodeToCompare != null) {
				if (data.compareTo(nodeToCompare.data) > 0) {
					currentNode = nodeToCompare;
				} else if (data.compareTo(nodeToCompare.data) < 0) {
					currentLevel--;
				} else return new NodeEntry<Boolean, Node>(true, nodeToCompare);
			} else currentLevel--;
		}

		// Return the node immediately before the possible
		// position where the data could have been found.
		// This can be used for inserting a new node to the
		// list containing the same data value as the query.
		return new NodeEntry<Boolean, Node>(false, currentNode);
	}

	public Node insert(T data) {
		int currentLevel;
		Node currentNode;
		Node nodeToInsert;
		Node nodeToCompare;
		Node lastNodeTraversedDown;
		NodeEntry<Boolean, Node> entry = find(data);
		// Return the node that has identical
		// data value as the input argument.
		if (entry.getKey()) return entry.getValue();
		else {
			// Get a handle to the node that is going to be
			// the immediate left neighbor of the yet to be
			// inserted node. 
			Node leftNeighbor = entry.getValue();
			// Create the new node to be inserted into the list.
			Random rand = new Random(2 * this.listLevel - 1);
			nodeToInsert = new Node(data, rand.nextInt() + 1);
			nodeToInsert.rightNeighbors
				.put(1, leftNeighbor.rightNeighbors.get(1));
			// Ensure the inserted node points to a right-hand
			// neighbor, whose level is greater than or equal
			// to that of the inserted node.
			currentNode = nodeToInsert;
			// This is to ensure whatever level smaller than
			// that of the inserted node is encountered will
			// be recorded.
			int lowestLevelPointed = -1;
			while (!currentNode.equals(sentinel)) {
				if (currentNode.level >= nodeToInsert.level) {
					nodeToInsert.rightNeighbors.put(nodeToInsert.level, currentNode);
					break;
				} else {
					if (lowestLevelPointed == -1 ||
						lowestLevelPointed < currentNode.level) {
						lowestLevelPointed = currentNode.level;
						nodeToInsert.rightNeighbors.put(currentNode.level, currentNode);
					}
				}
				currentNode = currentNode.rightNeighbors.get(1);
			}
			// Ensure the immediately left-hand neighbor
			// correctly points to the inserted node.
			if (leftNeighbor.level >= nodeToInsert.level) {
				for (int leftNeighborLevelPointer :
					 leftNeighbor.rightNeighbors.keySet()) {
					if (leftNeighborLevelPointer <= nodeToInsert.level) {
						leftNeighbor.rightNeighbors
							.remove(leftNeighborLevelPointer);
					}
				}
				leftNeighbor.rightNeighbors.put(1, nodeToInsert);
				leftNeighbor.rightNeighbors.put(nodeToInsert.level, nodeToInsert);
			} else {
				leftNeighbor.rightNeighbors.clear();
				leftNeighbor.rightNeighbors.put(1, nodeToInsert);
				leftNeighbor.rightNeighbors.put(leftNeighbor.level, nodeToInsert);
				// Update nodes before the left neighbor to have
				// references to the inserted node if necessary.
				currentNode = this.header;
				currentLevel = this.listLevel;
				while (currentLevel > 1) {
					nodeToCompare = currentNode.rightNeighbors.get(currentLevel);
					if (nodeToCompare != null) {
						if (data.compareTo(nodeToCompare.data) > 0) {
							currentNode = nodeToCompare;
						} else if (data.compareTo(nodeToCompare.data) < 0) {
							lastNodeTraversedDown = currentNode;
							currentLevel--;
						}
					} else currentLevel--;
				}
			}

			return nodeToInsert;
		}
	}

	public void delete(T data) {
		Node nodeToCompare;
		Node currentNode = this.header;
		int currentLevel = this.header.rightNeighbors.size() - 1;
		if (currentLevel == -1) return;
		while (true) {
			do {
				nodeToCompare = currentNode.rightNeighbors.get(currentLevel--);
			} while ((nodeToCompare.equals(sentinel) ||
					 nodeToCompare.data.compareTo(data) > 0) &&
					 currentLevel != -1);
			if (currentLevel == -1) return;
			if (nodeToCompare.data.compareTo(data) == 0) {
				// TODO: Remove the node and link previous node to next.
				return;
			}
			currentNode = nodeToCompare;
		}
	}
}
