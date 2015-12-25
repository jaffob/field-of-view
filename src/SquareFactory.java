import java.util.HashMap;


public class SquareFactory {

	private final HashMap<Character, Class<Square>> squareTypes;
	
	public SquareFactory() {
		squareTypes = new HashMap<Character, Class<Square>>();
	}
	
	public Class<Square> getSquareType(char squareType) {
		return squareTypes.get(squareType);
	}
	
	public void addSquareType(char squareType, Class<Square> squareClass) {
		squareTypes.put(squareType, squareClass);
	}
	
	public Square createSquare(char squareType, char squareProperties) {
		Square sq;
		try {
			sq = getSquareType(squareType).getConstructor(Character.class).newInstance(squareProperties);
		} catch (Exception e) {
			return null;
		}
		return sq;
	}

}
