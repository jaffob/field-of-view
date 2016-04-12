
public enum Direction {
	RIGHT,
	DOWN,
	LEFT,
	UP;
	
	public Vector2D getUnitVector() {
		if (this == RIGHT) return new Vector2D(1, 0);
		if (this == DOWN) return new Vector2D(0, 1);
		if (this == LEFT) return new Vector2D(-1, 0);
		return new Vector2D(0, -1);
	}
	
	@Override
	public String toString() {
		switch (this) {
		case DOWN:
			return "Down";
		case LEFT:
			return "Left";
		case RIGHT:
			return "Right";
		case UP:
			return "Up";
		}
		return "";
	}
	
	public int toInteger() {
		switch (this) {
		case DOWN:
			return 0;
		case LEFT:
			return 1;
		case RIGHT:
			return 2;
		case UP:
			return 3;
		}
		return -1;
	}
	
	public static Direction fromInteger(int i) {
		switch (i) {
		case 0:
			return DOWN;
		case 1:
			return LEFT;
		case 2:
			return RIGHT;
		case 3:
			return UP;
		}
		
		return null;
	}
}
