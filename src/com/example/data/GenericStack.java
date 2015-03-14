package com.example.data;

import java.util.EmptyStackException;

public class GenericStack<T extends Comparable<? super T>> {

	public GenericStack() {
		
	}

	class Element {
		T data;
		Element next;
		Element min;
		Element max;

		public Element(T data, Element next) {
			this.data = data;
			this.next = next;
			this.min = null;
			this.max = null;
		}

		public Element(T data) {
			this.data = data;
			this.next = null;
			this.min = null;
			this.max = null;
		}
	}

	private Element root = null;

	public synchronized void push(T data) throws NullPointerException {
		if (data == null) throw new NullPointerException();
		if (this.root == null) {
			this.root = new Element(data);
			this.root.next = this.root;
			this.root.min = this.root;
			this.root.max = this.root;
		} else {
			Element element = new Element(data, this.root.next);
			if (data.compareTo(this.root.next.min.data) < 0)
				element.min = element;
			else
				element.min = this.root.next.min;
			if (data.compareTo(this.root.next.max.data) > 0)
				element.max = element;
			else
				element.max = this.root.next.max;
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

	public T getMin() {
		if (this.root != null && this.root.next != null)
			return this.root.next.min.data;
		else if (this.root != null && this.root.next == null)
			return this.root.data;
		else return null;
	}

	public T getMax() {
		if (this.root != null && this.root.next != null)
			return this.root.next.max.data;
		else if (this.root != null && this.root.next == null)
			return this.root.data;
		else return null;
	}
}