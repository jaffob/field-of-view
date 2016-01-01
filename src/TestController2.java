import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;


public class TestController2 implements Controller {

	private TestMain1 tm1;
	private int playerNum;
	public KnowledgeState knowledge;
	public int inputResponse; 
	public Direction inputMove;
	
	public TestController2(TestMain1 testMain1, int playerNum) {
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
		return pieceIds.get(0);
	}

	@Override
	public Vector2D selectSquare(ArrayList<Vector2D> squarePositions) {
		return squarePositions.get(0);
	}

	@Override
	public int selectAction(ActionList availableActions) {
		
		
		inputResponse = -1;
		inputMove = null;
		
		for (int i = 0; i < availableActions.size(); i++) {
			JButton button = new JButton(availableActions.getAction(i).getFriendlyName());
			final int aaa = i;
			button.addActionListener(new ActionListener() {

		        @Override
		        public void actionPerformed(ActionEvent e) {
		            inputResponse = aaa;
		        }
		    });
			TestMain1.buttons.add(button);
		}
		
		TestMain1.buttons.revalidate();
		TestMain1.buttons.repaint();
		
		while (inputResponse < 0) {
			
			if (inputMove != null) {
				for (int i = 0; i < availableActions.size(); i++) {
					Action a = availableActions.getAction(i);
					if (a instanceof Action_Move && ((Action_Move)a).getDirection() == inputMove) {
						return i;
					}
				}
			}
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		TestMain1.buttons.removeAll();
		TestMain1.buttons.revalidate();
		TestMain1.buttons.repaint();
		return inputResponse;
	}

	@Override
	public void receiveTurnComponent(KnowledgeTurnComponent component) {
		Utils.log("Controller " + playerNum + ": received turn component");
		knowledge.absorbTurnComponent(component);
		tm1.repaint();
	}

}
