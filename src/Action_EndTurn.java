
public class Action_EndTurn extends Action {

	public Action_EndTurn(int player, int actor) {
		super(player, actor);
	}

	@Override
	public void doAction(FieldOfView game) {
		addActionPosition(game.getPlayer(getPlayer()).getPieceById(getActor()).getPosition());
	}

	@Override
	public String getFriendlyName() {
		return "End Turn";
	}

}
