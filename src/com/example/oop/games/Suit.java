package com.example.oop.games;

import java.util.HashSet;
import java.util.Set;

import com.example.oop.games.Card.Significance;

public final class Suit {

	class Hearts extends Card {
		Hearts(Significance significance) {
			super(significance);
		}
	}

	class Spades extends Card {
		Spades(Significance significance) {
			super(significance);
		}
	}

	class Clubs extends Card {
		Clubs(Significance significance) {
			super(significance);
		}
	}

	class Diamonds extends Card {
		Diamonds(Significance significance) {
			super(significance);
		}
	}

	class Joker extends Card {
		Joker(Significance significance) {
			super(significance);
		}
	}

	private Set<Hearts> mHearts;
	private Set<Spades> mSpades;
	private Set<Clubs> mClubs;
	private Set<Diamonds> mDiamonds;
	private Joker mJoker;
	
	Suit() {
		mHearts = new HashSet<Hearts>();
		mSpades = new HashSet<Spades>();
		mClubs = new HashSet<Clubs>();
		mDiamonds = new HashSet<Diamonds>();

		mHearts.add(new Hearts(new Significance(1)));
		mHearts.add(new Hearts(new Significance(2)));
		mHearts.add(new Hearts(new Significance(3)));
		mHearts.add(new Hearts(new Significance(4)));
		mHearts.add(new Hearts(new Significance(5)));
		mHearts.add(new Hearts(new Significance(6)));
		mHearts.add(new Hearts(new Significance(7)));
		mHearts.add(new Hearts(new Significance(8)));
		mHearts.add(new Hearts(new Significance(9)));
		mHearts.add(new Hearts(new Significance(10)));
		mHearts.add(new Hearts(new Significance(11)));

		mSpades.add(new Spades(new Significance(1)));
		mSpades.add(new Spades(new Significance(2)));
		mSpades.add(new Spades(new Significance(3)));
		mSpades.add(new Spades(new Significance(4)));
		mSpades.add(new Spades(new Significance(5)));
		mSpades.add(new Spades(new Significance(6)));
		mSpades.add(new Spades(new Significance(7)));
		mSpades.add(new Spades(new Significance(8)));
		mSpades.add(new Spades(new Significance(9)));
		mSpades.add(new Spades(new Significance(10)));
		mSpades.add(new Spades(new Significance(11)));

		mClubs.add(new Clubs(new Significance(1)));
		mClubs.add(new Clubs(new Significance(2)));
		mClubs.add(new Clubs(new Significance(3)));
		mClubs.add(new Clubs(new Significance(4)));
		mClubs.add(new Clubs(new Significance(5)));
		mClubs.add(new Clubs(new Significance(6)));
		mClubs.add(new Clubs(new Significance(7)));
		mClubs.add(new Clubs(new Significance(8)));
		mClubs.add(new Clubs(new Significance(9)));
		mClubs.add(new Clubs(new Significance(10)));
		mClubs.add(new Clubs(new Significance(11)));

		mDiamonds.add(new Diamonds(new Significance(1)));
		mDiamonds.add(new Diamonds(new Significance(2)));
		mDiamonds.add(new Diamonds(new Significance(3)));
		mDiamonds.add(new Diamonds(new Significance(4)));
		mDiamonds.add(new Diamonds(new Significance(5)));
		mDiamonds.add(new Diamonds(new Significance(6)));
		mDiamonds.add(new Diamonds(new Significance(7)));
		mDiamonds.add(new Diamonds(new Significance(8)));
		mDiamonds.add(new Diamonds(new Significance(9)));
		mDiamonds.add(new Diamonds(new Significance(10)));
		mDiamonds.add(new Diamonds(new Significance(11)));

		mJoker = new Joker(new Significance(0));
	}

	public void suffle() {
		
	}

	public Card pick() {
		return null;
	}
}
