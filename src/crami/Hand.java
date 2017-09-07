package crami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hand {
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
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}
	}

	/* ------- methods ------- */
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
			} catch(NumberFormatException | IOException e) {
				e.printStackTrace( );
			}

			lastindex = tmp - 1;
		}

		Card it = hand[lastindex]; /* convert nullindex to real index */
		hand[lastindex] = null;

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println("after:\n" + toString( ));

		return it;
	}

	@Override
	public String toString() {
		String str = "";

		for(int i = 0; i <= gametype.ncards; ++i)
			if(hand[i] != null) str += hand[i].toString("%s");

		return str + "\n";
	}

	public Card getCardAt(int index) {
		return (index >= 0 && index <= gametype.ncards) ? hand[index] : null;
	}

}
