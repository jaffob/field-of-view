
public class MoveAction extends Action {

	private final Direction direction;
	
	public MoveAction(Player player, Piece actor, Direction direction) {
		super(player, actor, false);
		this.direction = direction;
	}
	
	public MoveAction(Piece actor, Direction direction) {
		this(actor.getOwnerPlayer(), actor, direction);
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public boolean doAction() {
		return getActor().move(getDirection());
	}

	@Override
	public String toString() {
		return "[" + getPlayer() + "] [" + getActor() + "] Move " + getDirection();
	}
}
