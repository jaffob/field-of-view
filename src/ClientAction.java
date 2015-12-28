
public abstract class ClientAction {

	private final int player;
	private final ClientPiece actor;
	
	public ClientAction(int player, ClientPiece actor) {
		this.player = player;
		this.actor = actor;
	}

	public int getPlayer() {
		return player;
	}

	public ClientPiece getActor() {
		return actor;
	}

	public abstract String getFriendlyName();
	
}
