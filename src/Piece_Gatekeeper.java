
public class Piece_Gatekeeper extends Piece {

	public Piece_Gatekeeper(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(8);
	}
	
	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		Action_Gate action = new Action_Gate(getOwner(), getId());
		
		// Can only toggle gates if generator has power.
		if (game.getGameStateVar("generator") > 0) {
			
			// Add squares to the Action_Gate for every adjacent gate.
			for (Vector2D pos : getAdjacentPositions(true)) {
				if (game.getMap().getSquare(pos) instanceof Square_Gate) {
					action.addSquare(pos);
				}
			}
		}
		
		// If we're near at least one gate, add the action to the list.
		if (action.hasSquares()) {
			actions.addAction(action);
		}
		
		return actions;
	}

}
