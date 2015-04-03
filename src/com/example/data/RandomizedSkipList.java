package com.example.data;

import java.util.ArrayList;
import java.util.List;

public final class RandomizedSkipList<T extends Comparable<? super T>> {
	BorderNode header;
	BorderNode sentinel;

	class Node {
		int level;
		T data;
		Node[] rightNeighbors;

		Node() {
			this.level = -1;
			this.data = null;
			this.rightNeighbors = null;
		}

		@SuppressWarnings("unchecked")
		Node(int level, T data) {
			this.level = level;
			this.data = data;
			this.rightNeighbors = (Node[]) new Object[level + 1];
		}

		public void setRightNeighbor(final Node rightNeiborToPoint, int level) {
			this.rightNeighbors[level] = rightNeiborToPoint;
		}
	}

	class BorderNode {
		List<Node> rightNeighbors;

		BorderNode() {
			this.rightNeighbors = new ArrayList<Node>();
		}

		public void setRightNeighbor(final Node rightNeighborToPoint) {
			this.rightNeighbors.add(rightNeighborToPoint);
		}
	}

	RandomizedSkipList() {
		this.header = new BorderNode();
		this.sentinel = new BorderNode();
	}

	public void insert(T data) {
		Node currentNode = null;
		// Begin with header. The last element in header's rightNeighbors list
		// represents the topmost pointer to an intermediate node somewhere in
		// the middle of skip list.
		int currentLevels = this.header.rightNeighbors.size();
		Node nextNodeOnRight = this.header.rightNeighbors.get(currentLevels - 1);
		if (data.compareTo(nextNodeOnRight.data) >= 0) {
			currentNode = nextNodeOnRight; 
		}
		
	}
}
