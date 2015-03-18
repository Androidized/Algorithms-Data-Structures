package com.example.data;

import com.example.data.exception.EmptyDataStructureException;

public class InefficientGenericStack<T extends Comparable<? super T>> {
	private GenericSingleLinkedList<T> stack;
	private static final boolean DBG = false;

	public InefficientGenericStack() {
		this.stack = new GenericSingleLinkedList<T>();
	}

	public T pop() {
		try {
			return stack.deleteFromHead().data;
		} catch (EmptyDataStructureException e) {
			return null;
		}
	}

	public T peek() {
		if (!stack.isEmpty()) {
			return stack.peek().data;
		} else return null;
	}

	public void push(T data) {
		stack.appendToHead(data);
	}

	public void printContent() {
		stack.printContent();
	}

	public void getMinData() {
		T data = stack.getNodeWithMinData().data;
		System.out.println("Min data value: " + data.toString());
	}

	public void sortDecending() {
		InefficientGenericStack<T> backup = new InefficientGenericStack<T>();
		T localMaxima = this.peek();
		boolean localMaximaFound = false;
		boolean localMaximaPushed = false;
		boolean isScanComplete = false;

		do {
			System.out.println("Cycling through source...");
			if (DBG) DebugHelper.wait(1000);

			localMaximaFound = false;
			while (!this.isEmpty() && !backup.isFull()) {
				if (DBG) DebugHelper.wait(1000);
				if (backup.isEmpty() || backup.peek().compareTo(this.peek()) > 0) {
					System.out.println("Adding to backup: " + this.peek());
					backup.push(this.pop());
				} else {
					if (!localMaximaFound) {
						System.out.println("Adding to local maxima: " + this.peek());
						localMaxima = this.pop();
						localMaximaFound = true;
					} else break;
				}
			}

			if (this.isEmpty()) isScanComplete = true;
			
			System.out.println("Putting back to source...");
			if (DBG) DebugHelper.wait(1000);

			localMaximaPushed = false;
			while (!backup.isEmpty() || !localMaximaPushed) {
				if (DBG) DebugHelper.wait(1000);
				if (!localMaximaPushed && (!backup.isEmpty() && localMaxima.compareTo(backup.peek()) > 0) ||
						localMaximaPushed) {
					System.out.println("Putting back to source: " + backup.peek());
					this.push(backup.pop());					
				} else {
					this.push(localMaxima);
					System.out.println("Putting back to source: " + localMaxima);
					localMaximaPushed = true;
				}
			}
		} while (!isScanComplete);
	}

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public boolean isFull() {
		return false;
	}
}
