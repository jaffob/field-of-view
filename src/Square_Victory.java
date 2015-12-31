
public class Square_Victory extends Square {

	public Square_Victory(Vector2D position, Integer properties) {
		super(position, properties);
		
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}
	
	@Override
	public boolean canEnter(Piece piece, Direction dir) {
		// You must be a goal piece to enter this square.
		return super.canEnter(piece, dir) && piece.isGoalPiece();
	}

	@Override
	public ActionList getGoalActions(FieldOfView game, Piece piece) {
		ActionList actions = super.getGoalActions(game, piece);
		if (piece.getOwner() != getSide()) {
			actions.addAction(new Action_Victory(piece.getOwner(), piece.getId(), getSide()));
		}
		return actions;
	}
}
