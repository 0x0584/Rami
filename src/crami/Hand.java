package crami;

public class Hand {
	public static enum COMBINATION {
		/* 4-3-3-3 :
		 * (J♥)(J♣)(J♠)(J♦) - (A♠)(A♥)(A♣) - (J♥)(Q♥)(K♥) - (9♣)(10♣)(J♣) */
		FTTT,

		/* 5-5-3 :
		 * (A♠)(A♥)(A♣) - (9♥)(10♥)(J♥)(Q♥)(K♥) - (9♣)(10♣)(J♣)(Q♣)(K♣) */
		FFT,

		/* 5-4-4:
		 * (9♥)(10♥)(J♥)(Q♥)(K♥) - (J♥)(J♣)(J♠)(J♦) - (A♠)(A♥)(A♣)(A♦) */
		FFF;

		public static COMBINATION fetch(int foo) {
			switch(foo) {
			/* @formatter:off */
			default:
			case 1: return FTTT;
			case 2: return FFT;
			case 3: return FFF;
			/* @formatter:on */
			}
		}
	}

	private static enum COMBI_STATE {
		NULL, /* not a combination */

		PURE, NOT_PURE;
	}

	private static enum COMBI_TYPE {
		RANKED, SUITED
	}

	private Card[] hand;
	private Card skat;
	private Game.TYPE gametype;
	private int lastindex; /* this is the index of the last card in-card */

	public Hand(Card[] hand, Game.TYPE gametype) {
		this.hand = hand;
		this.gametype = gametype;
		lastindex = gametype.ncards;
	}

	/* ------- getters ------- */
	public Card getCardAt(int index) {
		return (index >= 0 && index <= gametype.ncards) ? hand[index] : null;
	}

	public Card getSkat() {
		return skat;
	}

	/* zarhouni */

	/* ------- methods ------- */
	public boolean vefiryCombination(COMBINATION fetch) {

		int[] combins, selected = new int[13];
		final int NCOMBINATIONS;

		COMBI_STATE combinstates[];
		COMBI_TYPE combinstypes[];

		boolean pureranked = false, puresuit = false;

		/* 0. check combination's type */
		switch(fetch) {
		/* @formatter:off */
		default:
		case FTTT: combins = new int[] { 4, 3, 3, 3 }; break;
		case FFF:  combins = new int[] { 5, 4, 4 };    break;
		case FFT:  combins = new int[] { 5, 5, 3 };    break;
		/* @formatter:on */
		}

		if(Debug.enabled) System.out.println(fetch.name( ));

		NCOMBINATIONS = combins.length;
		combinstates = new COMBI_STATE[NCOMBINATIONS];
		combinstypes = new COMBI_TYPE[NCOMBINATIONS];

		/* 1.1 collect information on the combinations */
		for(int icombi = 0; icombi < NCOMBINATIONS; ++icombi) {
			Card[] combination = new Card[combins[icombi]];
			System.out.printf("%d cards combination\n", combins[icombi]);

			/* 1.2 grab the combination */
			for(int icard = 0; icard < combins[icombi]; ++icard) {
				/* FIXME: verify duplicates
				 * 
				 * whether the selected card has been already selected */
				String str = String.format("%s\nselect a card from 1 to %d ",
						toString( ), (gametype.ncards + 1));

				int index = Utils.getInt(str, 1, (gametype.ncards + 1));

				combination[icard] = hand[index - 1];
			}

			COMBI_STATE tmp = COMBI_STATE.NULL;

			/* 1.3 check whether it's a ranked or suited */
			if((tmp = isRandked(combination)) != COMBI_STATE.NULL) {
				combinstypes[icombi] = COMBI_TYPE.RANKED;
			} else if((tmp = isSuited(combination)) != COMBI_STATE.NULL) {
				combinstypes[icombi] = COMBI_TYPE.SUITED;
			} else {
				if(Debug.enabled) {
					System.out.printf("false combination detected, abort\n");
				}
				return false;
			}

			combinstates[icombi] = tmp;
		}

		/* 2. verify the pure combinations */
		for(int icomb = 0; icomb < NCOMBINATIONS; ++icomb) {
			if(combinstates[icomb] == COMBI_STATE.PURE) {
				if(combinstypes[icomb] == COMBI_TYPE.RANKED) {
					pureranked = true;
				} else puresuit = true;
			}

			if(pureranked && puresuit) {
				break;
			}
		}

		return pureranked && puresuit;
	}

