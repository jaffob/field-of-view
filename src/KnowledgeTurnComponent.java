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

	private Action action;
	private final ArrayList<ClientSquare> squareUpdates;
	private final ArrayList<ClientPiece> pieceUpdates;
	private final ArrayList<KnowledgePieceEvent> pieceEvents;
	private final HashMap<String, Integer> gameStateVarUpdates;
	
	public KnowledgeTurnComponent() {
		action = null;
		squareUpdates = new ArrayList<ClientSquare>();
		pieceUpdates = new ArrayList<ClientPiece>();
		pieceEvents = new ArrayList<KnowledgePieceEvent>();
		gameStateVarUpdates = new HashMap<String, Integer>();
	}

	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}

	public ArrayList<ClientSquare> getSquareUpdates() {
		return squareUpdates;
	}

	public ArrayList<ClientPiece> getPieceUpdates() {
		return pieceUpdates;
	}

	public ArrayList<KnowledgePieceEvent> getPieceEvents() {
		return pieceEvents;
	}

	public HashMap<String, Integer> getGameStateVarUpdates() {
		return gameStateVarUpdates;
	}

	public boolean isEmpty() {
		return getAction() == null &&
				getSquareUpdates().isEmpty() &&
				getPieceUpdates().isEmpty() &&
				getPieceEvents().isEmpty() &&
				getGameStateVarUpdates().isEmpty();
	}

}
