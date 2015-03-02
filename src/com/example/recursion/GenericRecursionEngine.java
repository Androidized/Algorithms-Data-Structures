package com.example.recursion;

import java.util.HashSet;
import java.util.Set;

public class GenericRecursionEngine<T> {

	/**
	 * Return all subsets of a given set of elements of type T. 
	 */
	public HashSet<HashSet<T>> getSubsets(final HashSet<T> rootSet) {
		final HashSet<HashSet<T>> setOfSubsets = new HashSet<HashSet<T>>();
		getSubsets(rootSet, setOfSubsets);
		return setOfSubsets;
	}

	/**
	 * Wrapper for getSubsets(HashSet<T> rootSet) public method. 
	 */
	private void getSubsets(final HashSet<T> rootSet, final Set<HashSet<T>> setOfSubsets) {
		setOfSubsets.add(rootSet);
		HashSet<T> tempSet = null;
		for (T element : rootSet) {
			tempSet = new HashSet<T>(rootSet);
			tempSet.remove(element);
			if (!tempSet.isEmpty()) getSubsets(tempSet, setOfSubsets);
		}
	}
}
