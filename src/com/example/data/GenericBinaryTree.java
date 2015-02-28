package com.example.data;

public class GenericBinaryTree<T> {

	class Element {
		T data;
		Element leftChild;
		Element rightChild;

		public Element(T data, Element leftChild, Element rightChild) {
			this.data = data;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}

		public Element(T data) {
			this.data = data;
			this.leftChild = null;
			this.rightChild = null;
		}
	}

	public Element root;

	public T delete(T data) {
		return null;
	}

	public void insert(T data) {
		return;
	}

	public int getNumberOfLeaves(Element localRoot) {
		if (localRoot.leftChild == null && localRoot.rightChild == null)
			return 1;
		int numberOfLeaves = 0;
		if (localRoot.leftChild != null)
			numberOfLeaves += getNumberOfLeaves(localRoot.leftChild);
		if (localRoot.rightChild != null)
			numberOfLeaves += getNumberOfLeaves(localRoot.rightChild);
		return numberOfLeaves;
	}

	public int getTotalNumberOfInternalElements(Element localRoot) {
		if (localRoot == null ||
		   (localRoot.leftChild == null && localRoot.rightChild == null))
			return 0;
		else return 1 +
			   getTotalNumberOfInternalElements(localRoot.leftChild) +
			   getTotalNumberOfInternalElements(localRoot.rightChild);
	}
}
