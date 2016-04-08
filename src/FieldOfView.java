import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
	private final HashMap<String, Integer> gameStateVars;
	private final KnowledgeHandler knowledgeHandler;
	
	private int turn;						// Whose turn it is.
	private int turnCount;					// Total number of turns taken this game.

	/**
	 * Constructor: initialize gameplay.
	 * @throws FileNotFoundException if the given map file doesn't exist.
	 * @throws InvalidMapException if the map was deemed invalid.
	 */
	public FieldOfView(String mapFileName, Controller[] controllers) throws IOException, InvalidMapException {
		map = new Map(mapFileName);
		gameStateVars = new HashMap<String, Integer>();
		players = new Player[2];
		players[0] = new Player(this, 1, controllers[0]);
		players[1] = new Player(this, 2, controllers[1]);
		initializeGameStateVars();
		knowledgeHandler = new KnowledgeHandler(this);	
		turn = 1;
	}
	
	protected void initializeGameStateVars() {
		setGameStateVar("generator", 0);
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
			knowledgeHandler.notifyStartTurn(turn, turnCount);
			getCurrentPlayer().takeTurn(this, turnCount);
			knowledgeHandler.notifyEndTurn(turn, turnCount);
			nextTurn();
			incrementTurnCount();
		}
		
		return winner;
	}

	public int checkWin() {
		// TODO: update for >2 players; add controller updates
		if (getPlayer(1).getVictoryFlag() == 1 || getPlayer(2).getVictoryFlag() == -1) return 1;
		if (getPlayer(2).getVictoryFlag() == 1 || getPlayer(1).getVictoryFlag() == -1) return 2;
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
	
	public KnowledgeHandler getKnowledgeHandler() {
		return knowledgeHandler;
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
	
	public Integer getGameStateVar(String varName) {
		return gameStateVars.get(varName);
	}
	
	public void setGameStateVar(String varName, int newValue) {
		gameStateVars.put(varName, newValue);
	}
	
	public HashMap<String, Integer> getGameStateVars() {
		return gameStateVars;
	}

	public Piece getPieceById(int pieceId) {
		for (int i = 1; i <= getNumberOfPlayers(); i++) {
			Piece p = getPlayer(i).getPieceById(pieceId);
			if (p != null) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * Spawns a piece for a certain player at the specified position. Note
	 * that this function will destroy any piece that occupies the spawn
	 * square before spawning the new piece.
	 * @param type The class of piece to spawn
	 * @param playerNum The number of the player who will own this piece
	 * @param spawnPos The position to spawn the piece at
	 * @param spawnProtected Whether to provide this piece spawn protection
	 * @return Whether the piece spawned successfully.
	 */
	public boolean spawnPiece(Class<? extends Piece> type, int playerNum, Vector2D spawnPos, boolean spawnProtected) {
		
		Piece newPiece;
		Square sq = getMap().getSquare(spawnPos);
		
		// If this square is off the grid or isn't walkable, no way to do the spawn.
		if (!getMap().positionIsInBounds(spawnPos) || !sq.isWalkable()) {
			return false;
		}
		
		// Destroy any occupying pieces.
		if (sq.isOccupied()) {
			killPiece(sq.getOccupant(), true);
		}
		
		// Try to create the new Piece object.
		try {
			newPiece = type.getConstructor(Integer.class, Vector2D.class).newInstance(playerNum, spawnPos);
		} catch (Exception e) {
			return false;
		}
		
		// Do everything needed to spawn the piece.
		newPiece.setSpawnProtected(spawnProtected);
		getPlayer(playerNum).addPiece(newPiece);
		getMap().getSquare(spawnPos).setOccupant(newPiece.getId());
		getKnowledgeHandler().notifyPieceCreated(newPiece);
		return true;
	}
	
	/**
	 * Kill a piece. If the piece is shielded, this will remove a shield level.
	 * If the piece is supposed to respawn, this method will respawn it. This
	 * removes the piece by setting the piece's square as vacant, removing the
	 * piece from its owner's piece list, and telling the knowledge handler that
	 * the piece was destroyed.
	 * @param pieceId The ID of the piece to kill
	 * @param forceKill True if this piece must be killed
	 * @return Whether the piece was fully destroyed
	 */
	public boolean killPiece(int playerNum, int pieceId, boolean forceKill) {
		
		// Loop through all of playerNum's pieces.
		ArrayList<Piece> playerPieces = getPlayer(playerNum).getPieces();
		for (int i = 0; i < getPlayer(playerNum).getNumPieces(); i++) {
			Piece p = playerPieces.get(i);
			
			// This is the piece. Possibly destroy it.
			if (p.getId() == pieceId) {
				if (forceKill || p.inflictDamage(this)) {
					
					// The piece was killed, so remove it from play.
					getMap().getSquare(p.getPosition()).setOccupant(0);
					playerPieces.remove(i);
					getKnowledgeHandler().notifyPieceDestroyed(p);
					
					// If the piece is invulnerable (i.e. a King) then respawn it.
					if (p.isInvulnerable()) {
						getPlayer(playerNum).respawn(this, p.getClass());
					}
					
					return true;
				}
				return false;
			}
		}
		
		return false;
	}
	
	public boolean killPiece(int pieceId, boolean forceKill) {
		return killPiece(getPieceById(pieceId).getOwner(), pieceId, forceKill);
	}
}
