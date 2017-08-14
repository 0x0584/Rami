package crami;

public class Main {
	public static void main(String[] args) {
		Debug.On( );

		new Game(Game.TYPE.TALAJ, new Player[] {
				new Player("foo"), new Player("bar"), new Player("baz")
		}).startGame( );
	}
}
