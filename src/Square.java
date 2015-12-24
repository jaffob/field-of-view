
public class Square {

	public static final String NAME = "";			// The user-friendly name of this type of square
	public static final int TRANSPARENCY_TYPE = 0;	// 0 = never transparent, 1 = sometimes transparent (e.g. gate), 2 = always transparent
	
	private Piece occupant;
	private boolean isPlayable, isTransparent;
	private int moveToll;
	
	public Square() {
		setOccupant(null);
		setPlayable(false);
		setTransparent(false);
		setMoveToll(1);
	}

	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	public Piece getOccupant() {
		return occupant;
	}

	public void setOccupant(Piece occupant) {
		this.occupant = occupant;
	}
	
	public boolean isOccupied() {
		return getOccupant() != null;
	}

	/**
	 * Whether this square is playable. Playable squares can be occupied
	 * and don't obstruct the player's view. Examples of non-playable
	 * squares are walls and closed gates.
	 * @return
	 */
	public boolean isPlayable() {
		return isPlayable;
	}

	public void setPlayable(boolean isPlayable) {
		this.isPlayable = isPlayable;
		
		// If this square is set to non-playable, kill any piece that occupies it.
		if (!isPlayable && isOccupied()) {
			getOccupant().kill(true);
		}
	}
	
	public boolean isTransparent() {
		return isTransparent;
	}


	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}


	public int getMoveToll() {
		return moveToll;
	}


	public void setMoveToll(int moveToll) {
		this.moveToll = moveToll;
	}
	
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //

	public ActionSet getActions(Piece piece) {
		return new ActionSet();
	}
	/**
	 * Gets the set of special actions this square can perform. These
	 * actions can generally only be performed by the king.
	 * @return An ActionSet of special actions
	 */
	public ActionSet getSpecialActions() {
		return new ActionSet();
	}
	
	/**
	 * Gets the set of actions a goal piece can perform here. Winning
	 * is the primary use case here.
	 * @return An ActionSet of goal actions
	 */
	public ActionSet getGoalActions() {
		return new ActionSet();
	}

	/**
	 * Check if a piece is allowed to enter this square. This doesn't
	 * check if the piece is actually able to move to this square.
	 * Note: Subclasses should always call super.canEnter()
	 * @param piece The piece to test
	 * @param dir The direction the piece is moving to get here
	 * @return Whether the piece is allowed to enter
	 */
	public boolean canEnter(Piece piece, Direction dir) {
		return isPlayable() && !isOccupied();
	}
	
	/**
	 * Check if a piece is allowed to exit this square. This doesn't
	 * check if the piece is actually currently in this square.
	 * Note: Subclasses should always call super.canExit()
	 * @param piece The piece to test
	 * @param dir The direction the piece is going to leave
	 * @return Whether the piece is allowed to exit
	 */
	public boolean canExit(Piece piece, Direction dir) {
		return true;
	}
}
