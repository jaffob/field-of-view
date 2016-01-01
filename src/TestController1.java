import java.util.ArrayList;
import java.util.HashMap;


public class TestController1 implements Controller {

	private TestMain1 tm1;
	private Direction heading;
	private int playerNum;
	public KnowledgeState knowledge;
	
	public TestController1(TestMain1 testMain1, int playerNum) {
		tm1 = testMain1;
		this.playerNum = playerNum;
		
	}

	@Override
	public void initialize(ClientSquare[][] initialSquares, ArrayList<ClientPiece> initialPieces, HashMap<String, Integer> initialGameStateVars) {
		knowledge = new KnowledgeState(initialSquares, initialPieces, initialGameStateVars, 2);
	}

	@Override
	public void notifyStartTurn(int turnPlayer, int turnNum) {
		Utils.log("Controller " + playerNum + ": Player " + turnPlayer + " starts turn (turn #" + turnNum + " of game)");
		knowledge.notifyStartTurn(turnPlayer, turnNum);
	}

	@Override
	public void notifyEndTurn(int turnPlayer, int turnNum) {
		Utils.log("Controller " + playerNum + ": Player " + turnPlayer + " ends turn");
		knowledge.notifyEndTurn(turnPlayer, turnNum);
	}

	@Override
	public int selectPiece(ArrayList<Integer> pieceIds) {
		Utils.log("Controller " + playerNum + ": select piece");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pieceIds.get(0);
	}

	@Override
	public Vector2D selectSquare(ArrayList<Vector2D> squarePositions) {
		return squarePositions.get(0);
	}

	@Override
	public int selectAction(ActionList availableActions) {
		
		try {
			Thread.sleep(500);
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
		knowledge.absorbTurnComponent(component);
		tm1.repaint();
	}

}
