import java.awt.Point;


public class Piece {

	private final FieldOfView game;
	private int owner;
	private Point position;
	
	private int maxSquares;
	private boolean isGhost;
	
	public Piece(FieldOfView fovGame, int ownerNumber, Point startPosition) {
		game = fovGame;
		owner = ownerNumber;
		position = startPosition;
		setMaxSquares(0);
	}

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

	/**
	 * True if this piece can walk through enemy pieces (and vice versa).
	 * @return True if this piece is a ghost; false otherwise
	 */
	public boolean isGhost() {
		return isGhost;
	}

	/**
	 * Set whether this piece is a ghost.
	 * @param isGhost True for ghost; false otherwise
	 */
	public void setGhost(boolean isGhost) {
		this.isGhost = isGhost;
	}

}
