
public class SuicidePlayAction extends PlayAction {

	public SuicidePlayAction(Piece actor) {
		super(actor, "Suicide");
		setKillsActor(true);
	}

	public boolean doAction() {
		getActor().kill(true);
		return true;
	}

}
