
public class Action_Move extends Action {

	private final Direction direction;
	
	public Action_Move(int player, int actor, Direction direction) {
		super(player, actor);
		this.direction = direction;
		setEndsTurn(false);
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public void doAction(FieldOfView game) {
		Piece actorPiece = game.getPlayer(getPlayer()).getPieceById(getActor());
		if (actorPiece == null) {
			return;
		}
		
		// Get the squares we're exiting and entering.
		Square originSquare = actorPiece.getCurrentSquare(game.getMap());
		Vector2D destPos = actorPiece.getPosition().plus(getDirection().getUnitVector());
		Square destSquare = game.getMap().getSquare(destPos);
		
		// Return false if we're trying to move off the map. Should never happen because
		// we assume MoveActions are only created for valid moves.
		if (destSquare == null || originSquare == null)
			return;
		
		// Tell the player we are moving.
		game.getPlayer(getPlayer()).notifyPieceMove(getActor(), getDirection(), originSquare, destSquare);
		
		// Do the move.
		originSquare.setOccupant(0);
		destSquare.setOccupant(getActor());
		actorPiece.setPosition(destPos);
		actorPiece.setMovesThisTurn(actorPiece.getMovesThisTurn() + destSquare.getMoveToll());
		game.getKnowledgeHandler().notifyPieceMoved(actorPiece);
	}

	@Override
	public String getFriendlyName() {
		return "Move " + getDirection();
	}
}
