import java.util.ArrayList;


public class Player {

	private final FieldOfView game;
	private final int number;
	private ArrayList<Piece> pieces;
	
	public Player(FieldOfView fovGame, int playerNum) {
		game = fovGame;
		number = playerNum;
	}

	/**
	 * Starting point for this player's entire turn. This method takes
	 * user input (i.e. what piece to move where, and what action to
	 * perform) and doesn't return until the turn is over.
	 */
	public void takeTurn() {
		System.out.println("Player " + number + " takes turn (turn #" + game.getTurnCount() + " of game)");
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
	public void notifyKilledPiece(Piece piece, boolean wasDestroyed) {
		if (wasDestroyed) getPieces().remove(piece);
	}

}
