package crami;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** useful functions */
public class Utils {
	private static BufferedReader reader = new BufferedReader(
			new InputStreamReader(System.in));

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

	public static void cleaner() {
		try {
			reader.close( );
		} catch(IOException ignore) {}
	}

	/** checks whether a certain elements exists (or not) in `range`
	 * 
	 * @param range
	 *            range of elements
	 * @param exclude
	 *            elements are in or are out
	 * @param items
	 *            the items to check
	 * @return */
	public static boolean isIn(int[] range, boolean exclude, int... items) {
		boolean[] exists = new boolean[items.length + 1];

		for(int i = 0, ie = 0; i < range.length; i++) {
			for(int j = 0; j < items.length; j++) {
				if((range[i] == items[j]) && exclude) {
					exists[ie++] = true;
					break;
				}
			}
		}

		/* true if all the cards exists. otherwise false */
		boolean isin = true;

		for(int ie = 0; ie < exists.length - 1; isin &= exists[ie], ie++);

		return isin;
	}

	public static boolean isIn(Card[] range, boolean exclude,
			Card.RANK... items) {
		boolean[] exists = new boolean[items.length];

		for(int i = 0, ie = 0; i < range.length; i++) {
			for(int j = 0; j < items.length; j++) {
				if((range[i].getRank( ) == items[j]) && exclude) {
					exists[ie++] = true;
					break;
				}
			}
		}

		/* true if all the cards exists. otherwise false */
		boolean isin = true;

		for(int ie = 0; ie < exists.length; isin &= exists[ie], ie++);

		return isin;
	}

	/** get an int with [`min`, `max`] range
	 * 
	 * @param min
	 *            minimum
	 * @param max
	 *            maximum
	 * @return */
	public static int getInt(int min, int max) {
		return getInt("", min, max);
	}

	/** same as `getInt()` with prompt
	 * 
	 * @param prompt
	 *            string to prompt
	 * @param min
	 * @param max
	 * @return */
	public static int getInt(String prompt, int min, int max) {
		int index = Integer.MIN_VALUE;

		while(index < min || index > max) {
			System.out.printf("%s\n", prompt);
			System.out.flush( );

			try {
				index = Integer.parseInt(reader.readLine( ));
			} catch(NumberFormatException | IOException ignore) {}
		}
		return index;
	}

	/** get an int with [`min`, `max`] range
	 * 
	 * @param prompt
	 *            message
	 * @param min
	 *            minimum
	 * @param max
	 *            maximum
	 * @param include
	 * 
	 * @param items
	 * @return */
	public static int getInt(String prompt, int min, int max, boolean isin,
			int[] items) {

		while(true) {
			boolean satisfied = false; /* if `elem` is in `items` */
			System.out.println("sss");
			/* loop through elements */
			for(int i = 0, elem = getInt(prompt, min, max); i < items.length; i++) {
				satisfied = isin ? (items[i] != elem) : (items[i] == elem);
				if(satisfied) return elem;
			}
		}
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
