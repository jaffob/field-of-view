import java.util.HashMap;
import java.util.Set;


public class ClientPiece {

	private final Class<? extends Piece> gameClass;
	private final int id;
	private final int owner;
	private final Vector2D position;
	private final HashMap<String, Integer> stateVars;
	
	public ClientPiece(Class<? extends Piece> gameClass, int id, int owner, Vector2D position) {
		this.gameClass = gameClass;
		this.id = id;
		this.owner = owner;
		this.position = position;
		this.stateVars = new HashMap<String, Integer>();
	}

	public Class<? extends Piece> getGameClass() {
		return gameClass;
	}

	public int getId() {
		return id;
	}

	public int getOwner() {
		return owner;
	}

	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Set a state variable in this ClientPiece. To explicitly mark a
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
