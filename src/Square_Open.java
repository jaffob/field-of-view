
public class Square_Open extends Square {

	public Square_Open(Vector2D position, Integer properties) {
		super(position, properties);
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
