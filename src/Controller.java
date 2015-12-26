import java.util.ArrayList;


public interface Controller {

	public void notifyStartTurn();
	public void notifyEndTurn();
	
	/**
	 * Before moving or performing any action, select which piece
	 * to act on.
	 * @param pieces The list of pieces that can be moved
	 * @return The selected piece
	 */
	public Piece selectPiece(ArrayList<Piece> pieces);
	
	
	
	public Action selectAction(ActionList availableActions);
}
