package crami;

import java.util.Locale;
import java.util.Vector;
import java.io.*;

import crami.Card.FROM;

public class Game {
	/* ------- enumerations ------- */
	public static enum TYPE {
		SIMPLE(4, 13, true), TALAJ(8, 14, false);

		public final int nplayers;
		public final int ncards;
		public final boolean fromtable;

		TYPE(final int nplayers, final int ncards, final boolean fromtable) {
			this.nplayers = nplayers;
			this.ncards = ncards;
			this.fromtable = fromtable;
		}
	}

	/* ------- local fields ------- */
	private Deck deck;
	private TYPE gametype;
	private Card fauxjoke;
	private boolean istoken;

	private Player[] players;
	private final int nplayers;
	private Player whostrun;

	private Vector<Card> table;

	/* ------- constructors ------- */
	public Game() {
		deck = null;
		players = null;
		gametype = TYPE.SIMPLE;
		nplayers = 0;
		fauxjoke = null;
		istoken = false;
		table = null;
	}

	public Game(TYPE gametype, Player[] players) {
		deck = new Deck(true);
		this.players = players;
		this.nplayers = players.length;
		this.gametype = gametype;
		fauxjoke = null;
		istoken = false;
		table = new Vector<Card>( );
	}

	/* ------- methods ------- */
	// @SuppressWarnings("unused")
	public void startGame() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		Card selected = null;

		if(Debug.enabled) System.out.println(deck.toString( ));

		fauxjoke = deck.deal(gametype, players); /* fareq a sidi dak r'ami */

		if(Debug.enabled) System.out.println(initHands( ));

		int turn = 0;
		while(true) {
			boolean istable = (gametype.fromtable && !(table.isEmpty( )));
			boolean isfaux = (turn < 4 && !(istoken)), isrami = !(deck
					.isEmpty( ));
			String str = "";

			/* yallah a sidi, chkon li fih laeba! */
			whostrun = players[(++turn) % nplayers];

			// TODO: fix the case when the rami ends..

			str += whostrun.getNickname( ) + ", please, select an option:";
			str += "\n\n";

			/* if taking cards from table is allowed */
			if(istable) str += "[t]\t" + table.lastElement( ).toString( )
					+ "\n";

			/* if it is the first turn and the fauxjoke is not token */
			if(isfaux) str += "[f]\t" + fauxjoke.toString( ) + "\n";

			if(isrami) str += "[r]\n$ "; /* card form rami is always allowed */
			else ; /* re-shuffle the deck from the rested cards */

			System.out.print(str);

			/* getting from where the player would take the card */
			Card.FROM tmp = FROM.NULL;

			while(tmp == FROM.NULL) {
				String strerr = "";

				try {
					switch(tmp = Card.FROM.fetch(reader.readLine( )
							.toLowerCase(Locale.getDefault( )).charAt(0))) {
					/* @formatter:off*/
					case 	 RAMI: selected = deck.pickCard( ); 	break;
					case 	TABLE: if(istable) {
									  selected = table.lastElement( );
									  strerr = "";
								   } else { 
									   tmp = Card.FROM.NULL;	
									   strerr = "taking cards from ";
									   strerr += "table is not allowed\n";
								   }
																	break;
					case FAUXJOKE: if(isfaux) { 
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
				} catch(IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace( );
				}
			}

			if(Debug.enabled) System.out.println("selected: "
					+ selected.toString( ));

			/* insert the card in the player's hand */
			whostrun.getHand( ).insertCard(selected);

			if(Debug.enabled) System.out.println(whostrun.toString( ));

			if(whostrun.isMseket( )) break; /* salat a maelen */

			table.add(whostrun.getHand( ).throwCard( )); /* throw a card */

			if(Debug.enabled) System.out.println(table.lastElement( )
					.toString( ));
		}

		try {
			reader.close( );
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}
	}

	/* ------- local functions ------- */
	private String initHands() {
		String strhands = "";

		for(int iplayer = 0; iplayer < players.length; ++iplayer) {
			strhands += players[iplayer].getNickname( ) + "\n\n";
			for(int icard = 0; icard < gametype.ncards + 1; ++icard) {
				if(players[iplayer].getHand( ).getCardAt(icard) != null) {
					String strcard = players[iplayer].getHand( )
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
