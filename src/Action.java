/**
 * Action
 * @author Jack Boffa
 * Defines the abstract concept of an action a player can perform as part
 * of his/her turn. On a turn, the player gets a fixed number of chances
 * to "move" or "play", and Actions can represent both of these operations.
 * It is not guaranteed that an action will succeed (e.g. movement might be
 * blocked), so they should be used with that in mind.
 */
public abstract class Action {

	private final Player player;
	private final Piece actor;
	private final boolean endsTurn;
	
	public Action(Player player, Piece actor, boolean endsTurn) {
		this.player = player;
		this.actor = actor;
		this.endsTurn = endsTurn;
	}
	
	public Action(Piece actor, boolean endsTurn) {
		this(actor.getOwnerPlayer(), actor, endsTurn);
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

	/**
	 * Performs this action.
	 * @return Whether the action succeeded.
	 */
	public abstract boolean doAction();

}
