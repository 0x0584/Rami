package crami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hand {
	private Card[] hand;
	private Game.TYPE gametype;
	private int nullindex;

	public Hand(Card[] hand, Game.TYPE gametype) {
		this.hand = hand;
		this.gametype = gametype;
		nullindex = gametype.ncards;
	}

	/* ------- methods ------- */
	public void insertCard(Card card) {
		hand[nullindex] = card;
		nullindex = -1;
	}

	public Card throwCard() {
		int tmp = -1;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		/* TODO: find a better way! */

		// if(Debug.enabled) System.out.println(toString( ));

		while(tmp < 1 || tmp > 14) {
			System.out.println("chose a card from 1 to 14");

			try {
				tmp = Integer.parseInt(reader.readLine( ));
			} catch(NumberFormatException e) {
				e.printStackTrace( );
			} catch(IOException e) {
				e.printStackTrace( );
			}

			nullindex = tmp - 1;
		}

		Card it = hand[nullindex]; /* convert nullindex to real index */
		hand[nullindex] = null;

		
		try {
			reader.close( );
		} catch(IOException e) {
			e.printStackTrace( );
		}
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
