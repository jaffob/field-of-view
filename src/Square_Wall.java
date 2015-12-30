
public class Square_Wall extends Square {

	public Square_Wall(Vector2D position, Integer properties) {
		super(position, properties);
		setWalkable(false);
		setTransparent(false);
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.NEVER_TRANSPARENT;
	}

	
}
