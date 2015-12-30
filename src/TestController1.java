import java.util.ArrayList;


public class TestController1 implements Controller {

	public TestController1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(ClientSquare[][] initialSquares,
			ArrayList<ClientPiece> initialPieces) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyStartTurn(int turnPlayer, int turnNum) {
		Utils.log("Controller " + turnPlayer + " starts turn (turn #" + turnNum + " of game)");
		
	}

	@Override
	public void notifyEndTurn(int turnPlayer, int turnNum) {
		Utils.log("Controller " + turnPlayer + " ends turn");
		
	}

	@Override
	public int selectPiece(ArrayList<ClientPiece> pieces) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pieces.get(0).getId();
	}

	@Override
	public Vector2D selectSquare(ArrayList<ClientSquare> squares) {
		return squares.get(0).getPosition();
	}

	@Override
	public int selectAction(ActionList availableActions) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void receiveTurnComponent(KnowledgeTurnComponent component) {
		
	}

}
