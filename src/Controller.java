import java.util.ArrayList;


public interface Controller {
	
	public void initialize(ClientSquare[][] initialSquares, ArrayList<ClientPiece> initialPieces);
	
	public void notifyStartTurn(int turnPlayer, int turnNum);
	public void notifyEndTurn(int turnPlayer, int turnNum);
	
	/**
	 * Chooses a piece from a list of possible choices. Generally, this
	 * is where the user would be prompted to select a piece on the map.
	 * This function is used for any purpose where selection of a piece
	 * is required, ranging from choosing which piece to move at turn
	 * start, to choosing an enemy piece to kill.
	 * @param pieces List of pieces to choose from
	 * @return ID of the chosen piece
	 */
	public int selectPiece(ArrayList<ClientPiece> pieces);
	
	/**
	 * Chooses a square from a list of possible choices. Generally, this
	 * is where the user would be prompted to select a square on the map.
	 * This function is used for any purpose where selection of a square
	 * is required, ranging from choosing where to spawn a new piece, to
	 * choosing which adjacent gate to toggle.
	 * @param squares List of squares to choose from
	 * @return Position of the chosen square
	 */
	public Vector2D selectSquare(ArrayList<ClientSquare> squares);
	
	/**
	 * From the list of available ClientActions, choose one.
	 * @param availableActions List of actions available to the player
	 * @return The index of the selected action in the ClientActionList
	 */
	public int selectAction(ActionList availableActions);
	
	/**
	 * 
	 * @param component
	 */
	public void receiveTurnComponent(KnowledgeTurnComponent component);
}
