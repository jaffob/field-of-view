
public class Square_Gate extends Square {

	private boolean isOpen;
	
	public Square_Gate(Vector2D position, Integer properties) {
		super(position, properties);
	}
	
	@Override
	protected void absorbPropertyVal(int properties) {
		super.absorbPropertyVal(properties);
		
		// Use the 4th bit to determine if this gate starts open.
		setOpen((properties & 0b1000) != 0);
	}
	
	@Override
	public int createPropertyVal() {
		return super.createPropertyVal() + (isOpen() ? 0b1000 : 0);
	}
	
	@Override
	public ClientSquare createClientSquare(boolean isKnown) {
		ClientSquare cs = super.createClientSquare(isKnown);
		cs.setStateVar("isOpen", isOpen() ? 1 : 0);
		return cs;
	}

	@Override
	public Transparency getTransparencyGuarantee() {
		return Transparency.SOMETIMES_TRANSPARENT;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
		setTransparent(isOpen);
		setWalkable(isOpen);
	}

}
