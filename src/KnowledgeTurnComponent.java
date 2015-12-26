import java.util.ArrayList;

/**
 * KnowledgeTurnComponent
 * @author Jack
 * This is a basic data-structure class designed to store one component
 * of a turn. The KnowledgeBase stores a list of KnowledgeTurns, each
 * representing a full turn taken by a player. KnowledgeTurns then
 * store a list of KnowledgeTurnComponents representing each part of
 * the turn (e.g. Move, Move, Move, End Turn).
 */
public class KnowledgeTurnComponent {

	private final ClientAction action;
	private final ArrayList<ClientSquare> squareUpdates;
	private final ArrayList<ClientPiece> pieceUpdates;
	
	public KnowledgeTurnComponent(ClientAction action, ArrayList<ClientSquare> squareUpdates, ArrayList<ClientPiece> pieceUpdates) {
		this.action = action;
		this.squareUpdates = squareUpdates;
		this.pieceUpdates = pieceUpdates;
	}

	public ClientAction getAction() {
		return action;
	}

	public ArrayList<ClientSquare> getSquareUpdates() {
		return squareUpdates;
	}

	public ArrayList<ClientPiece> getPieceUpdates() {
		return pieceUpdates;
	}

}
