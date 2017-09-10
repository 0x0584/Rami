package crami;

/* TODO:  comment the fucking code! 
 * 
 * comment each function using javadoc comments and embed 
 * comments inside the body */
public class Main {
	public static void main(String[] args) {
		Debug.On( );

		testing( );

		run( );
	}

	public static void run() {

		new Game(Game.TYPE.SIMPLE, new Player[] {
				new Player("foo"), new Player("bar"), new Player("baz")
		}).startGame( );
	}

	public static void testing() {

	}
}
