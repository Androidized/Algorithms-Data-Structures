package com.example.recursion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.HashMultiset;

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

	/**
	 * Return all permutations of a given string. 
	 */
	public HashSet<StringBuilder> getPermutations(StringBuilder string) {
		HashSet<StringBuilder> setOfPermutations = new HashSet<StringBuilder>();
		HashSet<HashMap<Character, Integer>> rootSet = new HashSet<HashMap<Character, Integer>>();
		HashMap<Character, Integer> hash = null;
		for (int i= 0; i < string.length(); i++) {
			hash = new HashMap<Character, Integer>();
			hash.put(string.charAt(i), i);
			rootSet.add(hash);
		}
		getPermutations(null, rootSet, setOfPermutations);
		return setOfPermutations;
	}

	private void getPermutations(final StringBuilder rootString,
			                     final HashSet<HashMap<Character, Integer>> rootSet,
			                     final HashSet<StringBuilder> setOfPermutations) {
		StringBuilder tempRootString = null;
		for (HashMap<Character, Integer> hash : rootSet) {
			HashSet<HashMap<Character, Integer>> tempSet =
					new HashSet<HashMap<Character, Integer>>(rootSet);
			tempSet.remove(hash);
			for (Character ch : hash.keySet()) {
				if (rootString == null) tempRootString = new StringBuilder();
				else tempRootString = new StringBuilder(rootString);
				tempRootString.append(ch);
				if (tempSet.isEmpty()) {
					setOfPermutations.add(tempRootString);
				} else getPermutations(tempRootString, tempSet, setOfPermutations);
			}
		}
	}

	/**
	 * Return all permutations of a given
	 * string using Google common package.
	 */
	public HashSet<StringBuilder> getPermutationsWithBag(StringBuilder string) {
		HashSet<StringBuilder> setOfPermutations = new HashSet<StringBuilder>();
		HashMultiset<Character> multiset = HashMultiset.create();
		for (int i= 0; i < string.length(); i++)
			multiset.add(string.charAt(i));
		getPermutationsWithBag(null, multiset, setOfPermutations);
		return setOfPermutations;
	}

	private void getPermutationsWithBag(final StringBuilder rootString,
                                        final HashMultiset<Character> rootMultiset,
                                        final HashSet<StringBuilder> setOfPermutations) {
        StringBuilder tempRootString = null;
        HashMultiset<Character> tempMultiset = null;
        for (Character ch : rootMultiset) {
        	tempMultiset = HashMultiset.create(rootMultiset);
            tempMultiset.remove(ch);
            if (rootString == null) tempRootString = new StringBuilder();
            else tempRootString = new StringBuilder(rootString);
            tempRootString.append(ch);
            if (tempMultiset.isEmpty()) {
                setOfPermutations.add(tempRootString);
            } else getPermutationsWithBag(tempRootString, tempMultiset, setOfPermutations);
        }
    }

	public long swapInPlace(long value) {
		int numberOfDigits = 0;
		while ((int)(value / Math.pow(10, numberOfDigits)) != 0) numberOfDigits++;
		int k = 0;
		long result = value;
		while (k < numberOfDigits) {
			result += (value % Math.pow(10, k + 1) - value % Math.pow(10, k)) * Math.pow(10, 2 * (numberOfDigits - k) - 1);
			k++;
		}
		return (long) (result / Math.pow(10, numberOfDigits));
	}
}
