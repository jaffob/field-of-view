
public class Action_EndTurn extends Action {

	public Action_EndTurn(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doAction(FieldOfView game) {
		addActionPositionAtActor(game);
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "End Turn";
	}

}
