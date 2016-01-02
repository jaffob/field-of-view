import java.util.ArrayList;


public class Piece {

	// Basic stuff.
	private final int owner;
	
	// Handle giving IDs to pieces.
	private static int nextPieceId = 1;
	private final int id;
	
	// Attributes applicable to all pieces.
	private int maxMoves;
	private boolean isGoalPiece;
	private boolean isInvulnerable;
	private boolean canUseSpecialSquares;
	
	// State of this piece.
	private Vector2D position;
	private boolean isSelected;
	private int movesThisTurn;
	private int shieldLevel;
	
	public Piece(Integer ownerNumber, Vector2D startPosition) {
		owner = ownerNumber;
		
		id = Piece.generateNextId();
		
		setPosition(startPosition);
		setMovesThisTurn(0);
		setSelected(false);

		setMaxMoves(0);
		setGoalPiece(false);
		setInvulnerable(false);
		setCanUseSpecialSquares(false);
		setShieldLevel(0);
	}
	
	/**
	 * Gets the ID that should be assigned to a newly created piece.
	 * This works by maintaining a static "next ID" field that is
	 * returned then incremented every time this is called.
	 * @return
	 */
	private static int generateNextId() {
		return nextPieceId++;
	}

	/**
	 * Creates a ClientPiece based on this piece. This method lacks
	 * an isKnown parameter because clients aren't even aware a piece
	 * exists unless it can be seen. However, some qualities of pieces
	 * should be hidden from other players (such as number of remaining
	 * cameras for a scout). Therefore, this method should be passed
	 * the player who will be sent this ClientPiece, so the appropriate
	 * information can be hidden.
	 * @param forPlayer The player this ClientPiece will be sent to
	 * @return A ClientPiece representing this piece.
	 */
	public ClientPiece createClientPiece(int forPlayer) {
		ClientPiece cp = new ClientPiece(getClass(), getId(), getOwner(), getPosition());
		cp.setStateVar("maxMoves", getMaxMoves());
		cp.setStateVar("isGoalPiece", isGoalPiece() ? 1 : 0);
		cp.setStateVar("isInvulnerable", isInvulnerable() ? 1 : 0);
		cp.setStateVar("canUseSpecialSquares", canUseSpecialSquares() ? 1 : 0);
		cp.setStateVar("shieldLevel", getShieldLevel());
		return cp;
	}

	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	/**
	 * Get the number of the player that owns this piece.
	 * @return Player number of owner
	 */
	public int getOwner() {
		return this.owner;
	}
	
	/**
	 * Gets the actual Player object that owns this piece.
	 * @return The Player object of the owner
	 */
	public Player getOwnerPlayer(FieldOfView game) {
		return game.getPlayer(getOwner());
	}

	public int getId() {
		return id;
	}

	public Vector2D getPosition() {
		return position;
	}

	public ArrayList<Vector2D> getAdjacentPositions() {
		ArrayList<Vector2D> coords = new ArrayList<Vector2D>();
		for (Direction dir : Direction.values()) {
			coords.add(getPosition().plus(dir.getUnitVector()));
		}
		return coords;
	}
	
	public ArrayList<Vector2D> getAdjacentPositions(boolean includeCurrentPos) {
		ArrayList<Vector2D> coords = new ArrayList<Vector2D>();
		if (includeCurrentPos) {
			coords.add(getPosition());
		}
		for (Direction dir : Direction.values()) {
			coords.add(getPosition().plus(dir.getUnitVector()));
		}
		return coords;
	}
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public int getMovesThisTurn() {
		return movesThisTurn;
	}

	public void setMovesThisTurn(int movesThisTurn) {
		this.movesThisTurn = movesThisTurn;
	}

	public boolean isSelected() {
		return isSelected;
	}

