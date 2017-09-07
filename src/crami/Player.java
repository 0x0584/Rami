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

	private boolean isSameRank(Card[] card, boolean isrank) {
		Object foo = isrank ? card[0].getRank( ) : card[0].getSuit( );

		for(int i = 1; i < card.length; ++i) {
			Object bar = isrank ? card[i].getRank( ) : card[i].getSuit( );
			if(bar != foo) return false;
		}

		return true;
	}

	private boolean isSuccessive(Card[] part) {
		/* TODO: implement this description
		 * 
		 * Description: a successive is a set of cards which has the same
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
		return false;
	}

	private boolean isSuited(Card[] part) {
		return false;
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
