
public class Square {

	private Piece occupant;
	private boolean isPlayable;
	
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
	
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //

}
