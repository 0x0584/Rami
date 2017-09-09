package crami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

	private Card[] hand;
	private Game.TYPE gametype;
	private int lastindex; /* this is the index of the last card in-card */
	private static BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

	public Hand(Card[] hand, Game.TYPE gametype) {
		this.hand = hand;
		this.gametype = gametype;
		lastindex = gametype.ncards;
	}

	static public void cleanUp() {
		try {
			reader.close( );
		} catch(IOException ignore) {}
	}

	/* ------- methods ------- */
	public boolean vefiryCombination(COMBINATION fetch) {
		/* FIXME: this is getting fucking out of control!
		 * 
		 * first, detect what number of cards you want to take each time.
		 * then, take that number, each time and verify the rule of purity */

		int[] ncards;

		switch(fetch) {
		/* @formatter:off */
		default:
		case FTTT: ncards = new int[] { 4, 3, 3, 3 }; break;
		case FFF:  ncards = new int[] { 5, 4, 4 };    break;
		case FFT:  ncards = new int[] { 5, 5, 3 };    break;
		/* @formatter:on */
		}

		for(int icombi = 0; icombi < ncards.length; ++icombi) {
			Card[] part = new Card[ncards[icombi]];

			for(int icard = 0, index = 0; icard < ncards[icombi]; ++icombi) {
				/* FIXME: verify duplicates
				 * 
				 * whether the selected card has been already selected */
				System.out.println(hand.toString( )
						+ "\nselect a card from 1 to" + (gametype.ncards + 1));

				while(index < 1 || index > 15) {
					try {
						index = Integer.parseInt(reader.readLine( ));
					} catch(NumberFormatException | IOException ignore) {}
				}

				part[icard] = hand[index - 1];
			}

			/* FIXME: detect whether part is a suited or ranked
			 * 
			 * that's beside the joker case! you have work to do tomorrow */
		}

		return false;
	}

	public void insertCard(Card card) {
		hand[lastindex] = card;
		lastindex = -1; /* not sure why! */
	}

	public Card throwCard() {
		/* TODO: find a better way! */

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println("before:\n" + toString( ));

		int tmp = -1;
		while(tmp < 1 || tmp > (gametype.ncards + 1)) {
			System.out.println("chose a card from 1 to "
					+ (gametype.ncards + 1));

			try {
				tmp = Integer.parseInt(reader.readLine( ));
			} catch(NumberFormatException | IOException ignore) {}

			lastindex = tmp - 1;
		}

		Card it = hand[lastindex];
		hand[lastindex] = null;

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println("after:\n" + toString( ));

		return it;
	}

	@Override
	public String toString() {
		String str = "";

		for(int i = 0; i <= gametype.ncards; ++i)
			if(hand[i] != null) str += hand[i].toString("%s%j");

		return str + "\n";
	}

	public Card getCardAt(int index) {
		return (index >= 0 && index <= gametype.ncards) ? hand[index] : null;
	}
}
