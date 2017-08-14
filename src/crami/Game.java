package crami;

import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

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
	}

	public Game(TYPE gametype, Player[] players) {
		deck = new Deck(true);
		this.players = players;
		this.nplayers = players.length;
		this.gametype = gametype;
		fauxjoke = null;
		istoken = false;
	}

	/* ------- methods ------- */
	public void startGame() {
		Scanner reader = new Scanner(System.in);
		int turn = 0;

		whostrun = players[++turn]; /* yallah a sidi, chkon li fih laeba! */

		if(Debug.enabled) System.out.println(deck.toString( ));

		fauxjoke = deck.deal(gametype, players); /* fareq a sidi dak r'ami */

		if(Debug.enabled) System.out.println(initHands( ));

		while(true) {
			Card selected = null;
			boolean ttable = (gametype.fromtable && !(table.isEmpty( )));
			boolean faux = (turn < 4 && !(istoken));

			System.out.println("Select an option: ");

			option: {
				if(ttable) System.out.println("[t]able:\t"
						+ table.lastElement( ).toString( ));

				if(faux) System.out.println("[f]auxjoke:\t"
						+ fauxjoke.toString( ));

				System.out.print("[r]ami\n$ ");

				switch(Card.FROM.fetch(reader.nextLine( )
						.toLowerCase(Locale.getDefault( )).charAt(0))) {
				/* @formatter:off*/
				case 	 RAMI: selected = deck.pickCard( ); 		break;
				case 	TABLE: selected = table.lastElement( );		break;
				case FAUXJOKE: selected = fauxjoke; istoken = true;	break;
				default: System.out.println("ERR\n");				break option;
				/* @formatter:on */
				}

				reader.close( );
			}

			if(Debug.enabled) System.out.println(selected.toString( ));

			whostrun.insertCard(selected, gametype);

			if(Debug.enabled) System.out.println(whostrun.toString( ));

			if(whostrun.isMseket( )) break; /* salat a maelen */

			table.add(whostrun.throwCard( )); /* throw a card */

			whostrun = players[(++turn) % nplayers]; /* chkon li fih laeba */
		}
	}

	/* ------- local functions ------- */
	private String initHands() {
		String str = "";

		for(int iplayer = 0; iplayer < players.length; ++iplayer) {
			for(int icard = 0; icard < gametype.ncards + 1; ++icard)
				if(players[iplayer].getHand( )[icard] != null) {
					str += players[iplayer].getHand( )[icard].toString( )
							+ "\n";
				}
			str += "\n";
		}

		str += fauxjoke.toString( ) + "\n";

		return str;
	}
}
