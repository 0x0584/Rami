package crami;

/* i'm writing this because i feel bad and i want to change this situation right now!
 * i wish is i had some money so that i can continue my studies but, it seems like 
 * there's no other way except selling my time for some amount of money!
 * 
 * today, i want to create a new future! I would release this game and hope that 
 * i would make some money
 * 
 * Amen!*/

public class Main {
	public static void main(String[] args) {
		Debug.On( );

		run( );
	}

	public static void run() {
		new Game(Game.TYPE.TALAJ, new Player[] {
				new Player("foo"), new Player("bar")
		}).startGame( );
	}

}
