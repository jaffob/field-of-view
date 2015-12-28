
public class Square_Open extends Square {

	public Square_Open(FieldOfView fovGame, Vector2D position, int properties) {
		super(fovGame, position, properties);
	}

	@Override
	public String getFriendlyName() {
		return "Open";
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}
}
