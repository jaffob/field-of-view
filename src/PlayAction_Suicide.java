
public class PlayAction_Suicide extends PlayAction {

	public PlayAction_Suicide(Piece actor) {
		super(actor, "Suicide");
		setKillsActor(true);
	}

	public void doAction() {
		getActor().kill(true);
	}

}
