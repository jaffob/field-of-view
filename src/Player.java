import java.util.ArrayList;


public class Player {

	protected final FieldOfView game;
	private final int number;
	private final Controller controller;
	private final ArrayList<Piece> pieces;
	
	public Player(FieldOfView fovGame, int playerNum, Controller controller) {
		game = fovGame;
		number = playerNum;
		this.controller = controller;
		pieces = new ArrayList<Piece>();
	}

	/**
	 * Starting point for this player's entire turn. This method takes
	 * user input (i.e. what piece to move where, and what action to
	 * perform) and doesn't return until the turn is over.
	 */
	public void takeTurn() {
		Utils.log("Player " + number + " starts turn (turn #" + game.getTurnCount() + " of game)");
		
		// Use the controller to select a piece.
		Piece selectedPiece = controller.selectPiece(pieces);
		selectedPiece.setSelected(true);
		
		// Main turn loop.
		ActionList availableActions;
		while (true) {
			
			// Get available actions, ending the turn if there aren't any.
			availableActions = selectedPiece.getActions();
			if (availableActions.size() == 0) {
				break;
			}
			
			Action selectedAction = controller.selectAction(availableActions);
			selectedAction.doAction();
			
			if (selectedAction.endsTurn()) {
				break;
			}
		}
		
		selectedPiece.setSelected(false);
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

	public boolean pieceCanMove(Piece piece, Direction dir,
			Square currentSquare, Square destSquare) {
		return true;
	}
	
	/**
	 * Called by a piece this player owns when it is killed, right before
	 * the piece itself handles it. This will be called even if the piece is a
	 * goal piece (and therefore will respawn), so use wasDestroyed to tell
	 * whether it should be removed from the piece list.
	 * @param piece The piece that got killed
	 * @param wasDestroyed True if killed; false if respawned
	 */
	public void notifyPieceKilled(Piece piece, boolean wasDestroyed) {
		if (wasDestroyed) getPieces().remove(piece);
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

}
