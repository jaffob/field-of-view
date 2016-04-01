
public abstract class Raycast {
	
	/**
	 * Casts a theoretical ray in the specified direction. The ray
	 * will originate one square in front of the specified origin,
	 * and continue until onPosition() returns false or the edge of
	 * the "map" is hit.
	 * @param castOrigin Start point of the cast
	 * @param mapSize Size of the map; used to restrict the ray
	 * @param castDirection Direction to shoot the ray in
	 */
	public void castDir(Vector2D castOrigin, Vector2D mapSize, Direction castDirection) {
		for (Direction dir : Direction.values()) {
			if (castDirection == null || castDirection == dir) {
				for (Vector2D pos = castOrigin.plus(dir.getUnitVector()); pos.isInBounds(mapSize); pos.add(dir.getUnitVector())) {
					if (!onPosition(new Vector2D(pos))) {
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Casts rays in all four directions from the specified position.
	 * @param castOrigin Start point of the cast
	 * @param mapSize Size of the map; used to restrict the rays
	 */
	public void castAllDirs(Vector2D castOrigin, Vector2D mapSize) {
		castDir(castOrigin, mapSize, null);
	}

	public void castLine(Vector2D castStart, Vector2D castEnd) {
		int d = 0;

		int x1 = castStart.x, y1 = castStart.y, x2 = castEnd.x, y2 = castEnd.y;

		int dy = Math.abs(y2 - y1);
		int dx = Math.abs(x2 - x1);

		int dy2 = (dy << 1); // slope scaling factors to avoid floating
		int dx2 = (dx << 1); // point

		int ix = x1 < x2 ? 1 : -1; // increment direction
		int iy = y1 < y2 ? 1 : -1;

		if (dy <= dx) {
			while (true) {
				if (!onPosition(new Vector2D(x1, y1)))
					break;
				if (x1 == x2)
					break;
				x1 += ix;
				d += dy2;
				if (d > dx) {
					y1 += iy;
					d -= dx2;
				}
			}
		} else {
			while (true) {
				if (!onPosition(new Vector2D(x1, y1)))
					break;
				if (y1 == y2)
					break;
				y1 += iy;
				d += dx2;
				if (d > dy) {
					x1 += ix;
					d -= dy2;
				}
			}
		}
	}
	
	/**
	 * Called for each point a ray passes through. This should be used
	 * to perform operations at the specified position and to determine
	 * if the ray was blocked or should continue traveling.
	 * @param position The position is question
	 * @return False if the ray was blocked; true if it should continue.
	 */
	public abstract boolean onPosition(Vector2D position);
}
