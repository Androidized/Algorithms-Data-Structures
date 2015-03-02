package com.example.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.example.data.GenericBinaryHeap.Type;
import com.example.data.Graph.Edge;
import com.example.data.Graph.Vertex;
import com.example.sort.GenericSortEngine;

@SuppressWarnings("unused")
public class Main {
	public static void main(String[] args) {
		GenericSortEngine<Integer> engine = new GenericSortEngine<Integer>();
		Integer firstArray[] = new Integer[]{1, 2, 3, 4, 5};
		Integer secondArray[] = new Integer[]{2, 7, 8, 9};
		engine.sortAndDistribute(firstArray, secondArray);
		for (int i = 0; i < firstArray.length; i++)
			System.out.print(firstArray[i] + " ");
		System.out.println("");
		for (int i = 0; i < secondArray.length; i++)
			System.out.print(secondArray[i] + " ");

//		GenericBinaryHeap<Integer> heap = new GenericBinaryHeap<Integer>(Type.MIN);
//		heap.root = heap.new Element(1);
//		
//		heap.root.leftChild = heap.new Element(2);
//		heap.root.rightChild = heap.new Element(3);
//		
//		heap.root.leftChild.leftChild = heap.new Element(4);
//		heap.root.leftChild.rightChild = heap.new Element(5);
//		
//		heap.root.rightChild.leftChild = heap.new Element(6);
//		heap.root.rightChild.rightChild = heap.new Element(7);
//		
//		heap.root.leftChild.leftChild.leftChild = heap.new Element(8);
//		heap.root.leftChild.leftChild.rightChild = heap.new Element(9);
//		
//		heap.root.leftChild.rightChild.leftChild = heap.new Element(10);
//		// heap.root.leftChild.rightChild.rightChild = heap.new Element(11);
//		
//		heap.insert(0);
//		
//		System.out.println("");
//		GenericBinaryHeap<Integer>.DeepestRightmostElement dre = heap.getDeepestRightmostElement();
//		System.out.println("Deepest & Rightmost Element: " + dre.deepestRightmostElement.data.toString());
//		System.out.println("Parent Of Next Available Position: " + dre.parentOfNextAvailablePosition.data.toString());
		
//		GenericQueue<Integer> queue = new GenericQueue<Integer>();
//		queue.write(1);
//		queue.write(2);
//		queue.write(3);
//		queue.write(4);
//		queue.write(5);
//		queue.write(6);
//		queue.write(7);
//		while (!queue.isEmpty()) {
//			try {
//				System.out.println(queue.read().toString());
//			} catch (EmptyQueueException e) {
//				e.printStackTrace();
//			}
//		}
		
//		GenericStackViaQueue<Integer> stack = new GenericStackViaQueue<Integer>();
//		stack.push(1);
//		stack.push(2);
//		stack.push(3);
//		stack.push(4);
//		stack.push(5);
//		
//		while (!stack.isEmpty()) {
//			System.out.println(stack.pop().toString());
//		}
		
//		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
//		System.out.println("Inserting 10: " + bst.insert(10));
//		System.out.println("Inserting 9: " + bst.insert(9));
//		System.out.println("Inserting 14: " + bst.insert(14));
//		System.out.println("Inserting 4: " + bst.insert(4));
//		System.out.println("Inserting 2: " + bst.insert(2));
//		System.out.println("Inserting 3: " + bst.insert(3));
//		System.out.println("Inserting 1: " + bst.insert(1));
//		System.out.println("Inserting 5: " + bst.insert(5));
//		System.out.println("Inserting 7: " + bst.insert(7));
//		System.out.println("Inserting 12: " + bst.insert(12));
////		
//		Iterator<Integer> it = bst.iterator();
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.println("");
//		
//
//		System.out.println("Next in order traverse for " + 4 + " is " + bst.getNextInOrderTraverse(4).data.toString());
//		bst.delete(4);
//
//		it = bst.iterator();
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");
//		System.out.print(it.next() + " ");

//		GenericStack<Integer> stack = new GenericStack<Integer>();
//		stack.push(10);
//		stack.push(1);
//		stack.push(9);
//		stack.push(12);
//		stack.push(0);
//		stack.push(13);
//		stack.push(4);
//		stack.sortDecending();
//		while (!stack.isEmpty()) {
//			System.out.println(stack.pop());
//		}
		
//		Set<Vertex> vertices = new HashSet<Vertex>();
//		Vertex v0 = new Vertex(0);
//		Vertex v1 = new Vertex(1);
//		Vertex v2 = new Vertex(2);
//		Vertex v3 = new Vertex(3);
//		Vertex v4 = new Vertex(4);
//		Vertex v5 = new Vertex(5);
//		Vertex v6 = new Vertex(6);
//		
//		vertices.add(v0);
//		vertices.add(v1);
//		vertices.add(v2);
//		vertices.add(v3);
//		vertices.add(v4);
//		vertices.add(v5);
//		vertices.add(v6);
//		
//		Set<Edge> edges = new HashSet<Edge>();
//		edges.add(new Edge(v1,v0));
//		edges.add(new Edge(v2,v0));
//		edges.add(new Edge(v3,v0));
//		edges.add(new Edge(v4,v0));
//		edges.add(new Edge(v2,v5));
//		edges.add(new Edge(v2,v6));
//		// edges.add(new Edge(v1,v6));
//		
//		Graph graph = new Graph(edges, vertices);
//		System.out.println(graph.hasPath(v1, v6));
		
//		final CircularQueue<Integer> queue = new CircularQueue<Integer>(10);
//		Thread reader = new Thread() {
//			@Override
//			public void run() {
//				for (int i = 0; i < 10; i++) {
//					System.out.println("Read <<< " + queue.read());
//				}
//			}
//		};
//
//		Thread writer = new Thread() {
//			@Override
//			public void run() {
//				for (int i = 0; i < 10; i++) {
//					DebugHelper.wait(1000);
//					queue.write(i);
//					System.out.println("Wrote >>> " + i);
//				}
//			}
//		};
//		reader.start();
//		writer.start();
//		try {
//			reader.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		try {
//			writer.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
}