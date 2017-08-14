package crami;

public class Main {
	public static void main(String[] args) {
		Debug.On( );

		new Game(Game.TYPE.SIMPLE, new Player[] {
				new Player("foo"), new Player("bar")
		}).startGame( );
	}
	
}
