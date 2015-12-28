import java.util.ArrayList;

/**
 * Player
 * @author Jack Boffa
 * The Player class is the server-side representation of a player. Every
 * user participating the game is assigned a Player object. This is where
 * all of the logic for taking a turn is handled. It is also the only
 * class that is allowed to interact with Controllers once the game has
 * begun.
 */
public class Player {

	protected final FieldOfView game;
	private final int number;
	private final Controller controller;
	private final ArrayList<Piece> pieces;
	
	public Player(FieldOfView fovGame, int playerNum, Controller controller) {
		game = fovGame;
		number = playerNum;
		pieces = new ArrayList<Piece>();
		this.controller = controller;
		initializePieces();
	}
	
	/**
	 * Helper function that spawns our king(s).
	 */
	private void initializePieces() {
		// TODO: this
	}
	
	public void initializeController(ClientSquare[][] initialSquares, ArrayList<ClientPiece> initialPieces) {
		getController().initialize(initialSquares, initialPieces);
	}

	/**
	 * Starting point for this player's entire turn. This method takes
	 * user input (i.e. what piece to move where, and what action to
	 * perform) and doesn't return until the turn is over.
	 * @param turnNum The number of this turn in the game
	 */
	public void takeTurn(int turnNum) {
		Utils.log("Player " + number + " starts turn (turn #" + game.getTurnCount() + " of game)");
		
		getController().notifyStartTurn(getNumber(), turnNum);
		
		// Create ClientPieces for each of our pieces.
		ArrayList<ClientPiece> clientPieces = createClientPieces();
		
		// Ask the controller to choose a piece.
		int selectedPieceId = 0;
		Piece selectedPiece = null;
		do {
			selectedPieceId = getController().selectPiece(clientPieces);
		}
		while ((selectedPiece = getPieceById(selectedPieceId)) == null);
		
		selectedPiece.setSelected(true);
		
		// Main turn loop.
		while (true) {
			
			// Get available actions, ending the turn if there aren't any.
			ActionList availableActions = selectedPiece.getActions();
			if (availableActions.size() == 0) {
				break;
			}
			
			// Ask the controller to choose an action.
			ClientActionList clientActions = availableActions.createClientActionList();
			int selectedAction = 0;
			do {
				selectedAction = getController().selectAction(clientActions);
			}
			while (selectedAction < 0 || selectedAction >= availableActions.size());
			
			// Perform the action, then flush the knowledge handler's data to us and the opponent.
			availableActions.getAction(selectedAction).doAction(game);
			game.getKnowledgeHandler().flushTurnComponents();
			
			// If this action is supposed to end the turn, do exactly that.
			if (availableActions.getAction(selectedAction).endsTurn()) {
				break;
			}
		}
		
		// The turn is over. De-select the piece and tell the controller.
		selectedPiece.setSelected(false);
		getController().notifyEndTurn(getNumber(), turnNum);
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

	protected Controller getController() {
		return controller;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public boolean pieceCanMove(Piece piece, Direction dir,
			Square currentSquare, Square destSquare) {
		return true;
	}
	
	/**
	 * Called by a piece this player owns when it is destroyed.
	 * @param piece The piece that got killed
	 */
	public void notifyPieceKilled(Piece piece) {
		getPieces().remove(piece);
	}

	/**
	 * Called by a piece that is moving from originSquare to destSquare.
	 * This occurs right before the piece itself executes the move.
	 * @param piece The piece that is moving
	 * @param dir The direction the piece is moving in
	 * @param originSquare The square the piece is leaving
	 * @param destSquare The square the piece is entering
	 */
	public void notifyPieceMove(Piece piece, Direction dir,
			Square originSquare, Square destSquare) {
	}

	public void receiveTurnComponent(KnowledgeTurnComponent component) {
		getController().receiveTurnComponent(component);
	}
	
	public Piece getPieceById(int pieceId) {
		for (Piece p : getPieces()) {
			if (p.getId() == pieceId) {
				return p;
			}
		}
		return null;
	}
	
	public ArrayList<ClientPiece> createClientPieces() {
		ArrayList<ClientPiece> cps = new ArrayList<ClientPiece>();
		for (Piece p : pieces) {
			cps.add(p.createClientPiece(getNumber()));
		}
		return cps;
	}

}