	private COMBI_STATE isSuited(Card[] part) {
		/* Description: a suited is a set of cards which has the same
		 * `suit` but with successive ranks.
		 * 
		 * * examples:
		 * 
		 * (2♠)(3♠)(4♠) or (J♥)(JOKER)(K♥)(A♥) or (9♣)(10♣)(J♣)(Q♣)(K♣)
		 * 
		 * * in case combinations that has more than 5 cards:
		 * 
		 * (9♣)(10♣)(J♣)(Q♣)(K♣)(A♣) == (9♣)(10♣)(J♣) + (Q♣)(K♣)(A♣) */

		boolean[] joker = new boolean[4];
		boolean samesuit = true, succ = true, use14;
		final int NCARDS = part.length; /* # of passed cards */
		int njokers = 1, /* this is so that the we can know when we run out
						 * of jokers */
		MAX_JOKERS = -1; /* the # of possible jokers */

		/* 0.1 finding the maximum number of possible joker */
		for(int i = 0; i <= NCARDS; ++MAX_JOKERS, i += 3);

		/* 0.2 using the Ace as 1 or 14 */
		use14 = Utils.isIn(part, false, Card.RANK.ACE, Card.RANK.KING,
				Card.RANK.QUEEN);

		/* 1.1 checking whether the cards have the same suit */
		for(int icard1 = 1, end = (NCARDS - 1); icard1 < NCARDS; ++icard1) {
			int icard0 = icard1 - 1;

			boolean countjoker = part[icard0].isJoker( );
			countjoker ^= (icard1 == end && part[icard1].isJoker( ));

			if(countjoker) { /* counting found joker */
				joker[njokers++] = true;
				continue;
			}
			/* checking suit */
			samesuit &= (part[icard0].getSuit( ) == part[icard1].getSuit( ));
		}

		if(!samesuit || (njokers - 1) > MAX_JOKERS) return COMBI_STATE.NULL;

		/* 1.2 sort cards to find the suit easily */
		/* FIXME: using bubble-sort is ridicules
		 * 
		 * you have to find a better way */
		for(int loop = 1, end = (NCARDS - 1); loop < NCARDS; ++loop) {
			for(int icard1 = 1; icard1 < NCARDS; ++icard1) {
				int icard0 = icard1 - 1; /* for the sake of utility */

				boolean skipjoker = part[icard0].isJoker( );
				skipjoker ^= (icard1 == end && part[icard1].isJoker( ));

				if(skipjoker) continue;

				int card0 = part[icard0].getRank( ).value;
				int card1 = part[icard1].getRank( ).value;

				if(card0 == Card.RANK.ACE.value && use14) card0 = 14;
				else if(card1 == Card.RANK.ACE.value && use14) card1 = 14;

				if(card0 > card1) { /* reference: A2345678910JQK */
					Card tmp;
					tmp = Utils.swap(part[icard1], part[icard1] = part[icard0]);
					part[icard0] = tmp;
				}
			}
		}

		/* 2. compare successive cards to see if this is a suited set of cards */
		for(int icard1 = 1, end = (NCARDS - 1); icard1 < NCARDS; icard1++) {
			int icard0 = icard1 - 1;

			/* either one of the two cards is a joker */
			boolean skipjoker = part[icard0].isJoker( );
			skipjoker ^= (icard1 == end && part[icard1].isJoker( ));

			if(skipjoker) continue;

			int card0 = part[icard0].getRank( ).value;
			int card1 = part[icard1].getRank( ).value;

			if(card0 == Card.RANK.ACE.value && use14) card0 = 14;
			else if(card1 == Card.RANK.ACE.value && use14) card1 = 14;

			succ &= ((card1 - card0 == 1) || (card1 - card0 == 2 && joker[--njokers]));
		}

		/* 3. in order to be a suited combination of cards; cards must be part
		 * of the same suit also the cards must be a set of successive cards
		 * 
		 * if it has no joker(s) then it's a pure combination */
		if(samesuit && succ && !joker[njokers]) return COMBI_STATE.PURE;
		return (samesuit && succ) ? COMBI_STATE.NOT_PURE : COMBI_STATE.NULL;
	}

