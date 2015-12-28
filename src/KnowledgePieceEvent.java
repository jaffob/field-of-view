
public class KnowledgePieceEvent {

	private final int pieceId;
	private final KnowledgePieceEventType type;
	
	public KnowledgePieceEvent(int pieceId, KnowledgePieceEventType type) {
		this.pieceId = pieceId;
		this.type = type;
	}

	public int getPieceId() {
		return pieceId;
	}

	public KnowledgePieceEventType getType() {
		return type;
	}
}
