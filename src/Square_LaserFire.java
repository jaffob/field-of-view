
public class Square_LaserFire extends Square {

	public Square_LaserFire(Vector2D position, Integer properties) {
		super(position, properties);
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}
	
	@Override
	public ActionList getSpecialActions(FieldOfView game, Piece piece) {
		ActionList actions = super.getSpecialActions(game, piece);
		
		// Generator must have power to fire the lasers. That's the only condition.
		if (game.getGameStateVar("generator") > 0) {
			actions.addAction(new Action_LaserFire(piece.getOwner(), piece.getId()));
		}
		
		return actions;
	}

}
