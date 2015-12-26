
public class Square_Open extends Square {

	public Square_Open(FieldOfView fovGame, Vector2D position, char properties) {
		super(fovGame, position, properties);
	}

	@Override
	public String getFriendlyName() {
		return "Open";
	}
}
