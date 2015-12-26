
public class PlayAction_EndTurn extends PlayAction {

	public PlayAction_EndTurn(Player player, Piece actor) {
		super(player, actor, "End Turn");
	}
	
	public PlayAction_EndTurn(Piece actor) {
		this(actor.getOwnerPlayer(), actor);
	}

	@Override
	public void doAction(FieldOfView game) {}

	@Override
	public ClientAction createClientAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyClientAction(ClientAction ca) {
		// TODO Auto-generated method stub
		return false;
	}

}
