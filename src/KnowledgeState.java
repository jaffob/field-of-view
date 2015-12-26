import java.util.ArrayList;


public class KnowledgeState {

	// Track squares and changes to squares.
	private final ClientSquare[][] initialSquares;
	private final ClientSquare[][] currentSquares;
	
	// Track pieces and changes to pieces.
	private final ArrayList<ClientPiece> initialPieces;
	private final ArrayList<ClientPiece> currentPieces;
	
	// Track turns as sets of actions, square updates, and piece updates.
	private final ArrayList<KnowledgeTurn> turns;
	private final int numberOfPlayers;
	//private int currentTurnPlayer;
	//private int currentTurnNum;
	
	
	// ---------------------------------- //
	// --------- Initialization --------- //
	// ---------------------------------- //
	
	public KnowledgeState(ClientSquare[][] initialSquares, ArrayList<ClientPiece> initialPieces, int numberOfPlayers) {
		
		// Load initial pieces and squares.
		this.initialSquares = initialSquares;
		this.initialPieces = initialPieces;
		
		// TODO error checking on map size
		
		// Copy initial squares into current squares.
		this.currentSquares = new ClientSquare[initialSquares.length][initialSquares[0].length];
		for (int i = 0; i < initialSquares.length; i++) {
			for (int j = 0; j < initialSquares[0].length; j++) {
				this.currentSquares[i][j] = this.initialSquares[i][j];
			}
		}
		
		// Copy initial pieces into current pieces.
		this.currentPieces = new ArrayList<ClientPiece>(this.initialPieces);
		
		// Initialize everything else.
		this.turns = new ArrayList<KnowledgeTurn>();
		this.numberOfPlayers = numberOfPlayers;
	}

	public KnowledgeState(ClientSquare[][] initialSquares, ArrayList<ClientPiece> initialPieces) {
		this(initialSquares, initialPieces, 2);
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
		return currentPieces;
	}

	public ArrayList<KnowledgeTurn> getTurns() {
		return turns;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/*public int getCurrentTurnPlayer() {
		return currentTurnPlayer;
	}

	protected void setCurrentTurnPlayer(int currentTurnPlayer) {
		this.currentTurnPlayer = currentTurnPlayer;
	}

	public int getCurrentTurnNum() {
		return currentTurnNum;
	}

	protected void setCurrentTurnNum(int currentTurnNum) {
		this.currentTurnNum = currentTurnNum;
	}*/
	
	protected void addComponentToCurrentTurn(KnowledgeTurnComponent component) {
		// TODO is this legal?
		getTurns().get(-1).addComponent(component);
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
		getTurns().add(new KnowledgeTurn(turnPlayer, turnNum));
	}
	
	public void notifyEndTurn(int turnPlayer, int turnNum) {
		Utils.log("KnowledgeState end turn #" + turnNum + " for Player " + turnPlayer);
	}
	
	public void absorbTurnComponent(KnowledgeTurnComponent component) {
		
		// First, push this turn component to the current turn.
		addComponentToCurrentTurn(component);
		// Update 
		for (ClientSquare newSq : component.getSquareUpdates()) {
			
		}
	}
}