	protected void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	/**
	 * Gets the Square object that this piece currently occupies.
	 * @return The Square this piece is standing on.
	 */
	public Square getCurrentSquare(Map map) {
		return map.getSquare(getPosition());
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
	public int getMaxMoves() {
		return maxMoves;
	}

	/**
	 * Set the maximum number of squares this piece can move in a turn.
	 * @param maxSquares New square limit
	 */
	public void setMaxMoves(int maxMoves) {
		this.maxMoves = maxMoves;
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
		setShieldLevel(shieldLevel + 1);
	}
	
	public void removeShieldLevel() {
		if (shieldLevel > 0) setShieldLevel(shieldLevel - 1);
	}
	
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //

	public boolean allowSelect(FieldOfView game) {
		return getCurrentSquare(game.getMap()).allowPieceSelect(game, this);
	}
	
	public boolean allowSuicide(FieldOfView game) {
		return getCurrentSquare(game.getMap()).allowPieceSuicide(game, this);
	}
	
	public boolean allowEndTurn(FieldOfView game) {
		return getCurrentSquare(game.getMap()).allowPieceEndTurn(game, this);
	}
	
	public void select() {
		setSelected(true);
		setMovesThisTurn(0);
	}
	
	public void deselect() {
		setSelected(false);
	}
	
	/**
	 * Gets the set of actions available from the current square.
	 * Doesn't check if it's actually this player's turn.
	 * @return An ActionSet of all the possible actions.
	 */
	public ActionList getActions(FieldOfView game) {
		
		ActionList actions = new ActionList();
		
		// Add move actions.
		if (getCurrentSquare(game.getMap()).allowPieceMoveActions(game, this)) {
			actions.addList(getMoveActions(game));
		}
		
		// Add actions that are unique to this piece.
		if (getCurrentSquare(game.getMap()).allowPieceUniqueActions(game, this)) {
			actions.addList(getUniqueActions(game));
		}
		
		// Add actions specific to the square we're standing in.
		actions.addList(getCurrentSquare(game.getMap()).getActions(game, this));
		
		// Add suicide action.
		if (allowSuicide(game)) {
			actions.addAction(new Action_Suicide(getOwner(), getId()));
		}
		
		// Add end turn action.
		if (allowEndTurn(game)) {
			actions.addAction(new Action_EndTurn(getOwner(), getId()));
		}
		
		return actions;
	}
	
	/**
	 * 
	 * @return
	 */
	public ActionList getMoveActions(FieldOfView game) {
		ActionList actions = new ActionList();
		
		// If this piece has no moves left, stop here.
		if (getMovesThisTurn() >= getMaxMoves()) {
			return actions;
		}
		
		// Create a MoveAction for every accessible direction.
		for (Direction dir : Direction.values()) {
			Square destSquare =  game.getMap().getSquare(getPosition().plus(dir.getUnitVector()));
			if (getCurrentSquare(game.getMap()).canExit(this, dir)
					&& destSquare != null
					&& destSquare.canEnter(this, dir)
					&& getOwnerPlayer(game).pieceCanMove(this, dir, getCurrentSquare(game.getMap()), destSquare)) {
				actions.addAction(new Action_Move(getOwner(), getId(), dir));
			}
		}
		
		return actions;
	}
	
	/**
	 * Gets the set of actions this particular piece can perform
	 * in its current environment. These are actions that are unique to
	 * this type of piece (e.g. placing a camera), and it excludes
	 * special and goal actions (those are handled by the Square).
	 * @return ActionSet containing unique actions.
	 */
	public ActionList getUniqueActions(FieldOfView game) {
		return new ActionList();
	}

	/**
	 * Inflicts a point of damage upon this piece. If the piece was shielded,
	 * this method will remove a shield level. Returns whether the player
	 * should be destroyed or not (so it will return false if the player was
	 * shielded). This can also be used to provide invulnerability by always
	 * returning false.
	 * @return Whether the player is destroyed from the damage
	 */
	public boolean inflictDamage() {
		return shieldLevel-- == 0;
	}
	
}
