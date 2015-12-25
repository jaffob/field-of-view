import java.util.HashMap;


public class SquareFactory {

	private final HashMap<Character, Class<Square>> squareTypes;
	
	public SquareFactory() {
		squareTypes = new HashMap<Character, Class<Square>>();
	}
	
	protected Class<Square> getSquareType(char squareType) {
		return squareTypes.get(squareType);
	}
	
	protected void addSquareType(char squareType, Class<Square> squareClass) {
		squareTypes.put(squareType, squareClass);
	}
	
	public Square createSquare(char squareType, char squareProperties) {
		Square sq = new Square('\0');
		return sq;
	}

}
