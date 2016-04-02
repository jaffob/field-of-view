import java.util.ArrayList;
import java.util.HashMap;


public class KnowledgeState {
	
	// Track squares and changes to squares.
	private final ClientSquare[][] initialSquares;
	private final ClientSquare[][] currentSquares;
	
	// Track pieces and changes to pieces.
	private final ArrayList<ClientPiece> initialPieces;
	private final HashMap<Integer, ClientPiece> currentPieces;
	
	// Track game state vars.
	private final HashMap<String, Integer> initialGameStateVars;
	private final HashMap<String, Integer> currentGameStateVars;
	
	// Track turns as sets of actions, square updates, and piece updates.
	private final ArrayList<KnowledgeTurn> turns;
	private KnowledgeTurn currentTurn;
	private final int numberOfPlayers;
	
	
	// ---------------------------------- //
	// --------- Initialization --------- //
	// ---------------------------------- //
	
	public KnowledgeState(ClientSquare[][] initialSquares, ArrayList<ClientPiece> initialPieces, HashMap<String, Integer> initialGameStateVars, int numberOfPlayers) {
		
		// Load initial pieces, squares, and game state vars.
		this.initialSquares = initialSquares;
		this.initialPieces = initialPieces;
		this.initialGameStateVars = initialGameStateVars;
		
		// Copy initial squares into current squares.
		this.currentSquares = new ClientSquare[initialSquares.length][initialSquares[0].length];
		for (int i = 0; i < initialSquares.length; i++) {
			for (int j = 0; j < initialSquares[0].length; j++) {
				this.currentSquares[i][j] = this.initialSquares[i][j];
			}
		}
		
		// Copy initial pieces into a map.
		this.currentPieces = new HashMap<Integer, ClientPiece>();
		for (ClientPiece cp : initialPieces) {
			this.currentPieces.put(cp.getId(), cp);
		}
		
		this.currentGameStateVars = new HashMap<String, Integer>(initialGameStateVars);
		
		// Initialize everything else.
		this.turns = new ArrayList<KnowledgeTurn>();
		this.numberOfPlayers = numberOfPlayers;
	}
			
	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	public ClientSquare[][] getInitialSquares() {
		return initialSquares;
	}

	public ClientSquare[][] getCurrentSquares() {
		return currentSquares;
	}

	public ArrayList<ClientPiece> getInitialPieces() {
		return initialPieces;
	}

	public ArrayList<ClientPiece> getCurrentPieces() {
		return new ArrayList<ClientPiece>(currentPieces.values());
	}

	public HashMap<Integer, ClientPiece> getCurrentPiecesMap() {
		return currentPieces;
	}
	
	public HashMap<String, Integer> getInitialGameStateVars() {
		return initialGameStateVars;
	}


	public HashMap<String, Integer> getCurrentGameStateVars() {
		return currentGameStateVars;
	}


	public ArrayList<KnowledgeTurn> getTurns() {
		return turns;
	}

	public KnowledgeTurn getCurrentTurn() {
		return currentTurn;
	}


	protected void setCurrentTurn(KnowledgeTurn currentTurn) {
		this.currentTurn = currentTurn;
	}


	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	protected void addComponentToCurrentTurn(KnowledgeTurnComponent component) {
		getCurrentTurn().addComponent(component);
	}
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //
	
	/**
	 * Add a new empty turn to the turns list, and update currentTurnPlayer
	 * and currentTurnNum. Should be called whenever the next turn starts.
	 * Generally, that means the Controller that maintains this KnowledgeState
	 * would call nextTurn() at both the start and the end of turn.
	 */
	public void notifyStartTurn(int turnPlayer, int turnNum) {
		Utils.log("KnowledgeState start turn #" + turnNum + " for Player " + turnPlayer);
		setCurrentTurn(new KnowledgeTurn(turnPlayer, turnNum));
	}
	
	public void notifyEndTurn(int turnPlayer, int turnNum) {
		Utils.log("KnowledgeState end turn #" + turnNum + " for Player " + turnPlayer);
		getTurns().add(getCurrentTurn());
		setCurrentTurn(null);
	}
	
	public void absorbTurnComponent(KnowledgeTurnComponent component) {
		
		// First, push this turn component to the current turn.
		addComponentToCurrentTurn(component);
		
		// Loop through the piece events to remove pieces that were concealed, destroyed, or moved away.
		for (int i = 0; i < component.getPieceEvents().size(); i++) {
			KnowledgePieceEventType evType = component.getPieceEvents().get(i).getType();
			if (evType == KnowledgePieceEventType.CONCEALED ||
					evType == KnowledgePieceEventType.DESTROYED ||
					evType == KnowledgePieceEventType.MOVED_AWAY) {
				getCurrentPiecesMap().remove(component.getPieceEvents().get(i).getPieceId());
			}
		}
		
		// Update currentSquares with every new square we've received.
		for (ClientSquare newCS : component.getSquareUpdates()) {
			currentSquares[newCS.getPosition().x][newCS.getPosition().y] = newCS;
		}
		
		// Add or replace updated pieces.
		for (ClientPiece newCP : component.getPieceUpdates()) {
			getCurrentPiecesMap().put(newCP.getId(), newCP);
		}
		
		// Update any game state vars that changed.
		for (String varName : component.getGameStateVarUpdates().keySet()) {
			getCurrentGameStateVars().put(varName, component.getGameStateVarUpdates().get(varName));
		}
	}
}
