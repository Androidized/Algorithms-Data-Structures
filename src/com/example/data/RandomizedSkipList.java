package com.example.data;

import java.util.HashMap;
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
		NodeEntry<Boolean, Node> entry = find(data);
		if (entry.getKey()) return entry.getValue();
		else {

			// Create the node to be inserted into the list.
			Node leftNeighbor = entry.getValue();
			Random rand = new Random(2 * this.listLevel - 1);
			Node nodeToInsert = new Node(data, rand.nextInt() + 1);

			if (leftNeighbor.level > nodeToInsert.level) {
				nodeToInsert.rightNeighbors.put(1, leftNeighbor.rightNeighbors.get(1));
				nodeToInsert.rightNeighbors.put(nodeToInsert.level, leftNeighbor.rightNeighbors.get(1));
				for (int leftNeighborLevelPointer : leftNeighbor.rightNeighbors.keySet()) {
					if (leftNeighborLevelPointer <= nodeToInsert.level) {
						leftNeighbor.rightNeighbors.remove(leftNeighborLevelPointer);
					}
				}
				leftNeighbor.rightNeighbors.put(1, nodeToInsert);
				leftNeighbor.rightNeighbors.put(nodeToInsert.level, nodeToInsert);
			} else {
				nodeToInsert.rightNeighbors.put(1, leftNeighbor.rightNeighbors.get(1));
				leftNeighbor.rightNeighbors.clear();
				leftNeighbor.rightNeighbors.put(1, nodeToInsert);
				leftNeighbor.rightNeighbors.put(leftNeighbor.level, nodeToInsert);
			}

			// Update left neighbor(s) of inserted node to point to it.
			int levelToUpdateFrom = 1;
			if (leftNeighbor.level <= nodeToInsert.level) {
				levelToUpdateFrom = leftNeighbor.level;
			} else {
				levelToUpdateFrom = nodeToInsert.level;
			}
			for (int i = levelToUpdateFrom; i > 0; i--) {
				nodeToInsert.rightNeighbors.put(i, leftNeighbor.rightNeighbors.get(i));
				leftNeighbor.rightNeighbors.put(i, nodeToInsert);
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
