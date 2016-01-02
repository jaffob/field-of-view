import java.util.ArrayList;
import java.util.HashMap;


public interface Controller {
	
	public void initialize(ClientSquare[][] initialSquares, ArrayList<ClientPiece> initialPieces, HashMap<String, Integer> initialGameStateVars);
	
	public void notifyStartTurn(int turnPlayer, int turnNum);
	public void notifyEndTurn(int turnPlayer, int turnNum);
	
	/**
	 * Chooses a piece from a list of possible choices. Generally, this
	 * is where the user would be prompted to select a piece on the map.
	 * This function is used for any purpose where selection of a piece
	 * is required, ranging from choosing which piece to move at turn
	 * start, to choosing an enemy piece to kill.
	 * @param pieceIds List of piece IDs to choose from
	 * @return Array index of the chosen piece (NOT the ID)
	 */
	public int selectPiece(ArrayList<Integer> pieceIds);
	
	/**
	 * Chooses a square from a list of possible choices. Generally, this
	 * is where the user would be prompted to select a square on the map.
	 * This function is used for any purpose where selection of a square
	 * is required, ranging from choosing where to spawn a new piece, to
	 * choosing which adjacent gate to toggle.
	 * @param squarePositions List of square positions to choose from
	 * @return Array index of the chosen square position
	 */
	public int selectSquare(ArrayList<Vector2D> squarePositions);
	
	/**
	 * From the list of available ClientActions, choose one.
	 * @param availableActions List of actions available to the player
	 * @return The index of the selected action in the ClientActionList
	 */
	public int selectAction(ActionList availableActions);
	
	/**
	 * Gets confirmation about something.
	 * @param message The message to display to the end user
	 * @return True for "yes", false for "no"
	 */
	public boolean getConfirmation(String message);
	
	/**
	 * This is called from the knowledge handler (well, ultimately the
	 * player) when flushing completed turn components. Generally, the
	 * implementation of this method will pass the turn component on to
	 * a KnowledgeState.
	 * @param component The turn component
	 */
	public void receiveTurnComponent(KnowledgeTurnComponent component);
}
