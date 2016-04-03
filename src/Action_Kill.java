
public class Action_Kill extends Action_Piece {

	public Action_Kill(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doActionOnPiece(FieldOfView game, Integer pieceId) {
		addActionPositionAtActor(game);
		return game.killPiece(pieceId, false);
	}

	@Override
	public String getFriendlyName() {
		return "Kill";
	}
}
