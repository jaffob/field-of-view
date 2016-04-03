import java.util.ArrayList;

/**
 * Action_Piece
 * A type of action that can be performed on a piece, or takes
 * a specific piece as input. That is, all actions where a piece
 * does something to another piece (of its choice) could be one of
 * these. This action is populated with all the pieces that can be
 * used, then asks the user (controller) which piece to use.
 */
public abstract class Action_Piece extends Action {

	private final ArrayList<Integer> pieces;
	
	public Action_Piece(int player, int actor) {
		super(player, actor);
		pieces = new ArrayList<Integer>();
	}

	public ArrayList<Integer> getPieces() {
		return pieces;
	}
	
	public void addPiece(Integer pieceId) {
		pieces.add(pieceId);
	}
	
	public Integer getPiece(int index) {
		return pieces.get(index);
	}
	
	public boolean hasPieces() {
		return !pieces.isEmpty();
	}

	@Override
	public void sendToKnowledgeHandler(FieldOfView game) {
		// Clear the pieces array before sending to KnowledgeHandler.
		getPieces().clear();
		super.sendToKnowledgeHandler(game);
	}
	
	@Override
	public boolean doAction(FieldOfView game) {
		return doActionOnPiece(game, getPiece(getPlayer(game).selectPiece(getPieces())));
	}

	/**
	 * Perform this action on a specified piece. Subclasses should
	 * generally override this and leave doAction() untouched. Note
	 * that it is still the responsibility of the subclass to call
	 * addActionPositionAtActor() if needed.
	 * @param game Reference to the game object
	 * @param pieceId ID of the piece to act on.
	 * @return Whether the action succeeded.
	 */
	public abstract boolean doActionOnPiece(FieldOfView game, Integer pieceId);
}
