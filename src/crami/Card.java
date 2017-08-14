package crami;

import java.util.Locale;

public class Card {
	/* ------- enumerations ------- */
	public static enum RANK {
		ACE(1),

		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),

		SEVEN(7), EIGHT(8), NINE(9), TEN(10),

		JACK(11), QUEEN(12), KING(13),

		COUNT(13),

		JOKER(0);

		public final int value;

		RANK(final int value) {
			this.value = value;
		};

		public static RANK fetch(int rank) {
			switch(rank) {
			/* @formatter:off */
			case  1: return  ACE;
			case  2: return  TWO;
			case  3: return  THREE;
			case  4: return  FOUR;
			case  5: return  FIVE;
			case  6: return  SIX;
			case  7: return  SEVEN;
			case  8: return  EIGHT;
			case  9: return  NINE;
			case 10: return  TEN;
			case 11: return  JACK;
			case 12: return  QUEEN;
			case 13: return  KING;

			default: return  JOKER;
			/* @formatter:on */
			}
		}
	};

	public static enum SUIT {
		/* ♠ ♥ ♣ ♦ */
		SPADES(0), HEARTS(1), CLUBS(2), DIAMONDS(3),

		COUNT(4);

		public final int value;

		SUIT(final int value) {
			this.value = value;
		}

		public static SUIT fetch(int suit) {
			switch(suit) {
			/* @formatter:off */
			default:
			case 0: return  SPADES; 
			case 1: return  HEARTS; 
			case 2: return  CLUBS; 
			case 3: return  DIAMONDS;
			/* @formatter:on */
			}
		}

		public static String fetchSymbol(int suit) {
			switch(suit) {
			/* @formatter:off */
			default:
			case 0: return  "♠"; 
			case 1: return  "♥"; 
			case 2: return  "♣"; 
			case 3: return  "♦";
			/* @formatter:on */
			}
		}

		public static String fetchSymbol(SUIT suit) {
			switch(suit) {
			/* @formatter:off */
			default:
			case   SPADES: return  "♠"; 
			case   HEARTS: return  "♥"; 
			case    CLUBS: return  "♣"; 
			case DIAMONDS: return  "♦";
			/* @formatter:on */
			}
		}
	};

	public static enum BACK {
		RED(0), BLUE(1),

		COUNT(2);

		public final int value;

		BACK(final int value) {
			this.value = value;
		}

		public static BACK fetch(int back) {
			switch(back) {
			/* @formatter:off */
			default: 
			case 0: return  RED;
			case 1: return  BLUE;
			/* @formatter:on */
			}
		}
	};

	/* ------- local fields ------- */
	private RANK rank;
	private SUIT suit;
	private BACK back;
	private int deckorder;

	/* ------- constructors ------- */
	public Card() {
		/* non-sense initialization */
		this.rank = RANK.ACE;
		this.suit = SUIT.SPADES;
		this.back = BACK.BLUE;
	}

	public Card(BACK back, RANK rank, SUIT suit, int deckorder) {
		this.deckorder = deckorder;
		this.rank = rank;
		this.suit = suit;
		this.back = back;
	}

	/* ------- getters ------- */
	public RANK getRank() {
		return rank;
	}

	public SUIT getSuite() {
		return suit;
	}

	public int getDeckOrder() {
		return deckorder;
	}

	public BACK getBack() {
		return back;
	}

	/* ------- setters ------- */
	public void setDeckorder(int deckorder) {
		this.deckorder = deckorder;
	}

	/* ------- override'd function ------- */
	@Override
	public String toString() {
		/* R (A♠) Ace Of Spades */
		return deckorder + "\t" + back.name( ).substring(0, 1) + "\t("
				+ toSymbol( ) + ")\t" + strcap(rank.name( )) + " Of "
				+ strcap(suit.name( ));

	}

	/* ------- local functions ------- */
	private String toSymbol() {
		String str = "";

		switch(rank) {
		/* @formatter:off */
		default: str += rank.value + ((rank != RANK.TEN) ? " " : ""); break;
		case   ACE:
		case  KING:
		case QUEEN:
		case  JACK: str += rank.name( ).substring(0, 1).toUpperCase( ) + " "; break;
		/* @formatter:on */
		}

		return str += SUIT.fetchSymbol(suit);
	}

	private String strcap(String str) {
		if(str.length( ) == 0 || str == null) return str;
		return str.substring(0, 1).toUpperCase(Locale.getDefault( ))
				+ str.substring(1).toLowerCase(Locale.getDefault( ));
	}

}
