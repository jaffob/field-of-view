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
	
	public int button_up_i = -1;
	public int button_down_i = -1;
	public int button_left_i = -1;
	public int button_right_i = -1;
	
	public TestController2(TestMain1 testMain1, int playerNum) {
		tm1 = testMain1;
		this.playerNum = playerNum;
		
		TestMain1.button_down.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputResponse = button_down_i;
			}
		});
		
		TestMain1.button_up.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputResponse = button_up_i;
			}
		});
		
		TestMain1.button_right.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputResponse = button_right_i;
			}
		});
		
		TestMain1.button_left.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputResponse = button_left_i;
			}
		});
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
		inputResponse = -1;
		
		for (int i = 0; i < pieceIds.size(); i++) {
			JButton button = new JButton("Piece " + pieceIds.get(i));
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
			try {
				Thread.sleep(200);
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
	public int selectSquare(ArrayList<Vector2D> squarePositions) {
		inputResponse = -1;
		
		for (int i = 0; i < squarePositions.size(); i++) {
			JButton button = new JButton("" + squarePositions.get(i));
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
			try {
				Thread.sleep(200);
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
	public int selectAction(ActionList availableActions) {
		
		button_up_i = -1;
		button_down_i = -1;
		button_left_i = -1;
		button_right_i = -1;
		TestMain1.button_left.setEnabled(false);
		TestMain1.button_up.setEnabled(false);
		TestMain1.button_down.setEnabled(false);
		TestMain1.button_right.setEnabled(false);
		
		inputResponse = -1;
		inputMove = null;
		
		for (int i = 0; i < availableActions.size(); i++) {
			
			if (availableActions.getAction(i) instanceof Action_Move) {
				switch (((Action_Move)availableActions.getAction(i)).getDirection()) {
				case DOWN:
					button_down_i = i;
					TestMain1.button_down.setEnabled(true);
					break;
				case LEFT:
					button_left_i = i;
					TestMain1.button_left.setEnabled(true);
					break;
				case RIGHT:
					button_right_i = i;
					TestMain1.button_right.setEnabled(true);
					break;
				case UP:
					button_up_i = i;
					TestMain1.button_up.setEnabled(true);
					break;
				default:
					break;
				
				}
			}
			else {
				
				final int aaa = i;
				JButton button = new JButton(availableActions.getAction(i).getFriendlyName());
				
				button.addActionListener(new ActionListener() {
	
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            inputResponse = aaa;
			        }
			    });
				TestMain1.buttons.add(button);
			}
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
		
		button_up_i = -1;
		button_down_i = -1;
		button_left_i = -1;
		button_right_i = -1;
		TestMain1.button_left.setEnabled(false);
		TestMain1.button_up.setEnabled(false);
		TestMain1.button_down.setEnabled(false);
		TestMain1.button_right.setEnabled(false);
		
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

	@Override
	public boolean getConfirmation(String message) {
		return true;
	}
	

}
