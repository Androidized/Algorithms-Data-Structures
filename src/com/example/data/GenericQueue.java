package com.example.data;

public class GenericQueue<T> {

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

	private Element queueTail = null;

	public synchronized T read() throws EmptyQueueException {
		if (this.queueTail == null) throw new EmptyQueueException();

		T data = this.queueTail.next.data;
		if (this.queueTail.next.equals(this.queueTail)) {
			this.queueTail = null;
		} else {
			this.queueTail.next = this.queueTail.next.next;
		}

		return data;
	}

	public synchronized void write(T data) {
		if (this.queueTail == null) {
			this.queueTail = new Element(data);
			this.queueTail.next = this.queueTail;
			return;
		}

		Element element = new Element(data, this.queueTail.next);
		this.queueTail.next = element;
		this.queueTail = element;
	}

	public boolean isEmpty() {
		return (this.queueTail == null);
	}
}
