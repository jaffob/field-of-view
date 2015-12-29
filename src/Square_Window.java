
public class Square_Window extends Square {

	public Square_Window(Vector2D position, int properties) {
		super(position, properties);
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
