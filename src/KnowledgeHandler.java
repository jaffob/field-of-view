import java.util.ArrayList;


public class KnowledgeHandler {

	private final FieldOfView game;
	private final boolean[][][] knowledge;
	private final KnowledgeTurnComponent[] turnComponents;
	
	public KnowledgeHandler(FieldOfView fovGame) {
		
		// Initialize variables.
		game = fovGame;
		knowledge = new boolean[game.getNumberOfPlayers()][game.getMap().getSize().x][game.getMap().getSize().y];
		turnComponents = new KnowledgeTurnComponent[game.getNumberOfPlayers()];
		resetTurnComponents();
		
		// For each player...
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			
			// Calculate knowledge for the first time.
			recalculateKnowledge(i);
		
			// TODO: make this part less of a hack
			// Tell this player to initialize its controller.
			// Re-calculating knowledge will have pushed some piece and
			// square updates to the turn components. Initial squares is
			// easy, but for initial enemy pieces that we know about, we
			// need to read the turn component.
			ArrayList<ClientPiece> initialPieces = game.getPlayer(i).createClientPieces();
			for (ClientPiece cp : getTurnComponent(i).getPieceUpdates()) {
				if (cp.getOwner() != i) {
					initialPieces.add(cp);
				}
			}
			game.getPlayer(i).initializeController(createClientSquares(i), initialPieces);
		}
		
