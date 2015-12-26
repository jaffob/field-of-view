
public class MoveAction extends Action {

	private final Direction direction;
	
	public MoveAction(Player player, Piece actor, Direction direction) {
		super(player, actor);
		setEndsTurn(false);
		this.direction = direction;
	}
	
	public MoveAction(Piece actor, Direction direction) {
		this(actor.getOwnerPlayer(), actor, direction);
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public void doAction(FieldOfView game) {
		getActor().move(getDirection());
	}

	@Override
	public String toString() {
		return "[" + getPlayer() + "] [" + getActor() + "] Move " + getDirection();
	}

	@Override
	public ClientAction createClientAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyClientAction(ClientAction ca) {
		// TODO Auto-generated method stub
		return false;
	}
}
