package com.example.data;

import java.util.EmptyStackException;

public final class GenericStackViaQueue<T> {
	private GenericQueue<T> queue;

	GenericStackViaQueue() {
		this.queue = new GenericQueue<T>();
	}

	public synchronized void push(T data) {
		this.queue.write(data);
	}

	public synchronized T pop() throws EmptyStackException {
		if (this.queue.isEmpty()) throw new EmptyStackException();

		GenericQueue<T> tempQueue = new GenericQueue<T>();
		T lastRead = null;
		while(!this.queue.isEmpty()) {
			try {
				lastRead = this.queue.read();
				if (!this.queue.isEmpty()) tempQueue.write(lastRead);
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		}

		while (!tempQueue.isEmpty()) {
			try {
				this.queue.write(tempQueue.read());
			} catch (EmptyQueueException e) {
				e.printStackTrace();
			}
		}

		return lastRead;
	}

	public synchronized boolean isEmpty() {
		return queue.isEmpty();
	} 
}
