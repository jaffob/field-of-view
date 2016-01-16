
public class Action_Gate extends Action_Square {

	public Action_Gate(int player, int actor) {
		super(player, actor);
	}

	@Override
	public String getFriendlyName() {
		return "Toggle Gate";
	}

	@Override
	public boolean doActionOnSquare(FieldOfView game, Vector2D squarePos) {
		Square_Gate gate = (Square_Gate)game.getMap().getSquare(squarePos);
		gate.openCloseGate(game, !gate.isOpen());
		return false;
	}

}
