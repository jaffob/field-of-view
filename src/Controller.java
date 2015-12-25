import java.util.ArrayList;


public interface Controller {

	/**
	 * Before moving or performing any action, select which piece
	 * to act on.
	 * @param pieces The list of pieces that can be moved
	 * @return The selected piece
	 */
	public Piece selectPiece(ArrayList<Piece> pieces);
	
	/**
	 * Select a direction to move one space in.
	 * @return
	 */
	public Action act();
}