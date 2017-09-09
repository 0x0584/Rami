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

		System.out.println("what are your combination?\n" + "[1] - 3-3-3-4\n"
				+ "[2] - 4-4-5\n" + "[3] - 5-5-3");

		int foo = 0;
		while(foo < 1 || foo > 3) {
			try {
				foo = reader.read( );
			} catch(IOException ignore) {}
		}

		return hand.vefiryCombination(Hand.COMBINATION.fetch(foo));
	}

	/* ------- override'd methods ------- */
	@Override
	public String toString() {
		/* nickname score hand */
		return nickname + "\n\n" + hand.toString( );
	}

	/* ------- local functions ------- */
	private boolean isIn(Card[] part, Card.RANK... ranks) {
		boolean[] foo = new boolean[ranks.length];

		for(int i = 0, k = 0; i < part.length; i++) {
			for(int j = 0; j < ranks.length; j++) {
				if(part[i].getRank( ) == ranks[j]) {
					foo[k++] = true;
					break;
				}
			}
		}

		boolean isin = true;

		for(int i = 0; i < foo.length; isin &= foo[i], i++);

		return isin;
	}

	private boolean isSuited(Card[] part) {
		/* TODO: implement this description in case of joker!
		 * 
		 * Description: a suited is a set of cards which has the same
		 * `suit` but with successive ranks.
		 * 
		 * (2♠)(3♠)(4♠) or (J♥)(Q♥)(K♥)(A♥) or (9♣)(10♣)(J♣)(Q♣)(K♣) */

		boolean samesuit = true, succ = true;
		boolean use14 = isIn(part, Card.RANK.ACE, Card.RANK.KING);

		for(int icard = 0; icard < part.length - 1; ++icard) {
			samesuit &= (part[icard].getSuit( ) == part[icard + 1].getSuit( ));
		}

		/* TODO: this is ridicules, you have to find a better way */
		for(int i = 1; i < part.length; ++i) {
			for(int icard = 0; icard < part.length - 1; ++icard) {
				int card0 = part[icard].getRank( ).value;
				int card1 = part[icard + 1].getRank( ).value;

				if(card0 == Card.RANK.ACE.value && use14) card0 = 14;
				else if(card1 == Card.RANK.ACE.value && use14) card1 = 14;

				if(card0 > card1) {
					part[icard] = swap(part[icard + 1],
							part[icard + 1] = part[icard]);
				}
			}
		}

		for(int icard = 1; icard < part.length; icard++) {
			int card0 = part[icard - 1].getRank( ).value;
			int card1 = part[icard].getRank( ).value;

			if(card0 == Card.RANK.ACE.value && use14) card0 = 14;
			else if(card1 == Card.RANK.ACE.value && use14) card1 = 14;

			succ &= (card1 - card0 == 1);
		}

		return samesuit && succ;
	}

	private boolean isRandked(Card[] part) {
		/* TODO: implement this description in case of joker
		 * 
		 * Description: a ranked is a set of cards which has the same
		 * `rank` but with different suits.
		 * 
		 * (A♠)(A♥)(A♣) or (J♥)(J♣)(J♠)(J♦) */

		if(part.length != 3 && part.length != 4) return false;

		boolean samerank = true, spades, clubs, hearts, diams;
		spades = clubs = hearts = diams = false;

		for(int icard = 1; icard < part.length; ++icard) {
			samerank &= (part[icard - 1].getRank( ) == part[icard].getRank( ));

			switch(part[icard - 1].getSuit( )) {
			/* @formatter:off*/
			case CLUBS:		 clubs = !(clubs)  ? true: false; break;
			case DIAMONDS: 	 diams = !(diams)  ? true: false; break;
			case HEARTS: 	hearts = !(hearts) ? true: false; break;
			case SPADES: 	spades = !(spades) ? true: false; break;
			default:					    				  break;
			/* @formatter:on */
			}
		}

		int suitcount = 0;
		for(int isuit = 0; isuit < 4; ++isuit) {
			if(new boolean[] {
					spades, diams, hearts, clubs
			}[isuit]) ++suitcount;
		}

		return samerank && suitcount > 2;
	}

	/** this trick works because Java guarantees that all arguments are
	 * evaluated from left to right. so this trick implements the swap
	 * method since there's no pointers in Java. so sad!
	 * 
	 * @usage foo = (bar, bar = foo)
	 * 
	 * @param c1
	 *            the first Card
	 * @param c1
	 *            the second Card
	 * 
	 * @return c1 the first Card */
	private Card swap(Card c1, Card c2) {
		return c1;
	}
}
