import java.util.ArrayList;


public class Action_Deploy extends Action {

	private final Class<? extends Piece> deployClass;
	private final String deployName;
	
	public Action_Deploy(int player, int actor, Class<? extends Piece> deployClass, String deployName) {
		super(player, actor);
		this.deployClass = deployClass;
		this.deployName = deployName;
	}

	public Class<? extends Piece> getDeployClass() {
		return deployClass;
	}

	public String getDeployName() {
		return deployName;
	}

	@Override
	public boolean doAction(FieldOfView game) {
		addActionPositionAtActor(game);
		
		ArrayList<Vector2D> spawns = new ArrayList<Vector2D>();
		
		// Populate spawns with positions of all available spawn squares.
		for (int i = 0; i < game.getMap().getSquares().length; i++) {
			for (int j = 0; j < game.getMap().getSquares()[0].length; j++) {
				Square sq = game.getMap().getSquare(i, j);
				if (sq instanceof Square_Spawn
						&& (sq.getSide() == 0 || sq.getSide() == getPlayerNum())
						&& sq.isWalkable() && !sq.isOccupied()) {
					spawns.add(sq.getPosition());
				}
			}
		}
		
		if (spawns.isEmpty())
			return false;
		
		// Damage whatever piece triggered the deploy.
		game.killPiece(getActor(), false);
		
		// Spawn the new piece.
		Vector2D spawnPos = spawns.get(getPlayer(game).selectSquare(spawns));
		return game.spawnPiece(getDeployClass(), getPlayerNum(), spawnPos);
	}

	@Override
	public String getFriendlyName() {
		return "Deploy " + getDeployName();
	}

}
