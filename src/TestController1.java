import java.util.ArrayList;


public class TestController1 implements Controller {

	private TestMain1 tm1;
	private Direction heading;
	private int playerNum;
	
	public TestController1(TestMain1 testMain1, int playerNum) {
		tm1 = testMain1;
		this.playerNum = playerNum;
	}

	@Override
	public void initialize(ClientSquare[][] initialSquares,
			ArrayList<ClientPiece> initialPieces) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyStartTurn(int turnPlayer, int turnNum) {
		Utils.log("Controller " + playerNum + ": Player " + turnPlayer + " starts turn (turn #" + turnNum + " of game)");
		
	}

	@Override
	public void notifyEndTurn(int turnPlayer, int turnNum) {
		Utils.log("Controller " + playerNum + ": Player " + turnPlayer + " ends turn");
		
	}

	@Override
	public int selectPiece(ArrayList<ClientPiece> pieces) {
		Utils.log("Controller " + playerNum + ": select piece");
		try {
			Thread.sleep(2000);
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
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Utils.log("Controller " + playerNum + ": select action");
		
		int bestDir = -1, bestDir2 = -1, end = 0;
		
		for (int i = 0; i < availableActions.size(); i++) {
			Action a = availableActions.getAction(i);
			if (a instanceof Action_Move) {
				if (heading == null) {
					heading = ((Action_Move)a).getDirection();
					return i;
				}
				if (((Action_Move)a).getDirection() == heading) {
					return i;
				}
				if (!((Action_Move)a).getDirection().getUnitVector().plus(heading.getUnitVector()).isZero()) {
					
					bestDir = i;
				}
				else
				{
					bestDir2 = i;
				}
			}
			if (a instanceof Action_EndTurn) {
				end = i;
			}
		}
		
		if (bestDir >= 0)
		{
			heading = ((Action_Move)availableActions.getAction(bestDir)).getDirection();
			return bestDir;
		}
		if (bestDir2 >= 0)
		{
			heading = ((Action_Move)availableActions.getAction(bestDir2)).getDirection();
			return bestDir2;
		}
			
		return end;
	}

	@Override
	public void receiveTurnComponent(KnowledgeTurnComponent component) {
		Utils.log("Controller " + playerNum + ": received turn component");
		tm1.repaint();
	}

}
