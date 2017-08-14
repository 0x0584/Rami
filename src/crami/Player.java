package crami;

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

	public void insertCard(Card... card) {
		/* choose one card */
	}

	public Card throwCard() {
		return null;
	}

	public boolean takeFromDeck() {
		return true;
	}

	public boolean isMseket() {
		return true;
	}
}
