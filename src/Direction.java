import java.awt.Point;


public enum Direction {
	RIGHT,
	DOWN,
	LEFT,
	UP;
	
	public Point getUnitVector() {
		if (this == RIGHT) return new Point(1, 0);
		if (this == DOWN) return new Point(0, 1);
		if (this == LEFT) return new Point(-1, 0);
		return new Point(0, -1);
	}
}
