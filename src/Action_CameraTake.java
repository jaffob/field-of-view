
public class Action_CameraTake extends Action_Square {

	public Action_CameraTake(int player, int actor) {
		super(player, actor);
		setEndsTurn(false);
	}

	@Override
	public boolean doActionOnSquare(FieldOfView game, Vector2D squarePos) {
		addActionPositionAtActor(game);
		addActionPosition(squarePos);
		
		// Get the Piece that represents the camera being taken.
		Piece targetCamera = game.getPieceById(game.getMap().getSquare(squarePos).getOccupant());
		
		if (targetCamera != null && targetCamera instanceof Piece_Camera) {
			
			// Destroy the camera piece. If successful, add a remaining camera for the scout.
			if (game.killPiece(targetCamera.getOwner(), targetCamera.getId(), true)) {
				Piece actor = getActorPiece(game);
				if (actor != null && actor instanceof Piece_Scout) {
					((Piece_Scout)actor).addCameraRemaining();
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String getFriendlyName() {
		return "Take Camera";
	}

}
