
public class Piece_Infiltrator extends Piece {

	public final int jumpDist = 4;
	
	public Piece_Infiltrator(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(4);
	}
	
	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		
		// Create empty jump action.
		Action_InfiltratorJump jump = new Action_InfiltratorJump(getOwner(), getId());
		
		// Add squares to the jump action that are jumpDist away in each direction.
		for (int i = -jumpDist; i <= jumpDist; i += jumpDist * 2) {
			for (int j = -jumpDist; j <= jumpDist; j += jumpDist * 2) {
				Vector2D pos = getPosition().plus(i, j);
				if (game.getMap().positionIsInBounds(pos) && game.getMap().getSquare(pos).isWalkable()) {
					jump.addSquare(pos);
				}
			}
		}
		
		// If any squares were added, add the action.
		if (jump.hasSquares()) {
			actions.addAction(jump);
		}
		
		// Now create the summon action and add basically all our pieces.
		Action_InfiltratorSummon summon = new Action_InfiltratorSummon(getOwner(), getId());
		for (Piece p : getOwnerPlayer(game).getPieces()) {
			if (p != this && p.allowSelect(game)) {
				summon.addPiece(p.getId());
			}
		}
		
		if (summon.hasPieces()) {
			actions.addAction(summon);
		}
		
		return actions;
	}

}
