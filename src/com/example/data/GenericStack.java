package com.example.data;

import java.util.EmptyStackException;

public class GenericStack<T> {

	public GenericStack() {
		
	}

	class Element {
		T data;
		Element next;

		public Element(T data, Element next) {
			this.data = data;
			this.next = next;
		}

		public Element(T data) {
			this.data = data;
			this.next = null;
		}
	}

	private Element root = null;

	public synchronized void push(T data) throws NullPointerException {
		if (data == null) throw new NullPointerException();
		if (this.root == null) {
			this.root = new Element(data);
			this.root.next= this.root;
		} else {
			Element element = new Element(data, this.root.next);
			this.root.next = element;
		}
	}

	public synchronized T pop() throws EmptyStackException {
		if (this.root == null) throw new EmptyStackException();

		T data = this.root.next.data;

		if (this.root.equals(this.root.next)) {
			this.root = null;
		} else {
			this.root.next = this.root.next.next;			
		}

		return data;
	}

	public synchronized T peek() {
		if (this.root == null) return null;
		else return this.root.next.data;
	}

	public boolean isEmpty() {
		return (this.root == null);
	}
}