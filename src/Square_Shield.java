
public class Square_Shield extends Square {

	public Square_Shield(Vector2D position, Integer properties) {
		super(position, properties);
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}
	
	@Override
	public ActionList getSpecialActions(FieldOfView game, Piece piece) {
		ActionList actions = super.getSpecialActions(game, piece);
		
		// Can only shield pieces if generator has power.
		if (game.getGameStateVar("generator") > 0) {
			Action_Shield shieldAction = new Action_Shield(piece.getOwner(), piece.getId());
			
			// Find viable candidates to be shielded. This square allows a
			// maximum shield level of 1, and a king can't shield itself.
			for (Piece p : piece.getOwnerPlayer(game).getPieces()) {
				if (p.getShieldLevel() == 0 && p.getId() != piece.getId()) {
					shieldAction.addPiece(p.getId());
				}
			}
			
			// If there's a piece that can be shielded, add the shield action.
			if (shieldAction.hasPieces()) {
				actions.addAction(shieldAction);
			}
		}
		
		return actions;
	}

}
