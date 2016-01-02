
public class Piece_Gatekeeper extends Piece {

	public Piece_Gatekeeper(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(8);
	}
	
	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		
		// Can only toggle gates if generator has power.
		if (game.getGameStateVar("generator") > 0) {
			
			// If this piece is next to a gate, add a toggle action.
			for (Vector2D pos : getAdjacentPositions(true)) {
				if (game.getMap().getSquare(pos) instanceof Square_Gate) {
					actions.addAction(new Action_Gate(getOwner(), getId()));
					break;
				}
			}
		}
		
		return actions;
	}

}
