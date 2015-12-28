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

	private final Player player;
	private final Piece actor;
	private boolean endsTurn;
	
	public Action(Player player, Piece actor) {
		this.player = player;
		this.actor = actor;
		setEndsTurn(false);
	}
	
	public Action(Piece actor) {
		this(actor.getOwnerPlayer(), actor);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	
	public Piece getActor() {
		return actor;
	}

	public boolean endsTurn() {
		return endsTurn;
	}

	protected void setEndsTurn(boolean endsTurn) {
		this.endsTurn = endsTurn;
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
	
	/**
	 * Generates and returns a ClientAction that represents this action.
	 * This object returned should be whatever ClientAction subclass
	 * corresponds to this Action subclass.
	 * @return A new ClientAction
	 */
	public abstract ClientAction createClientAction();
	

}
