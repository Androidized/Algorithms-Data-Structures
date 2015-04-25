package com.example.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class RandomizedSkipList<T extends Comparable<? super T>> {
	private Node header;
	private Node sentinel;
	private int listLevel;

	public RandomizedSkipList() {
		// Set both header and sentinel's level to "-1".
		// Set both header and sentinel's data to null.
		this.header = new Node(null, -1);
		this.sentinel = new Node(null, -1);
		// Let header point to sentinel when the list is empty.
		// When the last node is removed from the list, let the
		// header point to sentinel again.
		this.header.rightNeighbors.put(1, this.sentinel);
		// An empty list has always a level equal to "0".
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

		// Return if the list is empty.
		if (this.listLevel == 0)
			// Return the header as the second parameter
			// to indicate that the returned false was
			// as the result of querying the data to an
			// empty list.
			return new NodeEntry<Boolean, Node>(false, this.header);

		// Begin with the header and traverse the list
		// using the right pointers at each visited node.
		Node currentNode = this.header;
		Node nodeToCompare = null;
		Node leftNeighbor = null;
		int currentLevel = this.listLevel;
		while (currentLevel > 1) {
			nodeToCompare = currentNode.rightNeighbors.get(currentLevel);
			if (nodeToCompare != null) {
				if (data.compareTo(nodeToCompare.data) > 0) {
					currentNode = nodeToCompare;
				} else if (data.compareTo(nodeToCompare.data) < 0) {
					currentLevel--;
				} else
					return new NodeEntry<Boolean, Node>(true, nodeToCompare);
			} else currentLevel--;
		}

		// If the query value is the smallest in the entire list,
		// return the header as the immediate left hand neighbor
		// of the position where the data may be inserted. 
		if (currentNode.equals(this.header) &&
			currentNode.rightNeighbors.get(1).data.compareTo(data) >= 0) {
			return new NodeEntry<Boolean, Node>(false, this.header);
		}

		// After hitting the bottom of the list, continue
		// traversing the remaining nodes until the last
		// visited node's data value is found equal to or
		// greater than the query.
		while (!currentNode.equals(this.sentinel)) {
			if (currentNode.data.compareTo(data) > 0) {
				// Return the node immediately before the possible
				// position where the data could have been found.
				// This can be used for inserting a new node to the
				// list containing the same data value as the query.
				return new NodeEntry<Boolean, Node>(false, leftNeighbor);
			} else if (currentNode.data.compareTo(data) < 0) {
				// This is where we enter this while loop,
				// so we protect ourselves from losing the
				// track of the immediate left neighbor.
				leftNeighbor = currentNode;
				currentNode = currentNode.rightNeighbors.get(1);
			} else {
				return new NodeEntry<Boolean, Node>(true, currentNode);
			}
		}

		// After reaching the sentinel, return the last node
		// in the list as the position where the data may be
		// inserted into the list.
		return new NodeEntry<Boolean, Node>(false, leftNeighbor);
	}

	public Node insert(T data) {
		int currentLevel;
		Node leftNeighbor;
		Node currentNode;
		Node nodeToInsert;
		Node nodeToCompare;
		Node lastNodeTraversedDown = null;
		NodeEntry<Boolean, Node> entry = find(data);
		// Return the node that has identical
		// data value as the input argument.
		if (entry.getKey()) return entry.getValue();
		else {
			// Get a handle to the node that is going to be
			// the immediate left neighbor of the yet to be
			// inserted node. 
			leftNeighbor = entry.getValue();
			if (leftNeighbor.equals(this.header)) {
				// Set the level of the first inserted node
				// to the list to "1". Let the header point
				// to this node, while letting this node point
				// to the sentinel.
				nodeToInsert = new Node(data, 1);
				nodeToInsert.rightNeighbors.put(1, this.sentinel);
				this.header.rightNeighbors.put(nodeToInsert.level, nodeToInsert);
				this.header.rightNeighbors.put(1, nodeToInsert);
				return nodeToInsert;
			} else {
				Random rand = new Random(2 * this.listLevel - 1);
				nodeToInsert = new Node(data, rand.nextInt() + 1);
				// Let this node point to where the left neighbor used
				// to point, while letting the left neighbor point to
				// this node.
				nodeToInsert.rightNeighbors
					.put(1, leftNeighbor.rightNeighbors.get(1));
				// Ensure the inserted node points to a right-hand
				// neighbor, whose level is greater than or equal
				// to that of the inserted node.
				currentNode = nodeToInsert.rightNeighbors.get(1);
				// This is to ensure whatever level smaller than
				// that of the inserted node that is encountered
				// will be recorded.
				int lowestLevelPointed = -1;
				while (!currentNode.equals(sentinel)) {
					if (currentNode.level >= nodeToInsert.level) {
						// This is a blocking right-hand neighbor, thus,
						// update the inserted node's pointer and break.
						nodeToInsert.rightNeighbors.put(nodeToInsert.level, currentNode);
						break;
					} else {
						// If this is a non-blocking right-hand neighbor,
						// update the inserted node's pointer but continue.
						if (lowestLevelPointed == -1 ||
							lowestLevelPointed < currentNode.level) {
							lowestLevelPointed = currentNode.level;
							nodeToInsert.rightNeighbors.put(currentNode.level, currentNode);
						}
					}
					currentNode = currentNode.rightNeighbors.get(1);
				}
				// Ensure the immediate left-hand neighbor
				// correctly points to the inserted node. If
				// the this node has higher level than the
				// inserted one, it is a blocking node; thus,
				// no need to update the rest of left-hand
				// neighbors.
				if (leftNeighbor.level >= nodeToInsert.level) {
					for (int leftNeighborLevelPointer :
						 leftNeighbor.rightNeighbors.keySet()) {
						if (leftNeighborLevelPointer <= nodeToInsert.level) {
							leftNeighbor.rightNeighbors
								.remove(leftNeighborLevelPointer);
						}
					}
					leftNeighbor.rightNeighbors.put(1, nodeToInsert);
					leftNeighbor.rightNeighbors
						.put(nodeToInsert.level, nodeToInsert);
				} else {
					leftNeighbor.rightNeighbors.clear();
					leftNeighbor.rightNeighbors.put(1, nodeToInsert);
					leftNeighbor.rightNeighbors.put(leftNeighbor.level, nodeToInsert);
					// Update all nodes before the left neighbor to have
					// proper pointers to the inserted node if necessary.
					currentNode = this.header;
					// We haven't yet updated the list level if the new
					// node has greater level than the current list level.
					// This makes it possible to update the existing nodes
					// that are on the left hand side of the new node.
					currentLevel = this.listLevel;
					while (currentLevel > 1) {
						nodeToCompare = currentNode.rightNeighbors.get(currentLevel);
						if (nodeToCompare != null) {
							if (data.compareTo(nodeToCompare.data) > 0) {
								currentNode = nodeToCompare;
							} else if (data.compareTo(nodeToCompare.data) < 0) {
								lastNodeTraversedDown = currentNode;
								currentLevel--;
							} else {
								
							}
						} else currentLevel--;
					}
					if (lastNodeTraversedDown != null) {
						for (int i = nodeToInsert.level; i > 1; i--) {
							nodeToCompare = lastNodeTraversedDown.rightNeighbors.get(i);
							if (nodeToCompare != null && nodeToCompare.data.compareTo(data) >= 0) {
								lastNodeTraversedDown.rightNeighbors
									.put(nodeToInsert.level, nodeToInsert);
							}
						}
					}
				}
			}

			// If the added node is has the highest,
			// level let the header point to it, too.
			if (this.listLevel < nodeToInsert.level) {
				this.listLevel = nodeToInsert.level;
				this.header.rightNeighbors.put(nodeToInsert.level, nodeToInsert);
			}
			return nodeToInsert;
		}
	}

	public void delete(T data) {
		NodeEntry<Boolean, Node> entry = find(data);
		if (!entry.getKey()) return;
		else {
			Node nodeToRemove = null;
			Node nodeToCompare = null;
			Node leftNeighbor = this.header;
			Node currentNode = this.header;
			int currentLevel = this.listLevel;
			while (currentLevel > 0) {
				do {
					nodeToCompare = currentNode.rightNeighbors.get(currentLevel--);
				} while ((nodeToCompare.equals(sentinel) ||
						 nodeToCompare.data.compareTo(data) > 0) &&
						 currentLevel != -1);
				if (currentLevel == -1) break;
				if (nodeToCompare.data.compareTo(data) == 0) {
					// TODO: Remove the node and link previous node to next.
					break;
				}
				currentNode = nodeToCompare;
			}

			if (nodeToRemove != null &&
				leftNeighbor.equals(this.header) &&
				nodeToRemove.rightNeighbors.get(1).equals(this.sentinel)) {
				this.header.rightNeighbors.put(1, this.sentinel);
				// Reset the list level to "0" only when
				// it becomes empty. Tracking the list
				// level in cases other than this particular
				// scenario is rather complex, which is
				// avoided purposely.
				this.listLevel = 0;
			}
		}
	}
}

