
public class Piece_Swordsman extends Piece {

	public Piece_Swordsman(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(5);
	}
	
	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		Action_SwordsmanKill action = new Action_SwordsmanKill(getOwner(), getId());
		
		// For each player...
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			
			// If this player has a piece adjacent to us, add a target.
			for (Piece p : game.getPlayer(i).getPieces()) {
				if (p.getPosition().isAdjacentTo(getPosition()) &&
						!p.isSpawnProtected()) {
					action.addPiece(p.getId());
				}
			}
		}
		
		// Add the kill action to the list if we found any targets.
		if (action.hasPieces()) {
			actions.addAction(action);
		}
		
		return actions;
	}

}
