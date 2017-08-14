package crami;

import crami.Game.TYPE;

public class Player {
	private Score score;
	private String nikename;
	private Card[] hand;

	public Player() {
		score = new Score( );
		hand = null;
	}

	public Player(String nikename) {
		this( );
		this.nikename = nikename;
	}

	/* ------- getters ------- */
	public Score getScore() {
		return score;
	}

	public String getNikename() {
		return nikename;
	}

	public Card[] getHand() {
		return hand;
	}

	public void setHand(Card[] hand) {
		this.hand = hand;
	}

	public String toString() {
		String str = "";

		for(int i = 0; i < hand.length; ++i)
			str += hand[i].toString( ) + "\n";

		return nikename + "\n\n" + str;
	}

	public void insertCard(Card card, TYPE gametype) {
		hand[gametype.ncards] = card;
	}

	public Card throwCard() {
		return null;
	}

	public boolean takeFromDeck() {
		return true;
	}

	public boolean isMseket() {
		/* this is the fucking problem! */
		return true;
	}
}
