
public class SuicidePlayAction extends PlayAction {

	public SuicidePlayAction(Piece actor) {
		super(actor, "Suicide");
		setKillsActor(true);
	}

	public void doAction() {
		getActor().kill(true);
	}

}
