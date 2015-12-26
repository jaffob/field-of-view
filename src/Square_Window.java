
public class Square_Window extends Square {

	public Square_Window(FieldOfView fovGame, Vector2D position, char properties) {
		super(fovGame, position, properties);
		setWalkable(false);
	}

	@Override
	public String getFriendlyName() {
		return "Window";
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}

}
