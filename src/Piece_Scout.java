
public class Piece_Scout extends Piece {

	private int camerasRemaining;
	
	public Piece_Scout(Integer ownerNumber, Vector2D startPosition) {
		super(ownerNumber, startPosition);
		setMaxMoves(12);
		setCamerasRemaining(2);
	}

	public int getCamerasRemaining() {
		return camerasRemaining;
	}

	public void setCamerasRemaining(int camerasRemaining) {
		this.camerasRemaining = camerasRemaining;
	}
	
	public void addCameraRemaining() {
		camerasRemaining++;
	}
	
	public void removeCameraRemaining() {
		camerasRemaining--;
	}

	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		ActionList actions = super.getUniqueActions(game);
		
		// Create place and take camera actions.
		Action_CameraPlace place = new Action_CameraPlace(getOwner(), getId());
		Action_CameraTake take = new Action_CameraTake(getOwner(), getId());
		
		// Add squares to place and take depending on adjacent squares.
		for (Vector2D pos : getAdjacentPositions()) {
			Square sq = game.getMap().getSquare(pos);
			if (sq.isOccupied()) {
				Piece occupant = game.getPieceById(sq.getOccupant());
				if (occupant instanceof Piece_Camera) {
					take.addSquare(pos);
				}
			}
			else if (getCamerasRemaining() > 0 && sq.isWalkable()) {
				place.addSquare(pos);
			}
		}
		
		if (place.hasSquares()) {
			actions.addAction(place);
		}
		if (take.hasSquares()) {
			actions.addAction(take);
		}
		return actions;
	}
	
	@Override
	public ClientPiece createClientPiece(int forPlayer) {
		ClientPiece cp = super.createClientPiece(forPlayer);
		cp.setStateVar("camerasRemaining", getCamerasRemaining());
		return cp;
	}
}
