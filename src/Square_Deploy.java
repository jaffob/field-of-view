
public class Square_Deploy extends Square {

	public Square_Deploy(Vector2D position, Integer properties) {
		super(position, properties);
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}
	
	@Override
	public ActionList getSpecialActions(FieldOfView game, Piece piece) {
		ActionList actions = super.getSpecialActions(game, piece);
		
		// Make sure at least 1 spawn square is available.
		boolean squareAvailable = false;
		for (int i = 0; i < game.getMap().getSquares().length; i++) {
			for (int j = 0; j < game.getMap().getSquares()[0].length; j++) {
				Square sq = game.getMap().getSquare(i, j);
				if (sq instanceof Square_Spawn 
						&& (sq.getSide() == 0 || sq.getSide() == piece.getOwner())
						&& sq.isWalkable() && !sq.isOccupied()) {
					squareAvailable = true;
					break;
				}
			}
		}
		
		// Create deploy actions for each type of piece.
		if (squareAvailable) {
			actions.addAction(new Action_Deploy(piece.getOwner(), piece.getId(), Piece_Gatekeeper.class, "Gatekeeper"));
		}
		
		return actions;
	}


}