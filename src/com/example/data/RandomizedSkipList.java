package com.example.data;

import java.util.List;
import java.util.ArrayList;
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
		int level;
		List<Node> rightNeighbors;

		Node() {
			this.data = null;
			this.level = 0;
			this.rightNeighbors = new ArrayList<Node>(1);
		}

		Node(T data) {
			this.data = data;
			this.level = 0;
			this.rightNeighbors = new ArrayList<Node>(1);
		}

		Node(T data, int level) {
			this.data = data;
			this.level = level;
			this.rightNeighbors = new ArrayList<Node>(level);
		}
	}

	public boolean find(T data) {
		Node nodeToCompare;
		Node currentNode = this.header;
		int currentLevel = this.header.rightNeighbors.size() - 1;
		if (currentLevel == -1) return false;
		while (true) {
			do {
				nodeToCompare = currentNode.rightNeighbors.get(currentLevel--);
			} while ((nodeToCompare.equals(sentinel) ||
					 nodeToCompare.data.compareTo(data) > 0) &&
					 currentLevel != -1);
			if (currentLevel == -1) return false;
			if (nodeToCompare.data.compareTo(data) == 0) return true;
			currentNode = nodeToCompare;
		}
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
			this.header.rightNeighbors.add(nodeToInsert);
			nodeToInsert.rightNeighbors.add(sentinel);
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
				currentNode.rightNeighbors.set(0, nodeToInsert);
				int newNodeCurrentLevel = 0;
				while (newNodeCurrentLevel <= i) {
					if (next.equals(this.sentinel)) {
						while (newNodeCurrentLevel++ <= i)
							nodeToInsert.rightNeighbors.add(this.sentinel);
						return;
					}
					if (newNodeCurrentLevel <= next.level) {
						while (newNodeCurrentLevel <= i &&
						       newNodeCurrentLevel <= next.level) {
							nodeToInsert.rightNeighbors.add(next);
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
