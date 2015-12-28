
public class KnowledgeHandler {

	private final FieldOfView game;
	private final boolean[][][] knowledge;
	private final KnowledgeTurnComponent[] turnComponents;
	
	public KnowledgeHandler(FieldOfView fovGame) {
		game = fovGame;
		knowledge = new boolean[game.getMap().getSize().x][game.getMap().getSize().y][game.getNumberOfPlayers()];
		turnComponents = new KnowledgeTurnComponent[game.getNumberOfPlayers()];
		resetTurnComponents();
	}
	
	
	// ---------------------------------- //
	// ------- Getters and Setters ------ //
	// ---------------------------------- //
	
	public boolean isSquareKnown(int playerNum, Vector2D squarePos) {
		return knowledge[squarePos.x][squarePos.y][playerNum - 1];
	}
	
	protected void setSquareKnown(int playerNum, Vector2D squarePos, boolean isKnown) {
		knowledge[squarePos.x][squarePos.y][playerNum - 1] = isKnown;
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
		// Owners must know about all of their pieces.
		if (piece.getOwner() == playerNum) {
			return true;
		}
		
		// This piece is invisible to other players.
		if (piece.getKnowledgeMethod() == 0) {
			return false;
		}
		
		// This piece is always visible to other players.
		if (piece.getKnowledgeMethod() == 2) {
			return true;
		}
		
		// This piece is dependent on whether its square is known.
		return isSquareKnown(playerNum, piece.getPosition());
	}
	
	protected void recalculateKnowledge(int playerNum) {
		
	}

	public void notifyStartTurn(int turnPlayer, int turnNum) {

	}
	
	public void notifyEndTurn(int turnPlayer, int turnNum) {
		
	}
	
	/**
	 * Notifies the KnowledgeHandler that a state var of a square has
	 * changed. This 
	 * @param squarePos
	 * @param varName
	 */
	public void notifySquareStateVarChange(Vector2D squarePos, String varName) {
		/* notify this handler that the state of a square has changed. This will make us look at
		 * state vars again, and if anything changed that a player should know about, resend that
		 * information.
		 */
		
		// For each player...
		for (int i = 1; i <= game.getNumberOfPlayers(); i++) {
			
			// Re-create a client square for the square that changed.
			boolean knowsThisSquare = isSquareKnown(i, squarePos);
			ClientSquare csq = game.getMap().getSquare(squarePos).createClientSquare(knowsThisSquare);
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
				getTurnComponent(i).getCreatedPieceIds().add(piece.getId());
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
				getTurnComponent(i).getDestroyedPieceIds().add(piece.getId());
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
}
