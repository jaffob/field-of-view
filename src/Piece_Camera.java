
public class Piece_Camera extends Piece {

	public Piece_Camera(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(0);
	}
	
	@Override
	public boolean allowSelect(FieldOfView game) {
		return false;
	}
}
