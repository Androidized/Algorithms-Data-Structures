package com.example.numbers;

import java.util.Random;

@SuppressWarnings("serial")
public class RandomEngine extends Random {

	/**
	 * Shuffle an array using growing bucket sizes with no randomization. 
	 */
	public static void suffleInt(int[] array) {
		int temp;
		int bucketSize = 2;
		int length = array.length;

		while ((int) (length / bucketSize) != 0) {
			for (int i = 0; i < (int) length / bucketSize; i++) {
				for (int j = i * bucketSize; j < i * bucketSize + bucketSize/2; j++) {
					temp = array[j];
					array[j] = array[j + bucketSize/2];
					array[j + bucketSize/2] = temp;
				}
			}

			// Swap unbucketized elements at the end of the array with
			// the same number of elements from the beginning of array.
			if (length % bucketSize != 0) {
				for (int i = 0; i < length % bucketSize; i++) {
					temp = array[i];
					array[i] = array[i + (bucketSize) * ((int) length/bucketSize)];
					array[i + (bucketSize) * ((int) length/bucketSize)] = temp;
				}
			}

			bucketSize *= 2;
		}
	}

	/**
	 * Shuffle an array based on modernized Fisher–Yates
	 * algorithm, also known as Durstenfeld shuffle.
	 * 
	 * @see http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
	 * @see http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
	 */
	public static void shuffleIntRandomization(int[] array) {
		int temp;
		int randomIndex;
		Random random = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			randomIndex = random.nextInt(i);
			temp = array[randomIndex];
			array[randomIndex] = array[i];
			array[i] = temp;
		}
	}
}
