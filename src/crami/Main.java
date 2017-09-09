package crami;

/* i'm writing this because i feel bad and i want to change this situation
 * right now! i wish is i had some money so that i can continue my studies
 * but, it seems like there's no other way except selling my time for some
 * amount of money!
 * 
 * today, i want to create a new future! I would release this game and hope
 * that i would make some money..
 * 
 * Amen! (Shit! i'm not a believer!) */

/* TODO:  comment the fucking code! 
 * 
 * comment each function using javadoc comments and embed 
 * comments inside the body */
public class Main {
	public static void main(String[] args) {
		Debug.On( );

		testing( );

		// run( );
	}

	public static void run() {
		new Game(Game.TYPE.SIMPLE, new Player[] {
				new Player("foo"), new Player("bar"), new Player("baz")
		}).startGame( );
	}

	public static void testing() {
		System.out.println(new Player( ).isRandked(new Card[] {
				new Card(Card.BACK.BLUE, Card.RANK.ACE, Card.SUIT.DIAMONDS, 2),
				new Card(Card.BACK.BLUE, Card.RANK.ACE, Card.SUIT.CLUBS, 3),
				new Card(Card.BACK.BLUE, Card.RANK.ACE, Card.SUIT.CLUBS, 3),
				new Card(Card.BACK.BLUE, Card.RANK.ACE, Card.SUIT.CLUBS, 3),
		}) ? "TRUE" : "FALSE");
		/* new Card(Card.BACK.BLUE, Card.RANK.FIVE, Card.SUIT.HEARTS, 3,
		 * true) */

		System.out.println(new Player( ).isSuited(new Card[] {
				new Card(Card.BACK.BLUE, Card.RANK.QUEEN, Card.SUIT.CLUBS, 8),
				new Card(Card.BACK.BLUE, Card.RANK.ACE, Card.SUIT.CLUBS, 2),
				new Card(Card.BACK.BLUE, Card.RANK.JACK, Card.SUIT.CLUBS, 3),
				new Card(Card.BACK.BLUE, Card.RANK.KING, Card.SUIT.HEARTS, 6,
						true),
		}) ? "TRUE" : "FALSE");
	}
}
