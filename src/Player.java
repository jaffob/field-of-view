import java.util.ArrayList;


public class Player {

	private final FieldOfView game;
	private final int number;
	ArrayList<Piece> pieces;
	
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

}
