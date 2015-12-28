
public class PlayAction_Suicide extends PlayAction {

	public PlayAction_Suicide(Player player, Piece actor) {
		super(player, actor, "Suicide");
		setKillsActor(true);
	}
	
	public PlayAction_Suicide(Piece actor) {
		this(actor.getOwnerPlayer(), actor);
	}
	

	public void doAction(FieldOfView game) {
		getActor().kill(true);
	}

	@Override
	public ClientAction createClientAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
