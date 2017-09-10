package crami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import crami.Game.TYPE;

public class Player {
	/* ------- local fields ------- */
	private Score score;
	private String nickname;
	private Hand hand;
	private static BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

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

	public static void cleanUp() {
		try {
			reader.close( );
		} catch(IOException ignore) {}
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
		try {
			if(reader.read( ) != 'y') return false;
		} catch(IOException ignore) {}

		System.out.println("what is your combination?\n" + "[1] - 3-3-3-4\n"
				+ "[2] - 4-4-5\n" + "[3] - 5-5-3");

		int foo = 0;
		while((foo < 1 || foo > 3)) {
			// System.out.printf("test");
			try {
				foo = Integer.parseInt(reader.readLine( ));
			} catch(NumberFormatException | IOException ignore) {}
		}

		if(Debug.enabled) {
			System.out.printf("%s", new String[] {
					"hhhh", "[1] - 3-3-3-4\n", "[2] - 4-4-5\n", "[3] - 5-5-3"
			}[foo]);
		}

		return hand.vefiryCombination(Hand.COMBINATION.fetch(foo));
	}

	/* ------- override'd methods ------- */
	@Override
	public String toString() {
		/* nickname score hand */
		return nickname + "\n\n" + hand.toString( );
	}

}
