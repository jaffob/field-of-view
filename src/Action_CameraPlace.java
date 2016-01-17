
public class Action_CameraPlace extends Action_Square {

	public Action_CameraPlace(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doActionOnSquare(FieldOfView game, Vector2D squarePos) {
		addActionPositionAtActor(game);
		addActionPosition(squarePos);
		
		if (game.spawnPiece(Piece_Camera.class, getPlayerNum(), squarePos)) {
			Piece actor = getActorPiece(game);
			if (actor != null && actor instanceof Piece_Scout) {
				((Piece_Scout)actor).removeCameraRemaining();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getFriendlyName() {
		return "Place Camera";
	}

}
