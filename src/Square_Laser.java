
public class Square_Laser extends Square_Wall {

	private Direction laserDirection;
	
	public Square_Laser(Vector2D position, Integer properties) {
		super(position, properties);
	}
	
	@Override
	protected void absorbPropertyVal(int properties) {
		super.absorbPropertyVal(properties);
		laserDirection = Direction.fromInteger((properties & 0b110000) >> 4);
	}
	
	@Override
	public int createPropertyVal() {
		return super.createPropertyVal() + ((laserDirection.toInteger() & 0b11) << 4);
	}

}
