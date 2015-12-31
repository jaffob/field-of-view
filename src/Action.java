import java.util.ArrayList;

/**
 * Action
 * @author Jack Boffa
 * Defines the abstract concept of an action a player can perform as part
 * of his/her turn. On a turn, the player gets a fixed number of chances
 * to "move" or "play", and Actions can represent both of these operations.
 * It is guaranteed that an action will succeed, so they should only be
 * created and presented to the user when it is known they will work.
 */
public abstract class Action {

	private final int player;
	private final int actor;
	private boolean endsTurn;
	private boolean killsActor;
	private final ArrayList<Vector2D> actionPositions;
	
	public Action(int player, int actor) {
		this.player = player;
		this.actor = actor;
		setEndsTurn(true);
		setKillsActor(false);
		actionPositions = new ArrayList<Vector2D>();
	}
	
	public int getPlayer() {
		return player;
	}
	
	
	public int getActor() {
		return actor;
	}

	public boolean endsTurn() {
		return endsTurn;
	}

	protected void setEndsTurn(boolean endsTurn) {
		this.endsTurn = endsTurn;
	}
	
	public boolean killsActor() {
		return killsActor;
	}

	public void setKillsActor(boolean killsActor) {
		this.killsActor = killsActor;
	}

	public ArrayList<Vector2D> getActionPositions() {
		return actionPositions;
	}
	
	protected void addActionPosition(Vector2D pos) {
		actionPositions.add(pos);
	}
	
	// ---------------------------------- //
	// -------- Override Methods -------- //
	// ---------------------------------- //

	/**
	 * Performs this action on the game. This is where basically everything
	 * that actually happens in the game is done. Using the game object,
	 * this action can change the game at will, and assume that everything
	 * done will be sent to the KnowledgeHandler appropriately. If this
	 * action affects knowledge directly (e.g. surveillance), it can do
	 * that by accessing the KnowledgeHandler through the game object.
	 * @param game Reference to the game object
	 */
	public abstract void doAction(FieldOfView game);
	
	public void sendToKnowledgeHandler(FieldOfView game) {
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			if (playerWitnessedAction(game, i)) {
				game.getKnowledgeHandler().setTurnComponentAction(i, this);
			}
		}
	}
	
	protected boolean playerWitnessedAction(FieldOfView game, int playerNum) {
		if (playerNum == player) {
			return true;
		}
		
		for (Vector2D pos : getActionPositions()) {
			if (game.getKnowledgeHandler().isSquareKnown(playerNum, pos)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the name that this action should display to the end user.
	 * @return The name.
	 */
	public abstract String getFriendlyName();

}
