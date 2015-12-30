
public class Piece_King extends Piece {

	public Piece_King(int ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		
		setMaxMoves(10);
		setGoalPiece(true);
		setInvulnerable(true);
		setCanUseSpecialSquares(true);
	}

}
