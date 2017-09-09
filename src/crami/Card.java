package crami;

import java.util.Locale;

import crami.Game.TYPE;

public class Card {
	/* ------- enumerations ------- */
	public static enum FROM {
		TABLE(0), FAUXJOKER(1), RAMI(2), NULL(-1);

		public final int val;

		FROM(final int val) {
			this.val = val;
		}

		public static FROM fetch(int from) {
			switch(from) {
			/* @formatter:off */
			case 0: return TABLE;
			case 1: return FAUXJOKER;
			case 2: return RAMI;

			default: return RAMI;
			/* @formatter:on */
			}
		}

		public static FROM fetch(char c) {
			switch(c) {
			/* @formatter:off */
			default:  return NULL;
			case 'r': return RAMI;
			case 'f': return FAUXJOKER;
			case 't': return TABLE;
			/* @formatter:on */
			}
		}
	}

	public static enum RANK {
		ACE(1),

		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),

		SEVEN(7), EIGHT(8), NINE(9), TEN(10),

		JACK(11), QUEEN(12), KING(13),

		COUNT(13);

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
			default: 
			case 13: return  KING;

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
	private boolean isjoker;

	/* ------- constructors ------- */
	public Card() {
		/* non-sense initialization */
		this.rank = RANK.ACE;
		this.suit = SUIT.SPADES;
		this.back = BACK.BLUE;
		isjoker = false;
	}

	public Card(BACK back, RANK rank, SUIT suit, int deckorder) {
		this.deckorder = deckorder;
		this.rank = rank;
		this.suit = suit;
		this.back = back;
		isjoker = false;
	}

	public Card(BACK back, RANK rank, SUIT suit, int deckorder, boolean isjoker) {
		this(back, rank, suit, deckorder);
		this.isjoker = isjoker;
	}

	/* ------- getters ------- */
	public RANK getRank() {
		return rank;
	}

	public SUIT getSuit() {
		return suit;
	}

	public int getDeckOrder() {
		return deckorder;
	}

	public BACK getBack() {
		return back;
	}

	public boolean isJoker() {
		return isjoker;
	}

	/* ------- setters ------- */
	public void setDeckOrder(int deckorder) {
		this.deckorder = deckorder;
	}

	public void setAsJoker(boolean isit) {
		isjoker = isit;
	}

	/* ------- public functions ------- */
	public Card checkJoker(Game.TYPE gametype, Card faux) {
		SUIT fs = faux.getSuit( ), cs = this.getSuit( );
		RANK fr = faux.getRank( ), cr = this.getRank( ), TWO = Card.RANK.TWO;
		boolean usetjoker = (gametype == TYPE.TALAJ) ? (cr == TWO) : (cr == fr);

		if((cs.value % 2 != fs.value % 2) && usetjoker) this.isjoker = true;

		return this;
	}

	/* ------- override'd functions ------- */
	@Override
	public String toString() {
		/* R (A♠ JOKER) Ace Of Spades */
		return deckorder + "\t" + back.name( ).substring(0, 1) + "\t("
				+ toSymbol( ) + (isjoker ? " JOKER" : "") + ")\t"
				+ strcap(rank.name( )) + " Of " + strcap(suit.name( ));

	}

	public String toString(String pattern) {
		if(pattern.length( ) == 0 || pattern == null) return toString( );

		String[] opt = pattern.split("%");
		String str = "";

		for(int i = 1; i < opt.length; ++i, str += " ") {
			if(opt[i].equals("d") || opt[i].equals("D")) {
				str += deckorder;
			} else if(opt[i].equals("b")) {
				str += back.name( ).substring(0, 1);
			} else if(opt[i].equals("B")) {
				str += back.name( );
			} else if(opt[i].equals("S") || opt[i].equals("s")) {
				str += "(" + toSymbol( ) + ")";
			} else if(opt[i].equals("F") || opt[i].equals("f")) {
				str += strcap(rank.name( )) + " Of " + strcap(suit.name( ));
			} else if(opt[i].equals("J") || opt[i].equals("j")) {
				str += isjoker ? "J" : "";
			}
		}

		return str == "" ? toString( ) : str;
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
		case  JACK: str += rank.name( ).substring(0, 1).toUpperCase( ) 
						+ " "; 										  break;
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
