
public abstract class Square {
	
	protected final FieldOfView game;
	private String friendlyName;
	
	private Piece occupant;
	private boolean isOpen, isTransparent;
	private int side, moveToll;
	
	public Square(FieldOfView fovGame, char properties) {	// Public square. Ha ha.
		game = fovGame;
		setFriendlyName("");
		setOccupant(null);
		setOpen(true);
		setTransparent(true);
		setMoveToll(1);
		
		initializeProperties(properties);
	}
	
	/**
	 * Set up this square instance from the char read in from the file.
	 * @param properties Byte of property data
	 */
	protected void initializeProperties(char properties) {
		// First 2 bits represent the side.
		int props = (int)properties;
		setSide(props & 0b11);
		props >>= 2;
	}
	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	public String getFriendlyName() {
		return friendlyName;
	}

	protected void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

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
	 * Whether this square is open. Open squares can be occupied
	 * and don't obstruct the player's view. Examples of non-playable
	 * squares are walls and closed gates.
	 * @return
	 */
	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
		
		// If this square is set to non-playable, kill any piece that occupies it.
		if (!isOpen && isOccupied()) {
			getOccupant().kill(true);
		}
	}
	
	public boolean isTransparent() {
		return isTransparent;
	}


	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}


	public int getSide() {
		return side;
	}


	public void setSide(int side) {
		this.side = side;
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

	public ActionList getActions(Piece piece) {
		ActionList actions = new ActionList();
		
		if (piece.canUseSpecialSquares()) {
			actions.addList(getSpecialActions());
		}
		
		if (piece.isGoalPiece()) {
			actions.addList(getGoalActions());
		}
		
		return actions;
	}
	/**
	 * Gets the set of special actions this square can perform. These
	 * actions can generally only be performed by the king.
	 * @return An ActionSet of special actions
	 */
	public ActionList getSpecialActions() {
		return new ActionList();
	}
	
	/**
	 * Gets the set of actions a goal piece can perform here. Winning
	 * is the primary use case here.
	 * @return An ActionSet of goal actions
	 */
	public ActionList getGoalActions() {
		return new ActionList();
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
		return isOpen() && !isOccupied();
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
	
	/**
	 * Check if a particular piece is able to see through this square.
	 * This can be used by subclasses to (for example) create squares
	 * that are only transparent to one player. Otherwise, it's the
	 * same is isTransparent().
	 * @param piece
	 * @return
	 */
	public boolean canSeeThrough(Piece piece) {
		return isTransparent();
	}
}
