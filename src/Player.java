import java.util.ArrayList;


public class Player {

	private final FieldOfView game;
	private final int number;
	private final Controller controller;
	private ArrayList<Piece> pieces;
	
	public Player(FieldOfView fovGame, int playerNum, Controller controller) {
		game = fovGame;
		number = playerNum;
		this.controller = controller;
	}

	/**
	 * Starting point for this player's entire turn. This method takes
	 * user input (i.e. what piece to move where, and what action to
	 * perform) and doesn't return until the turn is over.
	 */
	public void takeTurn() {
		Utils.log("Player " + number + " starts turn (turn #" + game.getTurnCount() + " of game)");
	}
	
	/**
	 * Determine if this player has won the game.
	 * @return True if the player won; false otherwise.
	 */
	public boolean hasWon() {
		return game.getTurnCount() == 20;
	}
	
	/**
	 * Gets this player's number (1 or 2).
	 * @return The player's number
	 */
	public int getNumber() {
		return number;
	}

	public Controller getController() {
		return controller;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	/**
	 * Called by a piece this player owns that is killed. This will be
	 * called even if the piece is a goal piece (and therefore will
	 * respawn), so use wasDestroyed to tell whether it should be
	 * removed from the piece list.
	 * @param piece The piece that got killed
	 * @param wasDestroyed Whether the piece is actually destroyed
	 */
	public void notifyPieceKilled(Piece piece, boolean wasDestroyed) {
		if (wasDestroyed) getPieces().remove(piece);
	}

	/**
	 * Called by a piece that is moving from originSquare to destSquare.
	 * This is where the player handles a piece moving after it tells
	 * the piece to move. This method will return false if the piece
	 * can't move (e.g. if the player is out of moves).
	 * @param piece The piece that is moving
	 * @param dir The direction the piece is moving in
	 * @param originSquare The square the piece is leaving
	 * @param destSquare The square the piece is entering
	 * @return Whether the move is permitted
	 */
	public boolean notifyPieceMove(Piece piece, Direction dir,
			Square originSquare, Square destSquare) {
		return true;
	}

}
