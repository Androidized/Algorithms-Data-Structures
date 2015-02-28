package com.example.data;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Graph {
	private Set<Edge> mEdges;
	private Set<Vertex> mVertices;

	Graph() {
		this.mEdges = new HashSet<Edge>();
		this.mVertices = new HashSet<Vertex>();
	}

	Graph(Set<Edge> edges, Set<Vertex> vertices)
			throws IllegalArgumentException {

		if (edges == null || vertices == null)
			throw new IllegalArgumentException();

		for (Edge e : edges) {
			if (!vertices.contains(e.head) || !vertices.contains(e.tail))
				throw new IllegalArgumentException();
		}

		this.mEdges = edges;
		this.mVertices = vertices;
	}

	static class Edge {
		public Vertex head;
		public Vertex tail;

		Edge(Vertex head, Vertex tail) {
			this.head = head;
			this.tail = tail;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Edge) {
				if ((((Edge) obj).head.equals(this.head) && ((Edge) obj).tail.equals(this.tail)) ||
					(((Edge) obj).head.equals(this.tail) && ((Edge) obj).tail.equals(this.head))) {
					return true;
				}
			}
			return false;
		}
	}

	static class Vertex {
		int id;

		Vertex (int id) {
			this.id = id;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Vertex) {
				if (((Vertex) obj).id == this.id) {
					return true;
				} else return false;
			} else return false;
		}

		@Override
		public String toString() {
			return Integer.toString(this.id);
		}
	}

	public Graph addEdge(Edge e) {
		if (e == null) return this;

		boolean foundHead = false;
		boolean foundTail = false;
		for (Vertex v : mVertices) {
			if (e.head.equals(v)) {
				foundHead = true;
				continue;
			}
			if (e.tail.equals(v)) {
				foundTail = true;
				continue;
			}
		}

		if (foundHead && foundTail)
			this.mEdges.add(e);

		return this;
	}

	public void listEdges() {
		for (Edge e : mEdges) {
			System.out.println("Edge: " + e.head.toString() + " to " + e.tail.toString());
		}
	}

	public void listVetices() {
		for (Vertex v : mVertices) {
			System.out.println("Vertex: " + v.toString());
		}
	}

	public Set<Edge> getEdgesToVertex(Vertex v) {
		Set<Edge> set = new HashSet<Edge>();
		for (Edge e : mEdges) {
			if (e.head.equals(v) || e.tail.equals(v)) {
				set.add(e);
			}
		}
		return set;
	}

	private boolean hasPath(Vertex begin, Vertex end, Set<Edge> unExploredEdges) {
		Stack<Vertex> adjacentVertices = new Stack<Vertex>();

		for (Edge e : unExploredEdges) {
			if ((e.head.equals(end) && e.tail.equals(begin)) ||
				(e.tail.equals(end) && e.head.equals(begin))) {
				System.out.println("Edge: " + e.head.toString() + " <---> " + e.tail.toString());
				return true;
			}
			if (e.head.equals(begin)) {
				adjacentVertices.push(e.tail);
				continue;
			}
			if (e.tail.equals(begin)) {
				adjacentVertices.add(e.head);
				continue;
			}
		}

		Set<Edge> remainingEdges;
		while (!adjacentVertices.isEmpty()) {
			Vertex v = adjacentVertices.pop();
			for (Edge e : unExploredEdges) {
				if ((e.head.equals(begin) && e.tail.equals(v)) ||
					(e.head.equals(v) && e.tail.equals(begin))) {
					remainingEdges = new HashSet<Edge>(unExploredEdges);
					Edge edgeToRemove = null;
					for (Edge edge : remainingEdges) {
						if (edge.equals(e)) edgeToRemove = edge;
					}
					remainingEdges.remove(edgeToRemove);
					if (this.hasPath(v, end, remainingEdges)) {
						System.out.println("Edge: " + v.toString() + " <---> " + begin.toString());
						return true;
					}
				}
			}
		}

		return false;
	}

	public boolean hasPath(Vertex begin, Vertex end) {
		return hasPath(begin, end, this.mEdges);
	}
}
