package com.example.data;

public class DualStackQueue<T extends Comparable<? super T>> {
	private InefficientGenericStack<T> producerStack;
	private InefficientGenericStack<T> consumerStack;

	DualStackQueue() {
		producerStack = new InefficientGenericStack<T>();
		consumerStack = new InefficientGenericStack<T>();
	}

	public void write(T data) {
		synchronized (producerStack) {
			while (producerStack.isFull()) {
				try {
					producerStack.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

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

			while (!producerStack.isEmpty()) {
				consumerStack.push(producerStack.pop());
			}

			producerStack.notify();
		}
	}
}
