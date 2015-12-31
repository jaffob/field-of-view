
public class Square_Gate extends Square {

	public Square_Gate(Vector2D position, Integer properties) {
		super(position, properties);
		
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.SOMETIMES_TRANSPARENT;
	}

}
