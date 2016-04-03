
public class Square_Shield extends Square {

	public Square_Shield(Vector2D position, Integer properties) {
		super(position, properties);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.ALWAYS_TRANSPARENT;
	}

}
