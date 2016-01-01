
public class Piece {

	// Basic stuff.
	private final int owner;
	
	// Handle giving IDs to pieces.
	private static int nextPieceId = 1;
	private final int id;
	
	// Attributes applicable to all pieces.
	private int maxMoves;
	private Class<Square> spawnSquareClass;
	private boolean isGoalPiece;
	private boolean isInvulnerable;
	private boolean canUseSpecialSquares;
	private boolean canSuicide;
	private boolean allowEndTurn;
	private int powerReq;
	
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
		
		setSpawnSquareClass(null);
		setMaxMoves(0);
		setGoalPiece(false);
		setInvulnerable(false);
		setCanUseSpecialSquares(false);
		setCanSuicide(true);
		setAllowEndTurn(true);
		setPowerReq(0);
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
		cp.setStateVar("canSuicide", canSuicide() ? 1 : 0);
		cp.setStateVar("powerReq", getPowerReq());
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

	public Class<Square> getSpawnSquareClass() {
		return spawnSquareClass;
	}

	public void setSpawnSquareClass(Class<Square> spawnSquareClass) {
		this.spawnSquareClass = spawnSquareClass;
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
	
	public boolean canSuicide() {
		return canSuicide;
	}

	public void setCanSuicide(boolean canSuicide) {
		this.canSuicide = canSuicide;
	}

	public boolean allowEndTurn() {
		return allowEndTurn;
	}

	public void setAllowEndTurn(boolean allowEndTurn) {
		this.allowEndTurn = allowEndTurn;
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

	public boolean allowSelect() {
		return true;
	}
	
	public void select() {
		setSelected(true);
		setMovesThisTurn(0);
	}
	
	public void deselect() {
		setSelected(false);
	}
	
	/**
	 * Attempt to move this piece one square in a given direction.
	 * Call this whenever the piece wants to move; it will check that
	 * the move is valid (although it won't check if it's the correct
	 * player's turn).
	 * @param dir The direction to move in
	 * @return Whether the move was successfully completed
	 */
	/*public void move(Direction dir) {
		
		// Get the squares we're exiting and entering.
		Square originSquare = getCurrentSquare();
		Vector2D destPos = getPosition().plus(dir.getUnitVector());
		Square destSquare = game.getMap().getSquare(destPos);
		
		// Return false if we're trying to move off the map. Should never happen because
		// we assume MoveActions are only created for valid moves.
		if (destSquare == null || originSquare == null)
			return;
		
		// Tell the player we are moving.
		getOwnerPlayer().notifyPieceMove(this, dir, originSquare, destSquare);
		
		// Do the move.
		originSquare.setOccupant(null);
		destSquare.setOccupant(this);
		setPosition(destPos);
		setMovesThisTurn(getMovesThisTurn() + destSquare.getMoveToll());
	}*/
	
	/**
	 * Gets the set of actions available from the current square.
	 * Doesn't check if it's actually this player's turn.
	 * @return An ActionSet of all the possible actions.
	 */
	public ActionList getActions(FieldOfView game) {
		
		ActionList actions = new ActionList();
		
		actions.addList(getMoveActions(game));
		actions.addList(getCurrentSquare(game.getMap()).getActions(game, this));
		actions.addList(getUniqueActions(game));
		
		if (canSuicide()) {
			actions.addAction(new Action_Suicide(getOwner(), getId()));
		}
		
		if (allowEndTurn()) {
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

	/*public void kill(boolean forceKill) {
		
		// The piece is killed.
		if (forceKill || shieldLevel == 0) {
			getCurrentSquare().setOccupant(null);
			
			// If invulnerable, respawn this piece.
			if (isInvulnerable()) {
				// TODO: Respawn. Do this by having a spawn queue that gets flushed to the controller at the beginning of its turn, so if you have two king spawns you can choose one.
			}
			else {
				game.getKnowledgeHandler().notifyPieceDestroyed(this);
				getOwnerPlayer().notifyPieceKilled(this);
			}
			
		}
		
		// The piece was shielded; simply remove one shield level.
		else {
			removeShieldLevel();
		}
	}*/
	
	public boolean inflictDamage() {
		return shieldLevel-- == 0;
	}
	
}
