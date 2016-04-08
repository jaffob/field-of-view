
public class Piece_Grenadier extends Piece {

	private int grenadeThrowDist;
	
	public Piece_Grenadier(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(6);
		setGrenadeThrowDist(3);
	}

	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		
		// Grenadiers can only kill if they haven't moved this turn.
		// Also, why does Eclipse think grenadier isn't a word?
		if (getMovesThisTurn() == 0) {
			
			Action_GrenadierThrow action = new Action_GrenadierThrow(getOwner(), getId());
			
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Vector2D pos = getPosition().plus(i * getGrenadeThrowDist(), j * getGrenadeThrowDist());
					
					if ((i != 0 || j != 0) &&
							game.getMap().positionIsInBounds(pos) &&
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

	public int getGrenadeThrowDist() {
		return grenadeThrowDist;
	}

	public void setGrenadeThrowDist(int grenadeThrowDist) {
		this.grenadeThrowDist = grenadeThrowDist;
	}
}
