package crami;

import java.util.Vector;

public class Score {
	private Vector<Integer> hsab;
	private boolean isdead;
	private int total;
	private final int LIMIT;

	public Score() {
		hsab = new Vector<Integer>( );
		total = 0;
		LIMIT = 100;

		isdead = false;
	}

	public Score(int init, final int limit) {
		hsab = new Vector<Integer>( );
		total = init;
		LIMIT = limit;

		isdead = false;
	}

	public void increaseScore(int hsab) {
		if(!(isdead)) {
			this.hsab.addElement(hsab);
			isdead = ((total += hsab) >= LIMIT);
		}
	}

	/* ------- getters ------- */
	public Vector<Integer> getHsab() {
		return hsab;
	}

	public int getScore() {
		return total;
	}

	public boolean isDead() {
		return isdead;
	}

	public final int getLimit() {
		return LIMIT;
	}
}
