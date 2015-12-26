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
	 * Performs this action.
	 * @return Whether the action succeeded.
	 */
	public abstract void doAction(FieldOfView game);
	
	/**
	 * Generates and returns a ClientAction that represents this action.
	 * @return A new ClientAction
	 */
	public abstract ClientAction createClientAction();
	
	public abstract boolean verifyClientAction(ClientAction ca);

}
