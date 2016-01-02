
public class Square_Generator extends Square {

	public Square_Generator(Vector2D position, Integer properties) {
		super(position, properties);
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}
	
	@Override
	public ActionList getSpecialActions(FieldOfView game, Piece piece) {
		ActionList actions = super.getSpecialActions(game, piece);
		Integer currentPower = game.getGameStateVar("generator");
		
		if (currentPower == null) {
			return actions;
		}
		
		if (currentPower < 2) {
			actions.addAction(new Action_Generator(piece.getOwner(), piece.getId(), true));
		}
		
		if (currentPower > 0) {
			actions.addAction(new Action_Generator(piece.getOwner(), piece.getId(), false));
		}
		
		return actions;
	}

}
