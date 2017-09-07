package crami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hand {
	private Card[] hand;
	private Game.TYPE gametype;
	private int lastindex; /* this is the index of the last card in-card */

	public Hand(Card[] hand, Game.TYPE gametype) {
		this.hand = hand;
		this.gametype = gametype;
		lastindex = gametype.ncards;
	}

	/* ------- methods ------- */
	public void insertCard(Card card) {
		hand[lastindex] = card;
		lastindex = -1; /* not sure why! */
	}

	public Card throwCard() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		/* TODO: find a better way! */

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println("before:\n" + toString( ));

		int tmp = -1;
		while(tmp < 1 || tmp > (gametype.ncards + 1)) {
			System.out.println("chose a card from 1 to 14");

			try {
				tmp = Integer.parseInt(reader.readLine( ));
			} catch(NumberFormatException e) {
				e.printStackTrace( );
			} catch(IOException e) {
				e.printStackTrace( );
			}

			lastindex = tmp - 1;
		}

		Card it = hand[lastindex]; /* convert nullindex to real index */
		hand[lastindex] = null;

		try {
			reader.close( );
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println("after:\n" + toString( ));

		return it;
	}

	@Override
	public String toString() {
		String str = "";

		for(int i = 0; i <= gametype.ncards; ++i)
			if(hand[i] != null) str += hand[i].toString( ) + "\n";

		return str;
	}

	public Card getCardAt(int index) {
		return (index >= 0 && index <= gametype.ncards) ? hand[index] : null;
	}

}
