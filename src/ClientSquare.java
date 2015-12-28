import java.util.HashMap;
import java.util.Set;


public class ClientSquare {

	private final Class<? extends Square> gameClass;
	private final Vector2D position;
	private final boolean isKnown;
	private final int side;
	private final HashMap<String, Integer> stateVars;
	
	public ClientSquare(Class<? extends Square> gameClass, Vector2D position, int side, boolean isKnown) {
		this.gameClass = gameClass;
		this.position = position;
		this.isKnown = isKnown;
		this.side = side;
		this.stateVars = new HashMap<String, Integer>();
	}

	public Class<? extends Square> getGameClass() {
		return gameClass;
	}

	public Vector2D getPosition() {
		return position;
	}

	public boolean isKnown() {
		return isKnown;
	}

	public int getSide() {
		return side;
	}
	
	/**
	 * Set a state variable in this ClientSquare. To explicitly mark a
	 * state var as unknown, pass null as the value.
	 * @param varName The name of the var to add or set
	 * @param value The value, or null for unknown vars
	 */
	public void setStateVar(String varName, Integer value) {
		stateVars.put(varName, value);
	}
	
	public Integer getStateVar(String varName) {
		return stateVars.get(varName);
	}
	
	public Set<String> getStateVarNames() {
		return stateVars.keySet();
	}

}
