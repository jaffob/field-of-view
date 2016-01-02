
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
	public void cast(Vector2D castOrigin, Vector2D mapSize, Direction castDirection) {
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
	public void cast(Vector2D castOrigin, Vector2D mapSize) {
		cast(castOrigin, mapSize, null);
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
