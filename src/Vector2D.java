import java.awt.Point;


public class Vector2D extends Point {

	private static final long serialVersionUID = -4262628273227735769L;

	public Vector2D() {
		super();
	}

	public Vector2D(Point p) {
		super(p);
	}

	public Vector2D(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Get the result of adding this and another Vector2D, without
	 * affecting this vector.
	 * @param v The other Vector2D
	 * @return A new Vector2D of the sum
	 */
	public Vector2D plus(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}
	
	/**
	 * Adds another Vector2D to this Vector2D.
	 * @param v Other vector
	 */
	public void add(Vector2D v) {
		x += v.x;
		y += v.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
