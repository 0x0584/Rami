package crami;

import java.util.Locale;
import java.util.Vector;
import java.io.*;

import crami.Card.FROM;

public class Game {
	/* ------- enumerations ------- */
	public static enum TYPE {
		SIMPLE(4, 13, true), TALAJ(4, 14, false);

		public final int nplayers;
		public final int ncards;
		public final boolean tableallowed;

		TYPE(final int nplayers, final int ncards, final boolean allowed) {
			this.nplayers = nplayers;
			this.ncards = ncards;
			this.tableallowed = allowed;
		}
	}

	/* ------- local fields ------- */
	private Deck deck;
	private TYPE gametype;
	private Card fauxjoke;
	private boolean istoken;

	private Player[] player;
	private final int N_PLAYERS;

	private Vector<Card> table;

	/* ------- constructors ------- */
	public Game() {
		deck = null;
		player = null;
		gametype = TYPE.SIMPLE;
		N_PLAYERS = 0;
		fauxjoke = null;
		istoken = false;
		table = null;
	}

	public Game(TYPE gametype, Player[] players) {
		deck = new Deck(true);
		this.player = players;
		this.N_PLAYERS = players.length;
		this.gametype = gametype;
		fauxjoke = null;
		istoken = false;
		table = new Vector<Card>( );
	}

	/* ------- methods ------- */
	// @SuppressWarnings("unused")
	private Card cardSelection(boolean[] factor) {

		Card selected = null;
		String str = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		/* if taking cards from table is allowed */
		if(factor[Card.FROM.TABLE.val]) str += "[t]\t"
				+ table.lastElement( ).toString( ) + "\n";

		/* if it is the first turn and the fauxjoke is not token */
		if(factor[Card.FROM.FAUXJOKER.val]) str += "[f]\t"
				+ fauxjoke.toString( ) + "\n";

		if(factor[Card.FROM.RAMI.val]) str += "[r]\n$ "; /* card form rami is
														 * always allowed */
		// TODO: fix the case when the rami ends..
		else ; /* re-shuffle the deck from the rested cards */

		System.out.print(str);

		/* getting from where the player would take the card */

		Card.FROM tmp = FROM.NULL;
		try {
			while(tmp == FROM.NULL) {
				String strerr = "";
				tmp = Card.FROM.fetch(reader.readLine( )
						.toLowerCase(Locale.getDefault( )).charAt(0));
				switch(tmp) {
				/* @formatter:off*/
			case 	 RAMI: selected = deck.pickCard( ); 	break;
			case 	TABLE: if(factor[Card.FROM.TABLE.val]) {
							  selected = table.lastElement( );
							  strerr = "";
						   } else { 
							   tmp = Card.FROM.NULL;	
							   strerr += "taking cards from ";
							   strerr += "table is not allowed\n";
						   }
															break;
			case FAUXJOKER: if(factor[Card.FROM.FAUXJOKER.val]) { 
							  selected = fauxjoke; 
							  istoken = true;
							  strerr = "";
						   } else {
							   strerr = "faux is not available\n";
							   tmp = Card.FROM.NULL;
						   }
															break;
			default: 
			case     NULL: strerr = "ERR\n";
			/* @formatter:on */
				}

				System.out.print(strerr);
			}
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}

		try {
			reader.close( );
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) {
			System.out.println("selected: " + selected.toString( ));
		}

		return selected;
	}

	public void startGame() {
		fauxjoke = deck.deal(gametype, player); /* fareq a sidi dak r'ami */

		/* ---------------------- DEBUG ---------------------- */
		if(Debug.enabled) System.out.println(initHands( ));

		int turn = 0;
		while(true) {
			/* 0. yallah a sidi, chkon li fih laeba! */
			Player current = player[(++turn) % N_PLAYERS];

			/* --------------------- DEBUG ---------------------- */
			if(Debug.enabled) System.out.println(current.getNickname( )
					+ ", please, select an option:\n\n");

			/* 1. insert the selected card in hand */
			current.getHand( ).insertCard(cardSelection(new boolean[] {
					/* table, if allowed and not empty */
					(gametype.tableallowed && !(table.isEmpty( ))),
					/* faux joker, if's the first round and not token */
					(turn < gametype.nplayers && !(istoken)),
					/* rami, i'm not sure if this one is necessary */
					!(deck.isEmpty( ))
			}));

			/* ---------------------- DEBUG ---------------------- */
			if(Debug.enabled) System.out.println(current.toString( ));

			/* 2. check whether you're mseket or not */
			if(current.isMseket( )) break; /* salat a maelen */

			/* 3. if not mseket, throw a card */
			table.add(current.getHand( ).throwCard( )); /* throw a card */

			/* ---------------------- DEBUG ---------------------- */
			if(Debug.enabled) {
				System.out.println(table.lastElement( ).toString( ));
			}
		}
	}

	/* ------- local functions ------- */
	private String initHands() {
		String strhands = "";

		for(int iplayer = 0; iplayer < player.length; ++iplayer) {
			strhands += player[iplayer].getNickname( ) + "\n\n";
			for(int icard = 0; icard < gametype.ncards + 1; ++icard) {
				if(player[iplayer].getHand( ).getCardAt(icard) != null) {
					String strcard = player[iplayer].getHand( )
							.getCardAt(icard).toString( );
					strhands += strcard + "\n";
				}
			}
			strhands += "\n";
		}

		strhands += fauxjoke.toString( ) + "\n";

		return strhands;
	}
}
