
public class Action_Shield extends Action_Piece {

	public Action_Shield(int player, int actor) {
		super(player, actor);
	}

	@Override
	public boolean doActionOnPiece(FieldOfView game, Integer pieceId) {
		Piece p = game.getPieceById(pieceId);
		p.setShieldLevel(p.getShieldLevel() + 1);
		game.getKnowledgeHandler().notifyPieceStateVarChange(p, "shieldLevel");
		return true;
	}

	@Override
	public String getFriendlyName() {
		return "Shield Piece";
	}

}
