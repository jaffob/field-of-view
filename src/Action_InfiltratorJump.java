
public class Action_InfiltratorJump extends Action_Square {

	public Action_InfiltratorJump(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doActionOnSquare(FieldOfView game, Vector2D squarePos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFriendlyName() {
		return "Jump";
	}

}
