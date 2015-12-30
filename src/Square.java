
public abstract class Square {

	private final Vector2D position;
	
	private int occupant;
	private boolean isWalkable, isTransparent;
	private int side, moveToll;
	
	
	// ---------------------------------- //
	// --------- Initialization --------- //
	// ---------------------------------- //
	
	public Square(Vector2D position, Integer properties) {	// Public square. Ha ha.
		this.position = position;
		occupant = 0;
		isWalkable = true;
		isTransparent = true;
		moveToll = 0;
		
		absorbPropertyVal(properties);
	}
	
	/**
	 * Set up this square instance from the char read in from the file.
	 * @param properties Byte of property data
	 */
	protected void absorbPropertyVal(int properties) {
		// First 2 bits represent the side.
		setSide(properties & 0b11);
	}
	
	public int createPropertyVal() {
		return side;
	}
	
	public ClientSquare createClientSquare(boolean isKnown) {
		ClientSquare cs = new ClientSquare(getClass(), position, side, isKnown);
		cs.setStateVar("isTransparent", isTransparent() ? 1 : 0);
		cs.setStateVar("isWalkable", isWalkable() ? 1 : 0);
		cs.setStateVar("moveToll", getMoveToll());
		return cs;
	}
	
	// ---------------------------------- //
	// -------- Abstract Methods -------- //
	// ---------------------------------- //
	
	public abstract Transparency getTransparencyGuarantee();
	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //

	public Vector2D getPosition() {
		return position;
	}

	public int getOccupant() {
		return occupant;
	}

	public void setOccupant(int occupant) {
		this.occupant = occupant;
	}
	
	public boolean isOccupied() {
		return getOccupant() > 0;
	}

	/**
	 * Whether this square is walkable. Walkable squares, such as open
	 * squares and all special squares, can be occupied by a piece.
	 * Blocking (non-walkable) squares such as walls and closed gates
	 * cannot contain a piece.
	 * @return
	 */
	public boolean isWalkable() {
		return isWalkable;
	}

	/**
	 * Sets whether this square is walkable. If setting this to false,
	 * first make sure to check if any pieces are occupying this square,
	 * and if so, to destroy them.
	 * @param isWalkable Whether this square should be walkable
	 */
	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
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


	protected void setSide(int side) {
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

	public ActionList getActions(FieldOfView game, Piece piece) {
		ActionList actions = new ActionList();
		
		if (piece.canUseSpecialSquares()) {
			actions.addList(getSpecialActions(game));
		}
		
		if (piece.isGoalPiece()) {
			actions.addList(getGoalActions(game));
		}
		
		return actions;
	}
	/**
	 * Gets the set of special actions this square can perform. These
	 * actions can generally only be performed by the king.
	 * @return An ActionSet of special actions
	 */
	public ActionList getSpecialActions(FieldOfView game) {
		return new ActionList();
	}
	
	/**
	 * Gets the set of actions a goal piece can perform here. Winning
	 * is the primary use case here.
	 * @return An ActionSet of goal actions
	 */
	public ActionList getGoalActions(FieldOfView game) {
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
		return isWalkable() && !isOccupied();
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
