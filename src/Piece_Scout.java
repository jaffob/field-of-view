
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

	@Override
	public ActionList getUniqueActions(FieldOfView game) {
		// TODO Auto-generated method stub
		return super.getUniqueActions(game);
	}
}
