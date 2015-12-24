
public final class Utils {

	public static final boolean DEBUG_MODE = true;
	
	public static final int MAX_NAME_LENGTH = 64;
	public static final int MIN_MAP_SIZE = 4;
	public static final int MAX_MAP_SIZE = 65536;
	private Utils() {}
	
	public static boolean isValidName(String s)
	{
		if (s.isEmpty() || s.length() > 64) return false;
		
		String specials = ",.-:;'()&#–—?!*%$@\"[]{}<>";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			
			if (!Character.isAlphabetic(c) && !Character.isDigit(c) && !Character.isSpaceChar(c) && specials.indexOf(c) == -1) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void log(String s) {
		if (DEBUG_MODE) {
			System.out.println(s);
		}
	}
}
