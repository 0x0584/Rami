package crami;

import java.util.Random;
import java.util.Vector;

public class Deck {
	/* ------- local fields ------- */
	private final int N_CARDS = Card.RANK.COUNT.value * Card.SUIT.COUNT.value
			* Card.BACK.COUNT.value; /* #RANKS x #SUITES x TWO DECKS */
	private Card[] card;
	private int used;
	private boolean isempty, isshuffled;

	/* ------- constructors ------- */
	public Deck() {
		int nrank = Card.RANK.COUNT.value, nsuit = Card.SUIT.COUNT.value;
		int nback = Card.BACK.COUNT.value;

		card = new Card[N_CARDS];
		used = 0;
		isempty = isshuffled = false;

		for(int back = 0, current = 0; back < nback; ++back)
			for(int suit = 0; suit < nsuit; ++suit)
				for(int rank = 1; rank <= nrank; ++rank, ++current) {
					card[current] = new Card(Card.BACK.fetch(back),
							Card.RANK.fetch(rank), Card.SUIT.fetch(suit),
							current);
				}
	}

	public Deck(boolean shuff) {
		this( );
		if(shuff) shuffDeck( );

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println(this.toString( ));
	}

	/* ------- methods ------- */
	public void shuffDeck() {
		/* this is how much the deck would be mixed-up (must be > 1)
		 * 2 == really mixed
		 * 3 == mixed
		 * 4 == slightly mixed
		 * 5 == really?
		 * 6 == no seriously? */;
		int ratio = (randInt(5) + 2);
		Vector<Integer> isused = new Vector<Integer>(N_CARDS);
		int index0, index1, size = isused.capacity( );

		for(int index = 0; index < size; ++index)
			isused.addElement(new Integer(index));

		for(int c = 0; c < (N_CARDS / ratio); ++c) {
			index0 = pickRandElement(isused, size--);
			index1 = pickRandElement(isused, size--);

			/* usage foo = (bar, bar = foo) */
			card[index0] = Utils
					.swap(card[index1], card[index1] = card[index0]);
		}

		// for(int index = 0; index < N_CARDS; ++index)
		// card[index].setDeckorder(index);

		isshuffled = true;

		if(Debug.enabled) System.out.println("ratio is: " + ratio);
	}

	public Card pickCard() {
		/* give the next */
		return (isempty = !(used < N_CARDS)) ? null : card[used++];
	}

	public Card deal(Game.TYPE gametype, Player[] players) {
		resetJoker( );

		int ncards = gametype.ncards, nplayers = players.length;
		int incr = (gametype == Game.TYPE.SIMPLE ? 1 : 2);

		Card[][] hand = new Card[nplayers][ncards + 1];

		for(int icard = 0; icard < ncards; icard += incr) {
			for(int player = 0; player < nplayers; ++player) {
				hand[player][icard] = pickCard( );
				if(gametype == Game.TYPE.TALAJ) {
					hand[player][icard + 1] = pickCard( );
				}
			}
		}

		Card faux = pickCard( );

		for(int iplayer = 0; iplayer < nplayers; ++iplayer) {
			for(int icard = 0; icard < ncards; ++icard) {
				hand[iplayer][icard].checkJoker(gametype, faux);
			}

			hand[iplayer][ncards] = null; /* set the last card to null */
			players[iplayer].setHand(new Hand(hand[iplayer], gametype));
		}

		return faux;/* fauxjoke */
	}

	private void resetJoker() {
		for(int i = 0; i < card.length; ++i)
			card[i].setAsJoker(false);
	}

	/* ------- override'd methods ------- */
	/** this function use pickCard()
	 * 
	 * @return the whole deck as a String */
	@Override
	public String toString() {
		/* pickCard modifies the used cards. keep a copy of the original # of
		 * used cards */
		int used = this.used, n = 0;
		Card tmpcard = null;
		String tmpstr = "";

		while((tmpcard = pickCard( )) != null) {
			if(!(isshuffled)) {
				tmpstr += ((tmpcard.getRank( ) == Card.RANK.KING) ? "\n" : "");
			}
			tmpstr += tmpcard.toString( ) + "\n";
			++n;
		}

		this.used = used;

		return tmpstr += "\n (" + n + ")";
	}

	/* ------- getters ------- */
	public Card[] getDeck() {
		return card;
	}

	public boolean isEmpty() {
		return isempty;
	}

	public int getUsed() {
		return used;
	}

	/* ------- local methods ------- */
	private int randInt(int size) {
		return new Random( ).nextInt(size);
	}

	private int pickRandElement(Vector<Integer> vect, int size) {
		int tmpindex = randInt(size); /* get random index */
		int foo = vect.elementAt(tmpindex); /* get the corresponding element */

		vect.removeElementAt(tmpindex); /* remove element from the vector */

		return foo;
	}
}
