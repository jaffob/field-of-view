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
	
	public Action(int player, int actor) {
		this.player = player;
		this.actor = actor;
		setEndsTurn(true);
		setKillsActor(false);
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

	// ---------------------------------- //
	// -------- Abstract Methods -------- //
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
	
	public abstract String getFriendlyName();
	/**
	 * Generates and returns a ClientAction that represents this action.
	 * This object returned should be whatever ClientAction subclass
	 * corresponds to this Action subclass.
	 * @return A new ClientAction
	 */
	// public abstract ClientAction createClientAction();

}
