package com.example.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class RandomizedSkipList<T extends Comparable<? super T>> {
	Node header;
	Node sentinel;

	public RandomizedSkipList() {
		this.header = new Node();
		this.sentinel = new Node();
	}

	class Node {
		T data;
		int level; // TODO: Possibly better off removing this
		Map<Integer, Node> rightNeighbors;

		Node() {
			this.data = null;
			this.level = -1; // TODO: Possibly better off removing this
			this.rightNeighbors = new HashMap<Integer, Node>();
		}

		Node(T data) {
			this.data = data;
			this.level = -1; // TODO: Possibly better off removing this
			this.rightNeighbors = new HashMap<Integer, Node>();
		}

		Node(T data, int level) {
			this.data = data;
			this.level = level; // TODO: Possibly better off removing this
			this.rightNeighbors = new HashMap<Integer, Node>(level);
		}
	}

	public boolean find(T data) {
		Node nodeToCompare;
		Node currentNode = this.header;
		int currentLevel = getHighestLevel(this.header.rightNeighbors);
		if (currentLevel == -1) return false;
		while (true) {
			do {
				while (!currentNode.rightNeighbors.containsKey(currentLevel) &&
						currentLevel != -1) currentLevel--;
				if (currentLevel != -1)
					nodeToCompare = currentNode.rightNeighbors.get(currentLevel--);
				else return false;
			} while (nodeToCompare.equals(sentinel) ||
					 nodeToCompare.data.compareTo(data) > 0);
			if (nodeToCompare.data.compareTo(data) == 0) return true;
			currentNode = nodeToCompare;
		}
	}

	private int getHighestLevel(final Map<Integer, Node> headerRightNeighbors) {
		if (headerRightNeighbors.isEmpty()) return -1;
		int highestLevel = 0;
		for (Integer level : headerRightNeighbors.keySet()) {
			if (highestLevel < level) highestLevel = level;
		}
		return highestLevel;
	}

	public void insert(T data) {
		Node nodeToInsert;
		Node nodeToCompare;
		Node currentNode = this.header;
		int currentLevel = this.header.rightNeighbors.size() - 1;
		if (currentLevel == -1) {
			// When the skip list is empty,
			// do not randomize the insertion.
			nodeToInsert = new Node(data);
			this.header.rightNeighbors.put(0, nodeToInsert);
			nodeToInsert.rightNeighbors.put(-1, sentinel);
			return;
		}
		while (true) {
			do {
				nodeToCompare = currentNode.rightNeighbors.get(currentLevel--);
			} while ((nodeToCompare.equals(sentinel) ||
					 nodeToCompare.data.compareTo(data) > 0) &&
					 currentLevel != -1);
			if (currentLevel == -1) {
				Node next = currentNode.rightNeighbors.get(0);
				int i = 0;
				Random rand = new Random();
				while (rand.nextInt() > 0.5) i++;
				nodeToInsert = new Node(data, i);
				currentNode.rightNeighbors.put(0, nodeToInsert);
				int newNodeCurrentLevel = 0;
				while (newNodeCurrentLevel <= i) {
					if (next.equals(this.sentinel)) {
						while (newNodeCurrentLevel++ <= i)
							nodeToInsert.rightNeighbors.put(-1, this.sentinel);
						return;
					}
					if (newNodeCurrentLevel <= next.level) {
						while (newNodeCurrentLevel <= i &&
						       newNodeCurrentLevel <= next.level) {
							nodeToInsert.rightNeighbors.put(next.level, next);
							newNodeCurrentLevel++;
						}
					}
					next = next.rightNeighbors.get(0);
				}
				return;
			}
			if (nodeToCompare.data.compareTo(data) == 0) return;
			currentNode = nodeToCompare;
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
