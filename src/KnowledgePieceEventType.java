
public enum KnowledgePieceEventType {
	CREATED, DESTROYED, MOVED, MOVED_AWAY, REVEALED, CONCEALED;
	
	@Override
	public String toString() {
		switch (this) {
		case CONCEALED:
			return "Concealed";
		case CREATED:
			return "Created";
		case DESTROYED:
			return "Destroyed";
		case MOVED:
			return "Moved";
		case MOVED_AWAY:
			return "Moved Away";
		case REVEALED:
			return "Revealed";
		}
		
		return "";
	};
}
