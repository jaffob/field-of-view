
public class Square_Gate extends Square {

	private boolean isOpen;
	
	public Square_Gate(Vector2D position, Integer properties) {
		super(position, properties);
	}
	
	@Override
	protected void absorbPropertyVal(int properties) {
		super.absorbPropertyVal(properties);
		
		// Use the 4th bit to determine if this gate starts open.
		boolean startOpen = (properties & 0b1000) != 0;
		setOpen(startOpen);
		setTransparent(startOpen);
		setWalkable(startOpen);
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

	/**
	 * Set whether this gate is open. This does nothing other than
	 * changing the isOpen property; actions should call openCloseGate()
	 * to ensure that everything is handled properly.
	 * @param isOpen
	 */
	protected void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * This method should be called from actions that open and close
	 * this gate. 
	 * @param game
	 * @param open
	 */
	public void openCloseGate(FieldOfView game, boolean open) {
		
		if (open == isOpen()) {
			return;
		}
		
		setOpen(open);
		setTransparent(open);
		setWalkable(open);
		
		// If closing, kill any pieces occupying this square.
		if (!open && isOccupied()) {
			game.killPiece(getOccupant(), true);
		}
		
		// Only tell the knowledge handler the transparency changed; it will
		// push an entirely new ClientPiece anyway.
		game.getKnowledgeHandler().notifySquareStateVarChange(this, "isTransparent");
	}
}
