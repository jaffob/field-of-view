
public class Action_Suicide extends Action {

	public Action_Suicide(int player, int actor) {
		super(player, actor);
	}
	
	public boolean doAction(FieldOfView game) {
		addActionPositionAtActor(game);
		game.killPiece(getPlayerNum(), getActor(), true);
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "Suicide";
	}
}
