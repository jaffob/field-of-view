
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
}
