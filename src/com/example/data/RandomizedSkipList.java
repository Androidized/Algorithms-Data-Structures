package com.example.data;

import java.util.List;
import java.util.ArrayList;

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
				if (nodeToCompare.equals(sentinel)) return false;
			} while (nodeToCompare.data.compareTo(data) > 0 &&
					 currentLevel != -1);
			if (currentLevel == -1) return false;
			if (nodeToCompare.data.compareTo(data) == 0) return true;
			currentNode = nodeToCompare;
		}
	}

	public void insert(T data) {
		
	}

	public void delete(T dta) {
		
	}
}
