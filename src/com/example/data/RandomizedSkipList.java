package com.example.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class RandomizedSkipList<T extends Comparable<? super T>> {
	private Node header;
	private Node sentinel;
	private int listLevel;
	private int numberOfNodes;

	public RandomizedSkipList() {
		// Set both header and sentinel's level to "-1".
		// Set both header and sentinel's data to null.
		this.header = new Node(null, -1);
		this.sentinel = new Node(null, -1);
		// Let header point to sentinel when the list is empty.
		// When the last node is removed from the list, let the
		// header point to sentinel again.
		this.header.nextNodes.put(1, this.sentinel);
		// An empty list has always a level equal to "0".
		this.listLevel = 0;
		this.numberOfNodes = 0;
	}

	class Node {
		T data;
		int level;
		Map<Integer, Node> nextNodes;

		Node(T data, int level) {
			this.data = data;
			this.level = level;
			this.nextNodes = new HashMap<Integer, Node>();
		}
	}

	public NodeEntry<Boolean, Node> find(T data) {

		// Return if the list is empty.
		if (this.numberOfNodes == 0)
			// Return the header as the second parameter
			// to indicate that the returned false was
			// as a result of querying data to an empty
			// list.
			return new NodeEntry<Boolean, Node>(false, this.header);

		// Begin with the header and traverse the list
		// using right pointers at every visited node.
		Node currentNode = this.header;
		Node nodeToCompare = null;
		Node leftNeighbor = null;
		int currentLevel = this.listLevel;
		while (currentLevel > 1) {
			nodeToCompare = currentNode.nextNodes.get(currentLevel);
			if (nodeToCompare != null) {
				if (data.compareTo(nodeToCompare.data) > 0) {
					currentNode = nodeToCompare;
				} else if (data.compareTo(nodeToCompare.data) < 0) {
					currentLevel--;
				} else
					return new NodeEntry<Boolean, Node>(true, nodeToCompare);
			} else currentLevel--;
		}

		// If the query value is found to be the smallest in the
		// list, we'd hit the bottom of the list through header.
		// Return the header as the left hand neighbor for the
		// position where the query data can be inserted later.
		if (currentNode.equals(this.header) &&
			currentNode.nextNodes.get(1).data.compareTo(data) >= 0) {
			return new NodeEntry<Boolean, Node>(false, this.header);
		}

		// Otherwise, continue traversing the remaining nodes
		// until the last visited node's data value is found
		// to be equal to or greater than the query.
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
				currentNode = currentNode.nextNodes.get(1);
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
		Node leftNeighbor;
		Node nodeToInsert;

		NodeEntry<Boolean, Node> entry = find(data);
		// Return the node in the list that has identical
		// data value as the query.
		if (entry.getKey()) return entry.getValue();
		else {
			// Get a handle to the node that is going to be
			// the immediate left neighbor of the yet to be
			// inserted node. This node could be the header.
			leftNeighbor = entry.getValue();

			if (leftNeighbor.equals(this.header)) {
				// The yet to be added node could be either
				// the first node inserted into the list or
				// the one with the smallest data value.
				if (this.numberOfNodes == 0) {
					// This is the first node added to the list.
					// Let this node point to the sentinel.
					nodeToInsert = new Node(data, 1);
					nodeToInsert.nextNodes.put(1, this.sentinel);
				} else {
					// This is a node with smallest data value in the list.
					nodeToInsert = new Node(data,
							new Random(2 * this.listLevel - 1).nextInt() + 1);
					// Let this node point to where the header used to point.
					nodeToInsert.nextNodes.put(1, this.header.nextNodes.get(1));
					for (int i = nodeToInsert.level; i > 0; i--) {
						this.header.nextNodes.remove(i);
					}
					this.header.nextNodes.put(nodeToInsert.level, nodeToInsert);
					// Update the inserted node's pointer relation with
					// all right hand neighbors.
					updatePointersToRight(nodeToInsert);
				}
				// Let the header point to it at level "1".
				this.header.nextNodes.put(1, nodeToInsert);
			} else {
				nodeToInsert = new Node(data,
						new Random(2 * this.listLevel - 1).nextInt() + 1);
				// Let the node point to where the left neighbor used to point.
				nodeToInsert.nextNodes
					.put(1, leftNeighbor.nextNodes.get(1));
				// Update the inserted node's pointer relation with
				// all right hand side neighbors.
				updatePointersToRight(nodeToInsert);
				// Ensure the left hand side neighbors correctly point
				// to the inserted node if necessary.
				updatePointersFromLeft(nodeToInsert);
			}

			// If the added node has the highest level,
			// update the skip list level.
			if (this.listLevel < nodeToInsert.level) {
				this.listLevel = nodeToInsert.level;
				this.header.nextNodes.put(nodeToInsert.level, nodeToInsert);
			}

			this.numberOfNodes++;
			return nodeToInsert;
		}
	}

	private void updatePointersToRight(final Node nodeToInsert) {
		Node currentNode;
		// Ensure the inserted node points to proper right hand neighbors.
		// Ensure whatever level smaller than that of the inserted node,
		// which is ever encountered, is recorded appropriately.
		int lowestLevelPointed = -1;
		currentNode = nodeToInsert.nextNodes.get(1);
		while (!currentNode.equals(this.sentinel)) {
			if (currentNode.level >= nodeToInsert.level) {
				// This is a blocking right-hand neighbor; therefore,
				// update the inserted node's pointer and terminate.
				nodeToInsert.nextNodes.put(nodeToInsert.level, currentNode);
				return;
			} else {
				// If this is a non-blocking right-hand neighbor,
				// update the inserted node's pointer but continue.
				if (lowestLevelPointed == -1 ||
					lowestLevelPointed < currentNode.level) {
					lowestLevelPointed = currentNode.level;
					nodeToInsert.nextNodes.put(currentNode.level, currentNode);
				}
			}
			currentNode = currentNode.nextNodes.get(1);
		}
	}

	private void updatePointersFromLeft(final Node nodeToInsert) {
		Node nodeToCompare;
		Node currentNode = this.header;
		int currentLevel = this.listLevel;
		while (currentLevel > 0) {
			nodeToCompare = currentNode.nextNodes.get(currentLevel);
			if (nodeToCompare != null) {
				if (currentLevel > nodeToInsert.level) {
					if (nodeToCompare.data.compareTo(nodeToInsert.data) > 0) {
						currentLevel--;
					} else if (nodeToCompare.data.compareTo(nodeToInsert.data) < 0) {
						currentNode = nodeToCompare;
					} // The equality condition should never occur.
				} else {
					if (nodeToCompare.data.compareTo(nodeToInsert.data) > 0) {
						// In some cases, the following may lead to duplicate
						// pointers to the same right hand side neighbor.
						currentNode.nextNodes.put(currentLevel, nodeToInsert);
						currentLevel--;
					} else if (nodeToCompare.data.compareTo(nodeToInsert.data) < 0) {
						currentNode = nodeToCompare;
					} // The equality condition should never occur.
				}
			} else {
				if (currentLevel <= nodeToInsert.level) {
					// In cases, the following possibly leads to several
					// duplicate pointers to the same right hand neighbor
					// if the inserted node is to be last node in the list,
					// and the current node is its left hand side neighbor.
					currentNode.nextNodes.put(currentLevel, nodeToInsert);
				}
				currentLevel--;
			}
		}
	}

	public void delete(T data) {
		boolean metNodeToRemove = false;
		NodeEntry<Boolean, Node> entry = find(data);
		if (!entry.getKey()) return;
		else {
			Node nodeToRemove = entry.getValue();
			Node nodeToCompare = null;
			Node currentNode = this.header;
			int currentLevel = this.listLevel;

			while (currentLevel > 0) {
				nodeToCompare = currentNode.nextNodes.get(currentLevel);
				if (nodeToCompare != null) {
					if (nodeToCompare.data.compareTo(nodeToRemove.data) > 0) {
						currentLevel--;
					} else if (nodeToCompare.data.compareTo(nodeToRemove.data) < 0) {
						currentNode = nodeToCompare;
						metNodeToRemove = false;
					} else { // That is nodeToCompare == nodeToRemove.
						metNodeToRemove = true;
						if (nodeToCompare.nextNodes.get(currentLevel) != null) {
							currentNode.nextNodes.put(currentLevel,
									nodeToCompare.nextNodes.get(currentLevel));
						}
						currentLevel--;
					}
				} else {
					if (metNodeToRemove) {
						if (nodeToRemove.nextNodes.get(currentLevel) != null) {
							currentNode.nextNodes.put(currentLevel,
									nodeToRemove.nextNodes.get(currentLevel));
						}
					}
					currentLevel--;
				}
			}
		}
	}

	private void removeDuplicatePointers(final Node node) {
		Node previousPointer = null;
		for (int i = node.level; i > 0; i--) {
			if (node.nextNodes.get(i) != null) {
				if (previousPointer != null) {
					if (previousPointer.equals(node.nextNodes.get(i))) {
						node.nextNodes.remove(i);
					} else {
						previousPointer = node.nextNodes.get(i);
					}
				} else {
					previousPointer = node.nextNodes.get(i);
				}
			}
		}
	}
}
