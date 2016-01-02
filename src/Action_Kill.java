import java.util.ArrayList;


public abstract class Action_Kill extends Action {

	private final ArrayList<Integer> targets;
	
	public Action_Kill(int player, int actor) {
		super(player, actor);
		targets = new ArrayList<Integer>();
	}

	public ArrayList<Integer> getTargets() {
		return targets;
	}
	
	public void addTarget(int pieceId) {
		targets.add(pieceId);
	}
	
	@Override
	public boolean doAction(FieldOfView game) {
		
		if (!hasTargets()) {
			return false;
		}
		
		addActionPositionAtActor(game);
		
		int selectedTarget = getTargets().get(getPlayer(game).selectPiece(getTargets()));
		game.killPiece(selectedTarget, false);
		
		return true;
	}
	
	@Override
	public void sendToKnowledgeHandler(FieldOfView game) {
		// Clear targets array before sending this action to the knowledge handler.
		getTargets().clear();
		super.sendToKnowledgeHandler(game);
	}
	
	public boolean hasTargets() {
		return !targets.isEmpty();
	}
}
