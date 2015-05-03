package com.example.data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrayStringAnalyzer<T> {

	static class Palindrome {
		boolean isOdd;
		int middleIndex;
		int size;

		Palindrome() {
			this.isOdd = false;
			this.middleIndex = 0;
			this.size = 0;
		}

		Palindrome(boolean isOdd, int middleIndex, int size) {
			this.isOdd = isOdd;
			this.middleIndex = middleIndex;
			this.size = size;
		}
	}

	public static void main(String[] args) {
		int[][] multi = new int[][]{
				  { 0, 1, 2},
				  { 3, 4, 5},
				  { 6, 7, 8}
				};
		resetRowsColumns(multi);
	}
	
	public final static boolean hasUniqueCharacters(StringBuilder str)
			throws IllegalArgumentException {
		int detectedCharactersPlaceHolder = 0;
		int length = str.length();
		if (length == 1 && (str.charAt(0) - 'a' >= 0)) {
			return true;
		}
		if (length == 0) {
			throw new IllegalArgumentException("Invalid input string!");
		}
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) - 'a' < 0) {
				throw new IllegalArgumentException();
			}
            if ((detectedCharactersPlaceHolder & (1 << str.charAt(i) - 'a')) > 0) {
            	return false;
            } else {
            	detectedCharactersPlaceHolder |= (1 << str.charAt(i) - 'a');
            }
		}
		return true;
	}

	public final static StringBuilder reverseString(StringBuilder inputStringBuilder)
			throws IllegalArgumentException {
		StringBuilder stringBuilder = new StringBuilder();
		int length = inputStringBuilder.length();
		if (length == 0) {
			throw new IllegalArgumentException("Invalid input string!");
		}
		if (length == 1) {
			return inputStringBuilder;
		}
		for (int i = length - 1; i >= 0; i--) {
			stringBuilder.append(inputStringBuilder.charAt(i));
		}
		return stringBuilder;
	}

	public final static StringBuilder removeDuplicateCharacters(StringBuilder inputStringBuilder)
			throws IllegalArgumentException {
		int length = inputStringBuilder.length();
		if (length == 0) {
			throw new IllegalArgumentException("Invalid input string!");
		}
		if (length == 1) {
			return inputStringBuilder;
		}
		if (hasUniqueCharacters(inputStringBuilder)) {
			return inputStringBuilder;
		} else {
			StringBuilder stringBuilder = new StringBuilder(inputStringBuilder.toString());
			int detectedCharactersPlaceHolder = 0;
			for (int i = 0; i < stringBuilder.length(); i++) {
				if ((detectedCharactersPlaceHolder & (1 << stringBuilder.charAt(i) - 'a')) > 0) {
					stringBuilder.deleteCharAt(i--);
				} else {
					detectedCharactersPlaceHolder |= (1 << stringBuilder.charAt(i) - 'a');
				}
			}
			return stringBuilder;
		}
	}

	public final static boolean areAnagrams(String firstString, String secondString) {
		if (firstString.length() != secondString.length()) {
			return false;
		} else {
			HashMap<Character, Integer> charactersHistogram = new HashMap<Character, Integer>();
			char firstArray[] = firstString.toCharArray();
			char secondArray[] = secondString.toCharArray();

			for (int i = 0; i < firstArray.length; i++) {
				if (charactersHistogram.get(firstArray[i]) != null) {
					charactersHistogram.put(firstArray[i], charactersHistogram.get(firstArray[i]) + 1);
				} else {
					charactersHistogram.put(firstArray[i], 1);
				}
			}

			for (int i = 0; i < secondArray.length; i++) {
				if (charactersHistogram.get(secondArray[i]) != null) {
					charactersHistogram.put(firstArray[i], charactersHistogram.get(firstArray[i]) - 1);
				} else {
					return false;
				}
			}

			for (Integer value : charactersHistogram.values()) {
				if (value != 0) return false;
			}

			return true;
		}
	}

	public final static int findLongestPalindrome(final StringBuilder string) {
		List<Palindrome> listOfPalindromes = new ArrayList<Palindrome>();
		int length = string.length();
		for (int i = 0; i < length - 1; i++) {
			if (i > 0 && string.charAt(i - 1) == string.charAt(i + 1)) {
				Palindrome p = new Palindrome();
				p.isOdd = true;
				p.middleIndex = i;
				p.size = 3;
				System.out.println("Created Palindrome Instance at Index " + p.middleIndex);
				if (i == 1) listOfPalindromes.add(p);
				for (int j = 2; j <= Math.min(i, length - i - 1); j++) {
					if (string.charAt(p.middleIndex - j) == string.charAt(p.middleIndex + j))
						p.size += 2;
					else break;
				}
				listOfPalindromes.add(p);
				System.out.println("Added Palindrome Instance Centered at Index: " + p.middleIndex + ", Size " + p.size);
			}
			if (string.charAt(i) == string.charAt(i + 1)) {
				Palindrome p = new Palindrome();
				p.isOdd = false;
				p.middleIndex = i;
				p.size = 2;
				System.out.println("Created Palindrome Instance at Index " + p.middleIndex);
				if (i == 0) listOfPalindromes.add(p);
				for (int j = 1; j < Math.min(i, length - i - 1); j++) {
					if (string.charAt(p.middleIndex - j) == string.charAt(p.middleIndex + 1 + j))
						p.size += 2;
					else break;
				}
				listOfPalindromes.add(p);
				System.out.println("Added Palindrome Instance Centered at Index: " + p.middleIndex + ", Size " + p.size);
			}
		}

		System.out.println("Found " + listOfPalindromes.size() + " Palindromes!");

		Palindrome palindromeWithMaxLength = null;
		for (Palindrome p : listOfPalindromes) {
			if (palindromeWithMaxLength == null) {
				palindromeWithMaxLength = p;
			} else {
				if (p.size > palindromeWithMaxLength.size) {
					palindromeWithMaxLength = p;
				}
			}
		}

		return palindromeWithMaxLength.size;
	}
	
	/**
	 * (0,0) to (0, n-1)
	 * (0,n-1) to (n-1,n-1)
	 * (n-1,n-1) to (n-1,0)
	 * (n-1,0) to (0,0)
	 * 
	 * (0,1) to (1, n-1)
	 * (1,n-1) to (n-1,n-1-1)
	 * (n-1,n-1-1) to (n-1-1,0)
	 * (n-1-1,0) to (0,1)
	 * */
	public final static void rotate(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println("\n");
		}
		
		int tmp;
		int m = 0;
		int n = matrix.length;
		while (n > 1) {
			for (int i = m; i < n - 1; i++) {
				tmp = matrix[m][i];
				matrix[m][i] = matrix[n - 1 - i][m];
				matrix[n - 1 - i][m] = matrix[n - 1 - m][n - 1 - i];
				matrix[n - 1 - m][n - 1 - i] = matrix[i][n - 1 - m];
				matrix[i][n - 1 - m] = tmp;
			}
			n--;
			m++;
		}
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println("\n");
		}
	}

	public final static void resetRowsColumns(int[][] matrix) {
		int rowsToReset = 0;
		int columnsToReset = 0;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == 0) {
					rowsToReset |= (1 << i);
					columnsToReset |= (1 << j);
				}
			}
		}

		for (int i = 0; i < matrix.length; i++) {
			if ((rowsToReset & (1 << i)) != 0) {
				for (int j = 0; j < matrix.length; j++) {
					matrix[i][j] = 0;
				}
			}
		}

		for (int j = 0; j < matrix.length; j++) {
			if ((columnsToReset & (1 << j)) != 0) {
				for (int i = 0; i < matrix.length; i++) {
					matrix[i][j] = 0;
				}
			}
		}

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println("\n");
		}
	}

	/**
	 * Spirally traverse a matrix 
	 */
	public static <T> void spiralTraverse(T[][] matrix) {
		if (matrix == null) return;

		int upperLimit = (int) Math.floor(Math
				.min(matrix.length, matrix[0].length)) / 2;

		for (int i = 0; i < upperLimit; i++) {
			traverse(true, false, i, i, matrix);
			traverse(false, false, upperLimit - i, i, matrix);
			traverse(true, true, upperLimit - i, i, matrix);
			traverse(false, true, i, i, matrix);
		}
	}

	/**
	 * Helper method to traverse a specified row or column
	 * of a matrix, either in forward or reverse direction,
	 * and given an offset from the front and the tail of
	 * the specified row or column 
	 */
	private static <T> void traverse(boolean isRow, boolean isReversal,
			int which, int offset, T[][] matrix) {
		if (matrix == null) return;

		int upperLimit = (int) Math.floor(Math
				.min(matrix.length, matrix[0].length)) / 2;

		if (isReversal) {
			if (isRow) {
				for (int j = upperLimit - offset; j < offset; j--) {
					System.out.println(matrix[which][j]);
				}
			} else {
				for (int j = upperLimit - offset; j < offset; j--) {
					System.out.println(matrix[which][j]);
				}
			}
		} else {
			if (isRow) {
				for (int j = offset; j < upperLimit - offset; j++) {
					System.out.println(matrix[which][j]);
				}
			} else {
				for (int j = offset; j < upperLimit - offset; j++) {
					System.out.println(matrix[j][which]);
				}
			}
		}
	}
}
