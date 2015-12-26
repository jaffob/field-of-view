
public class Square_Wall extends Square {

	public Square_Wall(FieldOfView fovGame, Vector2D position, char properties) {
		super(fovGame, position, properties);
		setWalkable(false);
		setTransparent(false);
	}

	@Override
	public String getFriendlyName() {
		return "Wall";
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.NEVER_TRANSPARENT;
	}
	
	
}
