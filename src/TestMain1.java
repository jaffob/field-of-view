import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class TestMain1 extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final JPanel buttons = new JPanel();
	public static final JPanel dbuttons = new JPanel();
	
	public static final JButton button_up = new JButton("^");
	public static final JButton button_down = new JButton("v");
	public static final JButton button_left = new JButton("<");
	public static final JButton button_right = new JButton(">");
	
	private final int SQSIZE = 32;
	public FieldOfView game;
	private TestController2[] conts;
	private Map map;
	public boolean isEditor;
	private Scanner console;
	String mappath;
	
	public TestMain1() {
		
		console = new Scanner(System.in);
		System.out.print("e for editor, q to quit, anything else for game: ");
		String choice = console.nextLine();
		// EDITOR
		if (choice.equals("e")) {
			System.out.print("Enter map name to load, or blank for new map: ");
			mappath = console.nextLine();
			isEditor = true;
			try {
				map = new Map(mappath);
			} catch (IOException | InvalidMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
			
			addMouseListener(new MouseAdapter() {
				 public void mousePressed(MouseEvent e) {
					 Vector2D sqpos = new Vector2D(e.getX() / SQSIZE, e.getY() / SQSIZE);
					 if (!map.positionIsInBounds(sqpos)) {
						 return;
					 }
					 if (SwingUtilities.isLeftMouseButton(e)) {
						 int sqtype = map.getSquareType(map.getSquare(sqpos).getClass());
						 Square newSquare = map.createSquare((sqtype + 1) % map.getNumSquareTypes(), sqpos, 0);
						 map.getSquares()[sqpos.x][sqpos.y] = newSquare;
					 }
					 else if (SwingUtilities.isRightMouseButton(e)) {
						 map.getSquares()[sqpos.x][sqpos.y].setSide( (map.getSquares()[sqpos.x][sqpos.y].getSide() + 1) % 3);
					 }
					 else if (map.getSquare(sqpos) instanceof Square_Gate) {
						 Square_Gate gate = (Square_Gate)map.getSquare(sqpos);
						 gate.setOpen(!gate.isOpen());
					 }
					 
					 repaint();
				 }
		    });
			
			JButton save = new JButton("Save");
			buttons.add(save);
			save.addActionListener(new ActionListener() {

		        @Override
		        public void actionPerformed(ActionEvent e) {
		            saveMap();
		        }
		    });
		}
		
		else if (choice.equals("q")) {
			System.exit(0);
		}
		
		// GAME
		else {
			System.out.print("Enter map name to load: ");
			mappath = console.nextLine();
			conts = new TestController2[2];
			conts[0] = new TestController2(this, 1);
			conts[1] = new TestController2(this, 2);
			
			try {
				game = new FieldOfView(mappath, conts);
			} catch (IOException | InvalidMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(0);
			}
			
			
		}
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, 1024, 512);
	    
	    if (isEditor) {
		    
		    Square[][] sq = isEditor ? map.getSquares() : game.getMap().getSquares();
		    for (int i = 0; i < sq.length; i++) {
				for (int j = 0; j < sq[0].length; j++) {
					Square s = sq[i][j];
					
					g.setColor(getSquareColor(s.createClientSquare(true)));
					
					g.fillRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
					g.setColor(Color.BLACK);
					g.drawRect(s.getPosition().x * SQSIZE, s.getPosition().y * SQSIZE, SQSIZE, SQSIZE);
					
					switch (s.getSide()) {
					case 1:
						g.setColor(Color.RED);
						break;
					case 2:
						g.setColor(Color.BLUE);
						break;
					default:
						g.setColor(Color.WHITE);
					}
					
					g.fillRect(s.getPosition().x * SQSIZE + 1, s.getPosition().y * SQSIZE + 1, 8, 8);
				}
			}
	    }
	    
	    else {
	    
	    	for (int player = 0; player < 2; player++) {
	    		KnowledgeState ks = conts[player].knowledge;
	    		ClientSquare[][] sq = ks.getCurrentSquares();
	    		for (int i = 0; i < sq.length; i++) {
					for (int j = 0; j < sq[0].length; j++) {
						ClientSquare s = sq[i][j];
						
						g.setColor(getSquareColor(s));
						
						int sqx = (544 * player) + s.getPosition().x * SQSIZE;
						int sqy = s.getPosition().y * SQSIZE;
						g.fillRect(sqx, sqy, SQSIZE, SQSIZE);
						
						Color genColor;
						if (ks.getCurrentGameStateVars().get("generator") == 2)
							genColor = new Color(0, 128, 0);
						else if (ks.getCurrentGameStateVars().get("generator") == 1) {
							genColor = new Color(0, 64, 0);
						}
						else
						{
							genColor = Color.BLACK;
						}
						g.setColor(Color.BLACK);
						g.drawRect(sqx, sqy, SQSIZE, SQSIZE);
						
						if (!s.isKnown()) {
							g.setColor(genColor);
							g.fillRect(sqx + 5, sqy + 5, SQSIZE - 10, SQSIZE - 10);
						}
					}
				}
	    		
	    		for (ClientPiece p : ks.getCurrentPieces()) {
			    	g.setColor(p.getOwner() == 1 ? Color.RED : Color.BLUE);
			    	g.fillOval((544 * player) + p.getPosition().x * SQSIZE + 4, p.getPosition().y * SQSIZE + 4, 24, 24);
			    	g.setColor(Color.WHITE);
			    	g.drawString(p.getGameClass().getName().substring(6, 8) + p.getId(), (544 * player) + p.getPosition().x * SQSIZE + 7, p.getPosition().y * SQSIZE + 21);
			    }
	    	}
		    
	    }
	}

	public static void main(String[] args) {
		JFrame window = new JFrame();
		TestMain1 game = new TestMain1();
		window.getContentPane().add(game, BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		window.getContentPane().add(buttons, BorderLayout.SOUTH);
		window.getContentPane().add(dbuttons, BorderLayout.NORTH);
		
		dbuttons.add(button_left);
		dbuttons.add(button_up);
		dbuttons.add(button_down);
		dbuttons.add(button_right);
		button_left.setEnabled(false);
		button_up.setEnabled(false);
		button_down.setEnabled(false);
		button_right.setEnabled(false);
		
		window.setSize(1060, 600);
		window.setVisible(true);
		if (!game.isEditor)
			System.out.println("player " + game.game.play() + " wins! woot");
	}

	protected void saveMap() {
		if (!isEditor)
			return;
		map.setMapName("Test of Mapping");
		map.setMapAuthor("Jack");
		map.setMapRequiredMod("none");
		map.setMapVersion("1.0");
		
		try {
			map.saveMapFile(mappath.isEmpty() ? "out.fov" : mappath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Color getSquareColor(ClientSquare cs) {
		if (cs.getGameClass().equals(Square_Open.class))
			return Color.WHITE;
		else if (cs.getGameClass().equals(Square_Window.class))
			return Color.GRAY;
		else if (cs.getGameClass().equals(Square_Start.class))
			return Color.YELLOW;
		else if (cs.getGameClass().equals(Square_Victory.class))
			return Color.GREEN;
		else if (cs.getGameClass().equals(Square_Gate.class)) {
			if (cs.getStateVar("isOpen") > 0) {
				return new Color(255, 150, 150);
			}
			else {
				return new Color(150, 0, 0);
			}
		}
		else if (cs.getGameClass().equals(Square_Deploy.class)) {
			return Color.BLUE;
		}
		else if (cs.getGameClass().equals(Square_Spawn.class)) {
			return new Color(255, 255, 128);
		}
		else if (cs.getGameClass().equals(Square_Generator.class)) {
			return Color.CYAN;
		}
		return Color.DARK_GRAY;
	}

	/*@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("AISDHOSD");
		Direction d;
		switch (e.getKeyChar()) {
		case 'a':
			d = Direction.LEFT;
			break;
		case 'w':
			d = Direction.UP;
			break;
		case 'd':
			d = Direction.RIGHT;
			break;
		case 's':
			d = Direction.DOWN;
			break;
		default:
			return;
		}
		conts[0].inputMove = d;
		conts[1].inputMove = d;
	}*/
}
