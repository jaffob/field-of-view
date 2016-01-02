import java.util.ArrayList;


public class Action_Gate extends Action {

	public Action_Gate(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doAction(FieldOfView game) {
		addActionPositionAtActor(game);
		
		Piece actorPiece = getActorPiece(game);
		ArrayList<Vector2D> gatePositions = new ArrayList<Vector2D>();
		
		for (Vector2D pos : actorPiece.getAdjacentPositions(true)) {
			if (game.getMap().getSquare(pos) instanceof Square_Gate) {
				gatePositions.add(pos);
			}
		}
		
		if (gatePositions.isEmpty()) {
			return false;
		}
		Vector2D selectedPosition = gatePositions.get(getPlayer(game).selectSquare(gatePositions));
		Square_Gate gate = (Square_Gate)game.getMap().getSquare(selectedPosition);
		gate.openCloseGate(game, !gate.isOpen());
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "Toggle Gate";
	}

}
