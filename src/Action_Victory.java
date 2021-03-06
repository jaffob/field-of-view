
public class Action_Victory extends Action {

	private final int losingPlayer;
	
	public Action_Victory(int player, int actor, int losingPlayer) {
		super(player, actor);
		this.losingPlayer = losingPlayer;
	}

	public int getLosingPlayer() {
		return losingPlayer;
	}

	@Override
	public boolean doAction(FieldOfView game) {
		game.getPlayer(getLosingPlayer()).setVictoryFlag(-1);
		addActionPositionAtActor(game);
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "Destroy Headquarters";
	}
	

}
