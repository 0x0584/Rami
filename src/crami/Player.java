package crami;

import crami.Game.TYPE;
import crami.Hand.COMBINATION;

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

	public Player(String nikename, Hand hand) {
		this( );
		this.nickname = nikename;
		this.hand = hand;
	}

	/* ------- getters and setters ------- */
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

	/* ------- public functions ------- */
	public boolean isMseket(Game.TYPE gametype) {
		return gametype == TYPE.SIMPLE ? skatSimple( ) : skatTalaj( );
	}

	/* ------- local functions ------- */
	private boolean skatTalaj() {
		// TODO: create skatTalaj
		return false;
	}

	private boolean skatSimple() {
		/* TODO: this is the fucking problem!
		 * 
		 * Description:
		 * 
		 * the first player to have a hand that looks like one of the following
		 * possible hands; 3-3-3-4, 4-4-5 or 5-5-3. with at least one pure
		 * successive and one pure suited; pure here means that this combination
		 * was made without using a joker */

		System.out.print("wanna sekt? [y/N] ");

		if(Utils.getChar( ) != 'y') return false;

		String str = "what is your combination?\n[1] - 3-3-3-4\n"
				+ "[2] - 4-4-5\n[3] - 5-5-3";

		COMBINATION combi = Hand.COMBINATION.fetch(Utils.getInt(str, 1, 3));

		return hand.vefiryCombination(combi);
	}

	/* ------- override'd methods ------- */
	@Override
	public String toString() {
		/* nickname score hand */
		return nickname + "\n\n" + hand.toString( );
	}

}