	private COMBI_STATE isRandked(Card[] part) {
		/* Description: a ranked is a set of cards which has the same
		 * `rank` but with different suits.
		 * 
		 * (A♠)(A♥)(A♣) or (J♥)(J♣)(J♠)(J♦) */
		final int NCARDS = part.length;

		/* (A♠)(A♥)(A♣) or (J♥)(J♣)(J♠)(J♦) */
		if(NCARDS != 3 && NCARDS != 4) return COMBI_STATE.NULL;

		int njokers = 0, nsuits = 0; /* number of suits existed
									 * in the cards set */
		boolean samerank = true, spades, clubs, hearts, diams;
		spades = clubs = hearts = diams = false;

		/* 1.1 checking existing ranks */
		for(int icard1 = 1, end = NCARDS - 1; icard1 < NCARDS; ++icard1) {
			int icard0 = icard1 - 1; /* handy */

			/* 1.2. count jokers */
			if(part[icard0].isJoker( ) || part[icard1].isJoker( )) {
				++njokers;
				continue;
			}

			/* checking ranks */
			samerank &= (part[icard0].getRank( ) == part[icard1].getRank( ));

			switch(part[icard0].getSuit( )) {
			/* @formatter:off*/
			case CLUBS:		 clubs = !(clubs)  ? true: false; break;
			case DIAMONDS: 	 diams = !(diams)  ? true: false; break;
			case HEARTS: 	hearts = !(hearts) ? true: false; break;
			case SPADES: 	spades = !(spades) ? true: false; break;
			default:					    				  break;
			/* @formatter:on */
			}

			/* TODO: find another way to do so
			 * 
			 * check the last card to see if we reach the end */
			if(icard1 == end) {
				switch(part[icard1].getSuit( )) {
				/* @formatter:off*/
				case CLUBS:		 clubs = !(clubs)  ? true: false; break;
				case DIAMONDS: 	 diams = !(diams)  ? true: false; break;
				case HEARTS: 	hearts = !(hearts) ? true: false; break;
				case SPADES: 	spades = !(spades) ? true: false; break;
				default:					    				  break;
				/* @formatter:on */
				}
			}
		}

		/* 2.1 counting found ranks */
		for(int isuit = 0; isuit < Card.SUIT.COUNT.value; ++isuit) {
			/* this seems like a bad practice but..
			 * an array of 4 bools hurts no one */
			if(new boolean[] {
					spades, diams, hearts, clubs
			}[isuit]) ++nsuits;
		}

		/* 2.2 use joker if found */
		if(njokers == 1) ++nsuits;

		/* 3. in order to be a ranked combination of cards; cards must have the
		 * same rank but with different suits
		 * 
		 * if it has no joker(s) then it's a pure combination */
		if(samerank && (nsuits > 2) && njokers == 0) return COMBI_STATE.PURE;
		return samerank && nsuits > 2 ? COMBI_STATE.NOT_PURE : COMBI_STATE.NULL;
	}

	public void insertCard(Card card) {
		hand[lastindex] = card;
		lastindex = -1; /* not sure why! */
	}

	public Card throwCard() {
		/* TODO: find a better way! */

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println("before:\n" + toString( ));

		lastindex = Utils.getInt("", 1, (gametype.ncards + 1)) - 1;

		Card it = hand[lastindex];
		hand[lastindex] = null;

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println("after:\n" + toString( ));

		return it;
	}

	@Override
	public String toString() {
		String str = "";

		for(int i = 0; i <= gametype.ncards; ++i) {
			if(hand[i] != null) str += hand[i].toString("%s%j");
		}

		return str + "\n";
	}
}
