
public class Piece_Grenadier extends Piece {

	public Piece_Grenadier(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(6);
	}

	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		
		// Grenadiers can only kill if they haven't moved this turn.
		// Also, why does Eclipse think grenadier isn't a word?
		if (getMovesThisTurn() == 0) {
			
			Action_GrenadierThrow action = new Action_GrenadierThrow(getOwner(), getId());
			
			for (int i = -4; i <= 4; i += 8) {
				for (int j = -4; j <= 4; j += 8) {
					Vector2D pos = getPosition().plus(i, j);
					
					if (game.getMap().positionIsInBounds(pos) &&
							game.getMap().getSquare(pos).isWalkable()) {
						action.addSquare(pos);
					}
				}
			}
			
			// Add the kill action to the list if we found any targets.
			if (action.hasSquares()) {
				actions.addAction(action);
			}
		}
		
		return actions;
	}
}
