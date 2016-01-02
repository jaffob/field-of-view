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
	
	public boolean isZero() {
		return x == 0 && y == 0;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * Gets the Manhattan distance between this and another point.
	 * @param v The other Vector2D
	 * @return The total x+y distance between the points.
	 */
	public int gridDistance(Vector2D v) {
		return Math.abs(x - v.x) + Math.abs(y - v.y);
	}
	
	/**
	 * Returns whether this position is adjacent to another. Points
	 * are adjacent if they are one apart either horizontally or
	 * vertically (diagonals don't count).
	 * @param v The other Vector2D
	 * @return Whether the positions are adjacent.
	 */
	public boolean isAdjacentTo(Vector2D v) {
		return gridDistance(v) == 1;
	}
	
	/**
	 * Returns whether this Vector2D (being treated as a position) is
	 * within the bounds of an area whose size is specified by areaSize.
	 * @param areaSize The size of the area
	 * @return Whether this point is in bounds
	 */
	public boolean isInBounds(Vector2D areaSize) {
		return x >= 0 && y >= 0 && x < areaSize.x && y < areaSize.y;
	}

}
