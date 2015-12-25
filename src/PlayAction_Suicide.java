
public class PlayAction_Suicide extends PlayAction {

	public PlayAction_Suicide(FieldOfView fovGame, Piece actor) {
		super(fovGame, actor, "Suicide");
		setKillsActor(true);
	}

	public void doAction() {
		getActor().kill(true);
	}

}
