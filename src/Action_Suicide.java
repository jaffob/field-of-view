
public class Action_Suicide extends Action {

	public Action_Suicide(int player, int actor) {
		super(player, actor);
		setKillsActor(true);
	}
	
	public void doAction(FieldOfView game) {
		addActionPositionAtActor(game);
		game.getPlayer(getPlayer()).killPiece(game, getActor());
	}

	@Override
	public String getFriendlyName() {
		return "Suicide";
	}
}
