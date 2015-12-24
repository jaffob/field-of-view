import java.io.IOException;

/**
 * Field Of View Game
 * @author Jack Boffa
 *
 * This class represents one game of Field of View. It is not supposed
 * to include the mechanisms by which games are started (like the main
 * menu). I'm making it single-player by default, but maybe I'll make
 * it fancy and extendable so you can do multiplayer too. Who knows!
 */
public class FieldOfView {

	private final Player[] players;			// Array of all players in the game.
	private final Map map;					// Object representing the map.
	private final Drawer drawer;			// Drawing interface.
	
	private int turn;						// Whose turn it is.
	private int turnCount;					// Total number of turns taken this game.
	private SquareFactory squareFactory;

	/**
	 * Constructor: initialize gameplay.
	 * @throws FileNotFoundException if the given map file doesn't exist.
	 * @throws InvalidMapException if the map was deemed invalid.
	 */
	public FieldOfView(String mapFileName, Controller[] controllers, Drawer drawer) throws IOException, InvalidMapException {
		map = new Map(this, mapFileName);
		this.drawer = drawer;
		players = new Player[2];
		players[0] = new Player(this, 1, controllers[0]);
		players[1] = new Player(this, 2, controllers[1]);
		setSquareFactory(new SquareFactory());
		turn = 1;
	}
	
	/**
	 * Starts the gameplay loop. This functions as a loop that makes
	 * each player alternate turns until someone wins.
	 * @return The number of the player that won (1 or 2).
	 */
	public int play() {
		// Loop indefinitely until checkWin() is nonzero.
		int winner = 0;
		while ((winner = checkWin()) == 0) {
			getCurrentPlayer().takeTurn();
			nextTurn();
			incrementTurnCount();
		}
		
		return winner;
	}

	public int checkWin() {
		if (getPlayer(1).hasWon()) return 1;
		if (getPlayer(2).hasWon()) return 2;
		return 0;
	}

	/**
	 * Gets a player object. Players should be referred to as 1 and 2,
	 * so it's good to use this instead of the actual array, because
	 * the array is zero-based.
	 * @param playerNum The player to get (generally 1 or 2)
	 * @return The Player object for that player
	 */
	public Player getPlayer(int playerNum) {
		return players[playerNum - 1];
	}
	
	/**
	 * Gets the number of players in the game (so, two).
	 * @return The number of players
	 */
	public int getNumberOfPlayers() {
		return players.length;
	}
	
	/**
	 * Gets the player whose turn it is.
	 * @return The player object whose turn it is.
	 */
	public Player getCurrentPlayer() {
		return getPlayer(getTurn());
	}
	
	public Map getMap() {
		return map;
	}
	
	public Drawer getDrawer() {
		return drawer;
	}

	/**
	 * Gets the number of turns taken this game. This includes both
	 * players' turns, so divide by two if you want to get the number
	 * of full turns.
	 * @return The number of turns taken
	 */
	public int getTurnCount() {
		return turnCount;
	}
	
	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}
	
	/**
	 * Adds one to the turn count.
	 */
	public void incrementTurnCount() {
		turnCount++;
	}

	/**
	 * @return The number of the player whose turn it is.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Set whose turn it is. This is private because it should never be
	 * modified outside of the play() method.
	 * @param turn Whose turn it is next.
	 */
	protected void setTurn(int turn) {
		this.turn = turn;
	}
	
	/**
	 * Set the turn variable to that of the next player (resetting to 1
	 * if it's player 2's turn).
	 */
	protected void nextTurn() {
		setTurn((turn % getNumberOfPlayers()) + 1);
	}

	public SquareFactory getSquareFactory() {
		return squareFactory;
	}

	protected void setSquareFactory(SquareFactory squareFactory) {
		this.squareFactory = squareFactory;
	}
}
