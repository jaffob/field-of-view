
public class Action_GrenadierThrow extends Action_Square {

	public Action_GrenadierThrow(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doActionOnSquare(FieldOfView game, Vector2D squarePos) {
		
		// The opponent knows everything about the grenade throw
		// if either they see the grenadier, or they see where the
		// grenade lands.
		addActionPositionAtActor(game);
		addActionPosition(squarePos);
		
		// For this square and all adjacent squares...
		for (Vector2D pos : squarePos.getAdjacentPositions(true)) {
			
			// Damage any piece occupying the square.
			int victim = game.getMap().getSquare(pos).getOccupant();
			if (victim > 0) {
				game.killPiece(victim, false);
			}
		}
		
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "Throw Grenade";
	}

}
