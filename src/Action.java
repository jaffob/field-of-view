import java.util.ArrayList;


public class Action {

	private String name, description;
	private boolean killsActor, winsGame, actsOnPiece;
	private ArrayList<Square> squares;
	
	public Action(Object placeholder, String name) {
		setName(name);
		setDescription("");
		setKillsActor(false);
		setWinsGame(false);
		setActsOnPiece(false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean killsActor() {
		return killsActor;
	}

	public void setKillsActor(boolean killsActor) {
		this.killsActor = killsActor;
	}

	public boolean winsGame() {
		return winsGame;
	}

	public void setWinsGame(boolean winsGame) {
		this.winsGame = winsGame;
	}

	public boolean actsOnPiece() {
		return actsOnPiece;
	}

	public void setActsOnPiece(boolean actsOnPiece) {
		this.actsOnPiece = actsOnPiece;
	}

	public ArrayList<Square> getSquares() {
		return squares;
	}

	public void addSquare(Square square) {
		squares.add(square);
	}

}
