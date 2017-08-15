package crami;

public class Player {
	/* ------- local fields ------- */
	private Score score;
	private String nickname;
	private Hand hand;

	/* ------- constructors ------- */
	public Player() {
		score = new Score( );
		hand = null;
	}

	public Player(String nikename) {
		this( );
		this.nickname = nikename;
	}

	public boolean isMseket() {
		/* this is the fucking problem! */
		return false;
	}

	/* ------- override'd methods ------- */
	@Override
	public String toString() {
		/* nickname score hand */
		return nickname + "\n\n" + hand.toString( );
	}

	/* ------- getters ------- */
	public Score getScore() {
		return score;
	}

	public String getNickname() {
		return nickname;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}
}
