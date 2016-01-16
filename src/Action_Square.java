import java.util.ArrayList;

/**
 * Action_Square
 * A type of action that can be performed on a square, or takes
 * a specific square as input. This action is populated with all
 * squares that can be used, then asks the user (controller) which
 * square to use.
 */
public abstract class Action_Square extends Action {

	private final ArrayList<Vector2D> squares;
	
	public Action_Square(int player, int actor) {
		super(player, actor);
		squares = new ArrayList<Vector2D>();
	}

	public ArrayList<Vector2D> getSquares() {
		return squares;
	}
	
	public void addSquare(Vector2D squarePos) {
		squares.add(squarePos);
	}
	
	public Vector2D getSquare(int index) {
		return squares.get(index);
	}
	
	public boolean hasSquares() {
		return !squares.isEmpty();
	}

	@Override
	public boolean doAction(FieldOfView game) {
		return doActionOnSquare(game, getSquare(getPlayer(game).selectSquare(getSquares())));
	}

	public abstract boolean doActionOnSquare(FieldOfView game, Vector2D squarePos);
}
