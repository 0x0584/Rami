package crami;

/* TODO:  comment the fucking code! 
 * 
 * comment each function using javadoc comments and embed 
 * comments inside the body */
public class Main {
	public static void main(String[] args) {
		Debug.On( );
//		testing( );

		 run( );
	}

	public static void run() {

		new Game(Game.TYPE.SIMPLE, new Player[] {
				new Player("foo"), new Player("bar"), new Player("baz")
		}).startGame( );
	}

	public static void testing() {
		int[] test = new int[] {
				1, 2, 3, 4, 5
		};
		for(int i = 0; i < test.length; i++) {
			System.out.printf("%d %s", test[i], i == test.length - 1 ? "\n"
					: "");
		}

		boolean takeit = true;
		System.out.printf("%d", Utils.getInt("$ " + !takeit, 0, 10, !takeit, test));

	}
}
