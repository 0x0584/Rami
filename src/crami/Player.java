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

	public boolean isSuited(Card[] part) {
		/* TODO: implement this description
		 * 
		 * Description: a suited is a set of cards which has the same
		 * `suit` but with successive ranks.
		 * 
		 * (2♠)(3♠)(4♠) or (J♥)(Q♥)(K♥)(A♥) or (9♣)(10♣)(J♣)(Q♣)(K♣)
		 * 
		 * Algorithm:
		 * 
		 * 1. get the min-card and the max-card from the passed cards
		 * 2. if the #cards != N, where N is (rank(max) - rank(min) - 1)
		 * then return false
		 * 3. */

		boolean samesuit = true;

		for(int icard = 0; icard < part.length - 1; ++icard) {
			samesuit &= (part[icard].getSuit( ) == part[icard + 1].getSuit( ));
		}

		for(int icard = 0; icard < part.length; ++icard) {

		}

		return false;
	}

	public boolean isRandked(Card[] part) {
		/* TODO: implement this description
		 * # in case of joker
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

	public boolean isMseket() {
		/* TODO: this is the fucking problem!
		 * 
		 * Description:
		 * 
		 * the first player to have a hand that looks like one of the following
		 * possible hands; 3-3-3-4, 4-4-5 or 5-5-3. with at least one pure
		 * successive and one pure suited; pure here means that this combination
		 * was made without using a joker */
		return true;
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
