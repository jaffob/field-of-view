import java.util.ArrayList;
import java.util.HashMap;

/**
 * KnowledgeTurnComponent
 * @author Jack Boffa
 * This is a basic data-structure class designed to store one component
 * of a turn. The KnowledgeBase stores a list of KnowledgeTurns, each
 * representing a full turn taken by a player. KnowledgeTurns then
 * store a list of KnowledgeTurnComponents representing each part of
 * the turn. Updates to known pieces and squares are stored in lists.
 * Updates are pushed to the end of each list by the KnowledgeHandler,
 * so if a list contains more than one reference to a piece or square,
 * the final (latest in the list) reference should be trusted.
 */
public class KnowledgeTurnComponent {

	private ClientAction action;
	private final ArrayList<ClientSquare> squareUpdates;
	private final ArrayList<ClientPiece> pieceUpdates;
	private final ArrayList<Integer> createdPieceIds;
	private final ArrayList<Integer> destroyedPieceIds;
	private final HashMap<String, Integer> gameStateVarUpdates;
	
	public KnowledgeTurnComponent() {
		action = null;
		squareUpdates = new ArrayList<ClientSquare>();
		pieceUpdates = new ArrayList<ClientPiece>();
		createdPieceIds = new ArrayList<Integer>();
		destroyedPieceIds = new ArrayList<Integer>();
		gameStateVarUpdates = new HashMap<String, Integer>();
	}

	public ClientAction getAction() {
		return action;
	}
	
	public void setAction(ClientAction action) {
		this.action = action;
	}

	public ArrayList<ClientSquare> getSquareUpdates() {
		return squareUpdates;
	}

	public ArrayList<ClientPiece> getPieceUpdates() {
		return pieceUpdates;
	}

	public ArrayList<Integer> getCreatedPieceIds() {
		return createdPieceIds;
	}

	public ArrayList<Integer> getDestroyedPieceIds() {
		return destroyedPieceIds;
	}

	public HashMap<String, Integer> getGameStateVarUpdates() {
		return gameStateVarUpdates;
	}

}
