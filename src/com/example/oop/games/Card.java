package com.example.oop.games;

class Card {
	Significance significance;

	static class Significance {
		int value;

		Significance(int value) {
			this.value = value;
		}
	}

	Card(Significance significance) {
		this.significance = significance;
	}
}
