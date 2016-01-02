
public class Action_Suicide extends Action {

	public Action_Suicide(int player, int actor) {
		super(player, actor);
	}
	
	public void doAction(FieldOfView game) {
		addActionPositionAtActor(game);
		game.killPiece(getPlayerNum(), getActor(), true);
	}

	@Override
	public String getFriendlyName() {
		return "Suicide";
	}
}
