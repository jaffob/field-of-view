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
	public void doAction(FieldOfView game) {
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
		
		Vector2D spawnPos = spawns.get(getPlayer(game).selectSquare(spawns));
		game.spawnPiece(getDeployClass(), getPlayerNum(), spawnPos);
		
		// Add the actor as the only action position.
		addActionPositionAtActor(game);
	}

	@Override
	public String getFriendlyName() {
		return "Deploy " + getDeployName();
	}

}
