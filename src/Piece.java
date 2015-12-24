
public class Piece {

	public static final String NAME = "";
	
	private final FieldOfView game;
	private int owner;
	
	private Vector2D position;
	
	// Attributes applicable to all pieces.
	private int maxSquares;
	private boolean isGoalPiece;
	private boolean isInvulnerable;
	private boolean canUseSpecialSquares;
	private int powerReq;
	
	// State of this piece.
	private int shieldLevel;
	
	public Piece(FieldOfView fovGame, int ownerNumber, Vector2D startPosition) {
		game = fovGame;
		owner = ownerNumber;
		setPosition(startPosition);
		
		setMaxSquares(0);
		setGoalPiece(false);
		setInvulnerable(false);
		setCanUseSpecialSquares(false);
		setPowerReq(0);
		setShieldLevel(0);
	}
	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	/**
	 * Get the number of the player that owns this piece.
	 * @return Player number of owner
	 */
	public int getOwnerNumber() {
		return this.owner;
	}

	/**
	 * Sets who owns this piece.
	 * @param ownerNumber New owner
	 */
	public void setOwnerNumber(int ownerNumber) {
		this.owner = ownerNumber;
	}
	
	/**
	 * Gets the actual Player object that owns this piece.
	 * @return The Player object of the owner
	 */
	public Player getOwnerPlayer() {
		return game.getPlayer(getOwnerNumber());
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Gets the Square object that this piece currently occupies.
	 * @return The Square this piece is standing on.
	 */
	public Square getCurrentSquare() {
		return game.getMap().getSquare(getPosition());
	}
	
	/**
	 * Gets a square adjacent to the player in a certain direction.
	 * @param dir The direction from the player of the square to get
	 * @return The Square object, or null if over edge of the map
	 */
	public Square getAdjacentSquare(Direction dir) {
		return null;
	}
	
	/**
	 * Gets the maximum number of squares this piece can move in a turn.
	 * @return Maximum squares
	 */
	public int getMaxSquares() {
		return maxSquares;
	}

	/**
	 * Set the maximum number of squares this piece can move in a turn.
	 * @param maxSquares New square limit
	 */
	public void setMaxSquares(int maxSquares) {
		this.maxSquares = maxSquares;
	}

	public boolean isGoalPiece() {
		return isGoalPiece;
	}

	public void setGoalPiece(boolean isGoalPiece) {
		this.isGoalPiece = isGoalPiece;
	}

	public boolean isInvulnerable() {
		return isInvulnerable;
	}

	public void setInvulnerable(boolean isInvulnerable) {
		this.isInvulnerable = isInvulnerable;
	}

	public int getPowerReq() {
		return powerReq;
	}

	public void setPowerReq(int powerReq) {
		this.powerReq = powerReq;
	}

	public boolean canUseSpecialSquares() {
		return canUseSpecialSquares;
	}

	public void setCanUseSpecialSquares(boolean canUseSpecialSquares) {
		this.canUseSpecialSquares = canUseSpecialSquares;
	}
	
	public int getShieldLevel() {
		return shieldLevel;
	}

	public void setShieldLevel(int shieldLevel) {
		this.shieldLevel = shieldLevel;
	}
	
	public void addShieldLevel() {
		shieldLevel++;
	}
	
	public void removeShieldLevel() {
		if (shieldLevel > 0) shieldLevel--;
	}
	
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //

	/**
	 * Attempt to move this piece one square in a given direction.
	 * Call this whenever the piece wants to move; it will check that
	 * the move is valid (although it won't check if it's the correct
	 * player's turn).
	 * @param dir The direction to move in
	 * @return Whether the move was successfully completed
	 */
	public boolean move(Direction dir) {
		
		// Get the squares we're exiting and entering.
		Square originSquare = getCurrentSquare();
		// TODO: replace with + operator
		Vector2D destPos = new Vector2D(getPosition().x + dir.getUnitVector().x, getPosition().y + dir.getUnitVector().y);
		Square destSquare = game.getMap().getSquare(destPos);
		
		// Return false if we're trying to move off the map.
		if (destSquare == null || originSquare == null)
			return false;
		
		// Make sure both squares are okay with this piece moving. Then ask the player.
		if (originSquare.canExit(this, dir)
				&& destSquare.canEnter(this, dir)
				&& getOwnerPlayer().notifyPieceMove(this, dir, originSquare, destSquare))
		{
			// The move is valid. 
			originSquare.setOccupant(null);
			destSquare.setOccupant(this);
			setPosition(destPos);
		}
		
		// The move was invalid.
		return false;
	}
	
	/**
	 * Gets the set of actions available from the current square.
	 * Doesn't check if it's actually this player's turn.
	 * @return An ActionSet of all the possible actions.
	 */
	public ActionSet getActions() {
		// return getCurrentSquare().getActions(this) + getCurrentSquareActions() + getAdjacentSquareActions();
		return new ActionSet();
	}
	
	/**
	 * Gets the set of actions this particular piece can perform
	 * in its current square. These are actions that are unique to
	 * this type of piece, excluding special and goal actions.
	 * @return ActionSet containing unique actions.
	 */
	public ActionSet getCurrentSquareActions() {
		return new ActionSet();
	}
	
	public ActionSet getAdjacentSquareActions() {
		return new ActionSet();
	}

	public void kill(boolean forceKill) {
		
		// The piece is killed.
		if (forceKill || shieldLevel == 0) {
			getCurrentSquare().setOccupant(null);
			getOwnerPlayer().notifyPieceKilled(this, !isInvulnerable());
			
			// If invulnerable, respawn this piece.
			if (isInvulnerable()) {
				// TODO: Respawn
			}
			
			// Otherwise, kill it for good.
			else
			{
				// TODO: destroy if you can do that
			}
		}
		
		// The piece was shielded; simply remove one shield level.
		else {
			removeShieldLevel();
		}
	}
	
}
