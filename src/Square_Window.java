
public class Square_Window extends Square {

	public Square_Window(Vector2D position, Integer properties) {
		super(position, properties);
		setWalkable(false);
	}
	
	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}

}
