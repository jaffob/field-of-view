
public class Piece_Guardian extends Piece {

	public Piece_Guardian(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(8);
	}
	
	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		
		// Guardians can only kill if they haven't moved this turn.
		if (getMovesThisTurn() == 0) {
			
			Action_GuardianKill action = new Action_GuardianKill(getOwner(), getId());
			
			// Use a Raycast to get kill-able pieces in every direction.
			Raycast rays = new Raycast() {
				@Override
				public boolean onPosition(Vector2D position) {
					Square currSq = game.getMap().getSquare(position);
					
					// If this square isn't walkable, stop the ray here.
					if (!currSq.isWalkable()) {
						return false;
					}
					
					// If this square is occupied, add its occupant as a target and stop the ray.
					if (currSq.isOccupied()) {
						action.addPiece(currSq.getOccupant());
						return false;
					}
					
					// We didn't hit anything; keep going.
					return true;
				}
			};
			
			rays.castAllDirs(getPosition(), game.getMap().getSize());
			
			// Add the kill action to the list if we found any targets.
			if (action.hasPieces()) {
				actions.addAction(action);
			}
		}
		
		return actions;
	}

}