		resetTurnComponents();
		
	}
	
	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	public boolean isSquareKnown(int playerNum, Vector2D squarePos) {
		return knowledge[playerNum - 1][squarePos.x][squarePos.y];
	}
	
	protected void setSquareKnown(int playerNum, Vector2D squarePos, boolean isKnown) {
		knowledge[playerNum - 1][squarePos.x][squarePos.y] = isKnown;
	}
	
	public KnowledgeTurnComponent getTurnComponent(int playerNum) {
		return turnComponents[playerNum - 1];
	}
	
	public void resetTurnComponents() {
		for (int i = 0; i < turnComponents.length; i++) {
			turnComponents[i] = new KnowledgeTurnComponent();
		}
	}
	
	// ---------------------------------- //
	// ------------- Methods ------------ //
	// ---------------------------------- //
	
	public boolean isPieceKnown(int playerNum, Piece piece) {
		return isSquareKnown(playerNum, piece.getPosition());
	}
	
	protected void recalculateKnowledge(int playerNum) {
		
		// Create a 2D array (all squares unknown) to represent the new knowledge for this player.
		boolean[][] newKnowledge = new boolean[game.getMap().getSize().x][game.getMap().getSize().y];
		
		// For each of this player's pieces...
		for (Piece piece : game.getPlayer(playerNum).getPieces()) {
			
			// Get a list of all the squares this piece can see, and mark them as known in newKnowledge.
			ArrayList<Vector2D> visibleSquares = game.getMap().getVisibleSquares(piece);
			for (Vector2D squarePos : visibleSquares) {
				newKnowledge[squarePos.x][squarePos.y] = true;
			}
		}
		
		// We now have an array of all the spaces this player can currently see. The main knowledge
		// array holds what they used to be able to see. Find all differences and push updates about
		// those squares to this player.
		for (int i = 0; i < game.getMap().getSize().x; i++) {
			for (int j = 0; i < game.getMap().getSize().y; i++) {
				if (knowledge[playerNum - 1][i][j] != newKnowledge[i][j]) {
					Square sq = game.getMap().getSquare(i, j);
					
					// Push updated knowledge about this square to the player.
					ClientSquare cs = sq.createClientSquare(newKnowledge[i][j]);
					getTurnComponent(playerNum).getSquareUpdates().add(cs);
					
					// If this square contains an enemy piece, push updates about that piece.
					Piece occupant = sq.getOccupant();
					if (sq.isOccupied() && sq.getOccupant().getOwner() != playerNum) {
						
						// The square was revealed. Note the piece was revealed and send a ClientPiece.
						if (newKnowledge[i][j]) {
							getTurnComponent(playerNum).getPieceEvents().add(new KnowledgePieceEvent(occupant.getId(), KnowledgePieceEventType.REVEALED));
							getTurnComponent(playerNum).getPieceUpdates().add(occupant.createClientPiece(playerNum));
						}
						
						// The square and piece were concealed. Simply note that fact, but we don't need to send a ClientPiece.
						else {
							getTurnComponent(playerNum).getPieceEvents().add(new KnowledgePieceEvent(occupant.getId(), KnowledgePieceEventType.CONCEALED));
						}
						
					}
					
					knowledge[playerNum - 1][i][j] = newKnowledge[i][j];
				}
			}
		}
	}

	public void notifyStartTurn(int turnPlayer, int turnNum) {

	}
	
	public void notifyEndTurn(int turnPlayer, int turnNum) {
		
	}
	
	/**
	 * Notifies the KnowledgeHandler that a state var of a square has
	 * changed.
	 * @param square
	 * @param varName
	 */
	public void notifySquareStateVarChange(Square square, String varName) {
		/* notify this handler that the state of a square has changed. This will make us look at
		 * state vars again, and if anything changed that a player should know about, resend that
		 * information.
		 */
		
		// For each player...
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			
			// Re-create a client square for the square that changed.
			boolean knowsThisSquare = isSquareKnown(i, square.getPosition());
			ClientSquare csq = square.createClientSquare(knowsThisSquare);
			Integer var = csq.getStateVar(varName);
			
			// If the state var that changed is known by this player, push a square update.
			if (var != null) {
				getTurnComponent(i).getSquareUpdates().add(csq);
				
				// If the transparency changed (and we can see the square), recalculate knowledge.
				if (varName.equals("isTransparent")) {
					recalculateKnowledge(i);
				}
			}
		}
	}


	public void notifyPieceCreated(Piece piece) {
		// Recalculate vision for whoever owns the new piece.
		recalculateKnowledge(piece.getOwner());

		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			if (isPieceKnown(i, piece)) {
				getTurnComponent(i).getPieceEvents().add(new KnowledgePieceEvent(piece.getId(), KnowledgePieceEventType.CREATED));
				getTurnComponent(i).getPieceUpdates().add(piece.createClientPiece(i));
			}
		}
	}
	
	/**
	 * Notifies this KnowledgeHandler that a piece has moved. This
	 * should be called after the move has been fully performed by
	 * the piece, so that the piece's position variable reflects
	 * the new position of the piece.
	 * @param piece The Piece that moved
	 */
	public void notifyPieceMoved(Piece piece) {
		
		recalculateKnowledge(piece.getOwner());
		
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			if (isPieceKnown(i, piece)) {
				getTurnComponent(i).getPieceUpdates().add(piece.createClientPiece(i));
			}
		}
	}
	
	/**
	 * Notify the KnowledgeHandler that a piece was destroyed. This
	 * should be called before the player is ever notified that the
	 * piece was killed.
	 * @param piece The piece that was destroyed
	 */
	public void notifyPieceDestroyed(Piece piece) {

		recalculateKnowledge(piece.getOwner());
		
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			if (isPieceKnown(i, piece)) {
				// Record that this piece was destroyed. We don't add a ClientPiece
				// to pieceUpdates because the piece's properties are no longer relevant.
				getTurnComponent(i).getPieceEvents().add(new KnowledgePieceEvent(piece.getId(), KnowledgePieceEventType.DESTROYED));
			}
		}
	}
	
	public void notifyPieceStateVarChange(Piece piece, String varName) {
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			ClientPiece csp = piece.createClientPiece(i);
			Integer var = csp.getStateVar(varName);
			
			// If the state var that changed is known by this player, push a piece update.
			if (var != null) {
				getTurnComponent(i).getPieceUpdates().add(csp);
			}
		}
	}
	
	public void notifyGameStateVarChange(String varName) {
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			getTurnComponent(i).getGameStateVarUpdates().put(varName, game.getGameStateVar(varName));
		}
	}
	
	/**
	 * Sets the ClientAction that is to be received by playerNum as part
	 * of the KnowledgeTurnComponent. This will generally be called from
	 * doAction() in the Action object, after all changes to the game have
	 * been made.
	 * @param playerNum The number of the player to push this action to
	 * @param action The ClientAction
	 */
	public void setTurnComponentAction(int playerNum, ClientAction action) {
		getTurnComponent(playerNum).setAction(action);
	}
	
	public void flushTurnComponents() {
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			game.getPlayer(i).receiveTurnComponent(getTurnComponent(i));
		}
		resetTurnComponents();
	}
	
	/**
	 * Create an array of ClientSquares for a certain player.
	 * @param playerNum The number of the player
	 * @return A 2D array of ClientSquares
	 */
	public ClientSquare[][] createClientSquares(int playerNum) {
		Vector2D mapSize = game.getMap().getSize();
		ClientSquare[][] css = new ClientSquare[mapSize.x][mapSize.y];
		
		for (int i = 0; i < mapSize.x; i++) {
			for (int j = 0; j < mapSize.y; j++) {
				Vector2D squarePos = new Vector2D(i, j);
				css[i][j] = game.getMap().getSquare(squarePos).createClientSquare(isSquareKnown(playerNum, squarePos));
			}
		}
		return css;
	}
}
