package com.example.data;

public final class CircularQueue<T> {
	private SingleLinkedList list;

	CircularQueue(int size) {
		this.list = new SingleLinkedList(size);
	}

	class Element {
		T data;
		Element next;

		Element() {
			this.data = null;
		}

		Element(T data) {
			this.data = data;
		}
	}

	class SingleLinkedList {
		private Element writePointer;
		private Element readPointer;

		SingleLinkedList(int size) {
			Element root = null;
			Element current = null;
			Element previous = null;

			for (int i = 0; i < size; i++) {
				current = new Element();
				if (i == 0) root = current;
				if (previous != null) previous.next = current;
				previous = current;
			}

			previous.next = readPointer = writePointer = root;
		}

		T read() {
			T data = null;
			if (readPointer.data != null) {
				data = readPointer.data;
				readPointer.data = null;
				readPointer = readPointer.next;
			}
			return data;
		}

		void write(T data) {
			if (writePointer.data == null) {
				writePointer.data = data;
				writePointer = writePointer.next;
			}
		}

		boolean isFull() {
			if ((readPointer == writePointer) &&
				(writePointer.data != null)) return true;
			else return false;
		}

		boolean isEmpty() {
			if ((readPointer == writePointer) &&
					(readPointer.data == null))
				return true;
			else return false;
		}
	}

	public T read() {
		synchronized (this.list) {
			while (this.list.isEmpty()) {
				try {
					this.list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			T data = this.list.read();
			this.list.notify();
			return data;
		}
	}

	public void write(T data) {
		synchronized (this.list) {
			while (this.list.isFull()) {
				try {
					this.list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.list.write(data);
			this.list.notify();
		}
	}
}
