package crami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** useful functions */
public class Utils {
	/** this trick works because Java guarantees that all arguments are
	 * evaluated from left to right. so this trick implements the swap
	 * method since there's no pointers in Java. so sad!
	 * 
	 * @usage foo = (bar, bar = foo)
	 * 
	 * @param c1
	 *            the first Card
	 * @param c1
	 *            the second Card
	 * 
	 * @return c1 the first Card */
	public static Card swap(Card c1, Card c2) {
		return c1;
	}

	private static BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

	public static void cleaner() {
		try {
			reader.close( );
		} catch(IOException ignore) {}
	}

	public static int getInt(String msg, int min, int max) {

		System.out.printf("%s\n", msg);

		int index = 0;
		while(index < min || index > max) {
			if(Debug.enabled) {
				System.out.println("###");
				System.out.flush( );
			}

			try {
				index = Integer.parseInt(reader.readLine( ));
			} catch(NumberFormatException | IOException ignore) {}
		}

		return index;
	}

	public static char getChar() {
		char tmp = '\0';
		while(tmp == '\n' || tmp == '\0' || tmp == '\t') {
			try {
				tmp = (char) System.in.read( );
			} catch(IOException ignore) {}
		}
		return tmp;
	}
}
