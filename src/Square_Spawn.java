
public class Square_Spawn extends Square {

	public Square_Spawn(Vector2D position, Integer properties) {
		super(position, properties);
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}

}
