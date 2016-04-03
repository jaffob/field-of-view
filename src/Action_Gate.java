
public class Action_Gate extends Action_Square {

	public Action_Gate(int player, int actor) {
		super(player, actor);
		setEndsTurn(false);
	}

	@Override
	public String getFriendlyName() {
		return "Toggle Gate";
	}

	@Override
	public boolean doActionOnSquare(FieldOfView game, Vector2D squarePos) {
		addActionPositionAtActor(game);
		Square_Gate gate = (Square_Gate)game.getMap().getSquare(squarePos);
		gate.openCloseGate(game, !gate.isOpen());
		return false;
	}

}
