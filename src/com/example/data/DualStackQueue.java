package com.example.data;

public class DualStackQueue<T extends Comparable<? super T>> {
	private GenericStack<T> producerStack;
	private GenericStack<T> consumerStack;

	DualStackQueue() {
		producerStack = new GenericStack<T>();
		consumerStack = new GenericStack<T>();
	}

	public void write(T data) {
		synchronized (producerStack) {
			producerStack.push(data);
			producerStack.notify();
		}
	}

	public T read() {
		if (consumerStack.isEmpty()) {
			transfer();
		}

		return consumerStack.pop();
	}

	private void transfer() {
		synchronized (producerStack) {
			while (producerStack.isEmpty()) {
				try {
					producerStack.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			do {
				consumerStack.push(producerStack.pop());
			} while (!producerStack.isEmpty());

			producerStack.notify();
		}
	}
}
