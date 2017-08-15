package crami;

/* simple debugging class */
public class Debug {
	public static boolean enabled = false;

	public static void On() {
		enabled = true;
	}

	public static void Off() {
		enabled = false;
	}
}
