
public class EndTurnPlayAction extends PlayAction {

	public EndTurnPlayAction(Piece actor) {
		super(actor, "End Turn");
	}

	@Override
	public boolean doAction() {
		return true;
	}

}
