
public class Square {

	private Piece occupant;
	private boolean isPlayable;
	private int moveToll;
	
	public Square() {
		setOccupant(null);
		setPlayable(false);
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
	
	public int getMoveToll() {
		return moveToll;
	}


	public void setMoveToll(int moveToll) {
		this.moveToll = moveToll;
	}
	
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //


	public ActionSet getSpecialActions(Piece piece) {
		return new ActionSet();
	}

	/**
	 * Check if a piece is allowed to enter this square. This doesn't
	 * check if the piece is actually able to move to this square.
	 * Note: Subclasses should always call super.canEnter()
	 * @param piece The piece to test
	 * @return Whether the piece is allowed to enter
	 */
	public boolean canEnter(Piece piece) {
		return isPlayable() && !isOccupied();
	}
	
	/**
	 * Check if a piece is allowed to exit this square. This doesn't
	 * check if the piece is actually currently in this square.
	 * Note: Subclasses should always call super.canExit()
	 * @param piece The piece to test
	 * @return Whether the piece is allowed to exit
	 */
	public boolean canExit(Piece piece) {
		return true;
	}
}
